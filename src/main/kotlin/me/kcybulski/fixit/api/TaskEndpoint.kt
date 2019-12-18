package me.kcybulski.fixit.api

import me.kcybulski.fixit.domain.processes.MiniTask
import me.kcybulski.fixit.domain.processes.Task
import me.kcybulski.fixit.domain.processes.UserTaskService
import org.camunda.bpm.engine.form.FormField
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("tasks")
class TaskEndpoint(private val userTaskService: UserTaskService) {

    @GetMapping
    fun getTasks(authentication: Authentication) = userTaskService
            .getUserTasks(authentication)
            .map { it.response() }

    @GetMapping("{id}")
    fun getTask(@PathVariable id: String, authentication: Authentication): ResponseEntity<TaskResponse> = userTaskService
            .getUserTask(id, authentication)
            ?.response()
            ?.let{ ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()


    @PostMapping("{id}")
    fun completeTask(@PathVariable id: String, @RequestBody request: TaskRequest, authentication: Authentication) = userTaskService
            .completeTask(authentication, id, request.variables)

}

data class TaskRequest(val variables: Map<String, Any>)

data class TaskResponse(val id: String, val name: String, val variables: Map<String, Any> = emptyMap(),
                        val form: List<FormField> = emptyList(), val priority: Int = 0, val photo: String? = "")

private fun MiniTask.response() = TaskResponse(id, name, photo = photo, priority = priority)

private fun Task.response() = TaskResponse(id, name, variables, form, priority)