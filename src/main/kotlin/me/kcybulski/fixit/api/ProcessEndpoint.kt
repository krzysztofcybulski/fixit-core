package me.kcybulski.fixit.api

import me.kcybulski.fixit.domain.MiniProcessInstance
import me.kcybulski.fixit.domain.ProcessInstance
import me.kcybulski.fixit.domain.ProcessService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("processes")
class ProcessesEndpoint(private val processService: ProcessService) {

    @GetMapping
    fun getList(): List<MiniProcessResponse> = processService.listAll()
            .map { it.response() }

    @GetMapping("{id}")
    fun getProcess(@PathVariable id: String): ResponseEntity<ProcessResponse> = processService.get(id)
            ?.response()
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PostMapping
    fun startProcess(@RequestBody request: ProcessRequest) = processService
            .startProcess(request.definitionKey, request.variables, request.businessKey)
            .response()

}

data class ProcessRequest(val definitionKey: String, val variables: Map<String, Any>, val businessKey: String?)

data class MiniProcessResponse(val id: String, val status: String, val progress: Int, val assignee: String?, val photo: String?, val businessKey: String?)

data class ProcessResponse(val id: String, val variables: Map<String, Any>, val businessKey: String?)

private fun MiniProcessInstance.response() = MiniProcessResponse(id, status, progress, assignee, photo, businessKey)

private fun ProcessInstance.response() = ProcessResponse(id, variables, businessKey)