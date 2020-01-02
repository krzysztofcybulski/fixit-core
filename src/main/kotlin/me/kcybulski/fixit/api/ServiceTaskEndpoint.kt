package me.kcybulski.fixit.api

import me.kcybulski.fixit.domain.services.Field
import me.kcybulski.fixit.domain.services.ServiceTask
import me.kcybulski.fixit.domain.services.ServiceTaskService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("services")
class ServiceTaskEndpoint(private val serviceTaskService: ServiceTaskService) {

    @GetMapping
    fun getServices(): List<ServiceTaskResponse> = serviceTaskService.getServiceTasks()
            .map { it.response() }

}

fun ServiceTask.response() = ServiceTaskResponse(name(), getFields().map { it.response() })
fun Field.response() = FieldResponse(id, name)

data class ServiceTaskResponse(val name: String, val fields: List<FieldResponse>)
data class FieldResponse(val id: String, val name: String)
