package me.kcybulski.fixit.domain.processes

import org.camunda.bpm.engine.FormService
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.engine.repository.ProcessDefinition
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class DefinitionService(private val repositoryService: RepositoryService,
                        private val formService: FormService) {

    fun getResource(key: String): String = getLatestDefinition(key)
            ?.let { repositoryService.getDeploymentResources(it.deploymentId)[0] }
            ?.let { repositoryService.getResourceAsStream(it.deploymentId, it.name) }
            ?.asString()
            ?: throw IllegalArgumentException()

    fun create(name: String, xml: String) {
        require(!definitionDeployed(name))
        updateOrCreate(name, xml)
    }

    fun update(key: String, xml: String) {
        require(definitionDeployed(key))
        updateOrCreate(key, xml)
    }

    fun getForm(key: String) = getLatestDefinition(key)
            ?.let { formService.getStartFormData(it.id) }
            ?.formFields
            ?: throw IllegalArgumentException()

    fun getLatestDefinition(key: String): ProcessDefinition? {
        return repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey(key)
                .latestVersion()
                .singleResult()
    }

    private fun updateOrCreate(key: String, xml: String) {
        repositoryService
                .createDeployment()
                .addString("${key}.bpmn", xml)
                .name(key)
                .enableDuplicateFiltering(true)
                .deploy()
    }

    private fun definitionDeployed(key: String) = getLatestDefinition(key) != null

    private fun InputStream.asString() = this.bufferedReader().use { it.readText() }

}