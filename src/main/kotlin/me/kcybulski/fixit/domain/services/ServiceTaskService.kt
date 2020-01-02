package me.kcybulski.fixit.domain.services

import org.springframework.stereotype.Service

@Service
class ServiceTaskService(private val serviceTasks: Set<ServiceTask>) {

    fun getServiceTasks() = serviceTasks

}