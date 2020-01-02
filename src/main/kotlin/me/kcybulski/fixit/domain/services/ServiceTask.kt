package me.kcybulski.fixit.domain.services

import org.camunda.bpm.engine.delegate.Expression
import org.camunda.bpm.engine.delegate.JavaDelegate

interface ServiceTask: JavaDelegate{

    fun name(): String = javaClass.simpleName
            .toLowerCase()
            .removeSuffix("servicetask")

    fun getFields(): List<Field> = javaClass
            .declaredFields
            .filter { it.type == Expression::class.java }
            .map { Field(it.name, it.name) }
}