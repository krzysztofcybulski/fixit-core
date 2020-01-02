package me.kcybulski.fixit.bpmn.service

import me.kcybulski.fixit.domain.services.ServiceTask
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.Expression
import org.springframework.stereotype.Service

@Service("debug")
class DebugServiceTask: ServiceTask {

    lateinit var text: Expression

    override fun execute(execution: DelegateExecution) {
        println(text.getValue(execution))
    }

}