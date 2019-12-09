package me.kcybulski.fixit.bpmn.service

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate

class DebugServiceTask: JavaDelegate {

    override fun execute(execution: DelegateExecution) {
        println(execution.getVariable("image"))
    }

}