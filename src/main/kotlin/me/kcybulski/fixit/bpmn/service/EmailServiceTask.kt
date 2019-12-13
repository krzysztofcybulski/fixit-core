package me.kcybulski.fixit.bpmn.service

import me.kcybulski.fixit.bpmn.SpringContext
import me.kcybulski.fixit.domain.EmailRequest
import me.kcybulski.fixit.domain.EmailSender
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate

class EmailServiceTask: JavaDelegate {

    private lateinit var senderEmail: String
    private lateinit var senderName: String
    private lateinit var receiverEmail: String
    private lateinit var receiverName: String
    private lateinit var subject: String
    private lateinit var text: String

    override fun execute(execution: DelegateExecution) {
        SpringContext.getBean(EmailSender::class.java).sendEmail(
                EmailRequest(senderEmail, senderName, receiverEmail, receiverName, subject, text)
        )
    }

}