package me.kcybulski.fixit.bpmn.service

import me.kcybulski.fixit.bpmn.SpringContext
import me.kcybulski.fixit.domain.EmailRequest
import me.kcybulski.fixit.domain.EmailSender
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.Expression
import org.camunda.bpm.engine.delegate.JavaDelegate

class EmailServiceTask: JavaDelegate {

    private lateinit var senderEmail: Expression
    private lateinit var senderName: Expression
    private lateinit var receiverEmail: Expression
    private lateinit var receiverName: Expression
    private lateinit var subject: Expression
    private lateinit var text: Expression

    override fun execute(execution: DelegateExecution) {
        SpringContext.getBean(EmailSender::class.java).sendEmail(getRequest(execution))
    }

    private fun getRequest(execution: DelegateExecution) = EmailRequest(
            senderEmail.toString(execution),
            senderName.toString(execution),
            receiverEmail.toString(execution),
            receiverName.toString(execution),
            subject.toString(execution),
            text.toString(execution))

}

fun Expression.toString(execution: DelegateExecution) = getValue(execution) as String