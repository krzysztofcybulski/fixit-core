package me.kcybulski.fixit.domain.processes

import org.camunda.bpm.engine.FormService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.form.FormField
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import org.camunda.bpm.engine.task.Task as CamundaTask

@Service
class UserTaskService(private val taskService: TaskService,
                      private val formService: FormService) {

    fun getUserTasks(authentication: Authentication): List<MiniTask> = getAvailableTasks(authentication)
            .map { it.asMiniDomain() }

    fun getUserTask(id: String, authentication: Authentication): Task? = taskService
            .createTaskQuery()
            .taskId(id)
            .active()
            .or()
            .taskCandidateUser((authentication.principal as User).username)
            .taskCandidateGroupIn(userGroups(authentication))
            .endOr()
            .singleResult()
            ?.asDomain()

    fun completeTask(authentication: Authentication, id: String, variables: Map<String, Any>) {
        require(userCanComplete(authentication, id))
        return taskService.complete(id, variables)
    }

    private fun userCanComplete(authentication: Authentication, id: String): Boolean = getAvailableTasks(authentication)
            .any { it.id == id }

    private fun getAvailableTasks(authentication: Authentication): List<CamundaTask> = taskService
            .createTaskQuery()
            .active()
            .or()
            .taskCandidateUser((authentication.principal as User).username)
            .taskCandidateGroupIn(userGroups(authentication))
            .endOr()
            .list()

    fun userGroups(authentication: Authentication): List<String> = authentication.authorities.map { it.authority }

    private fun CamundaTask.asMiniDomain() = MiniTask(id, name,
            taskService.getVariable(id, "photo") as String?, priority)

    private fun CamundaTask.asDomain() = Task(id, name,
            taskService.getVariables(id),
            formService.getTaskFormData(id).formFields,
            priority)
}

data class MiniTask(val id: String, val name: String, val photo: String?, val priority: Int = 0)

data class Task(val id: String, val name: String, val variables: Map<String, Any>, val form: List<FormField>, val priority: Int = 0)