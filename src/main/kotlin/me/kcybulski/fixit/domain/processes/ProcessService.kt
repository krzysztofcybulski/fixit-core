package me.kcybulski.fixit.domain.processes

import me.kcybulski.fixit.domain.exceptions.ProcessDefinitionNotFoundException
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.repository.ProcessDefinition
import org.camunda.bpm.engine.task.Task
import org.springframework.stereotype.Service
import org.camunda.bpm.engine.runtime.ProcessInstance as CamundaProcessInstance

@Service
class ProcessService(private val runtimeService: RuntimeService,
                     private val taskService: TaskService,
                     private val definitionService: DefinitionService) {

    fun listAll(): List<MiniProcessInstance> = runtimeService.createProcessInstanceQuery()
            .active()
            .list()
            .map { it.toMiniDomain() }


    fun get(id: String): ProcessInstance? = runtimeService.createProcessInstanceQuery()
            .active()
            .processInstanceId(id)
            .singleResult()
            ?.toDomain()

    fun startProcess(definitionKey: String, variables: Map<String, Any>, businessKey: String?): ProcessInstance =
            definitionService.getLatestDefinition(definitionKey)
                    ?.let { startProcess(it, variables, businessKey) }
                    ?.toDomain()
                    ?: throw ProcessDefinitionNotFoundException()

    private fun startProcess(processDefinition: ProcessDefinition, variables: Map<String, Any>, businessKey: String?): CamundaProcessInstance =
            businessKey?.let { runtimeService.startProcessInstanceById(processDefinition.id, it, variables) }
                    ?: runtimeService.startProcessInstanceById(processDefinition.id, variables)

    private fun CamundaProcessInstance.toMiniDomain(): MiniProcessInstance {
        val tasks = getTasks(this)
        return MiniProcessInstance(
                id, tasks.first().name, getProgress(tasks), tasks.first().assignee, getPhoto(this), businessKey
        )
    }

    private fun CamundaProcessInstance.toDomain(): ProcessInstance {
        val tasks = getTasks(this)
        return ProcessInstance(
                id, tasks.first().name, getProgress(tasks), getVariables(this),
                tasks.first().assignee, getPhoto(this), businessKey
        )
    }

    private fun getProgress(tasks: List<Task>): Int = try {
            tasks.first { it.priority >= 0 }.priority
        } catch (e: NoSuchElementException) {
            0
        }

    private fun getPhoto(processInstance: CamundaProcessInstance): String? =
            runtimeService.getVariable(processInstance.id, "photo") as String?

    private fun getVariables(processInstance: CamundaProcessInstance) =
            runtimeService.getVariables(processInstance.id)

    private fun getTasks(processInstance: CamundaProcessInstance): List<Task> =
            taskService.createTaskQuery()
                .processInstanceId(processInstance.id)
                .orderByTaskCreateTime()
                .desc()
                .list()
}

data class MiniProcessInstance(val id: String, val status: String, val progress: Int,
                               val assignee: String?, val photo: String?, val businessKey: String?)

data class ProcessInstance(val id: String, val status: String, val progress: Int, val variables: Map<String, Any>,
                           val assignee: String?, val photo: String?, val businessKey: String?)