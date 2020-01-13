package me.kcybulski.fixit.bpmn.service

import me.kcybulski.fixit.domain.emails.EmailRequest
import me.kcybulski.fixit.domain.emails.EmailSender
import me.kcybulski.fixit.domain.services.ServiceTask
import me.kcybulski.fixit.domain.services.ServiceTaskField
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.Expression
import org.springframework.stereotype.Service

@Service("email")
class EmailServiceTask(private val emailSender: EmailSender): ServiceTask {

    @field:ServiceTaskField("Email odbiorcy")
    lateinit var receiverEmail: Expression

    @field:ServiceTaskField("Nazwa odbiorcy")
    lateinit var receiverName: Expression

    @field:ServiceTaskField("Temat")
    lateinit var subject: Expression

    @field:ServiceTaskField("Tekst")
    lateinit var text: Expression

    override fun execute(execution: DelegateExecution) {
        this.emailSender.sendEmail(getRequest(execution))
    }

    private fun getRequest(execution: DelegateExecution) = EmailRequest(
            receiverEmail.toString(execution),
            receiverName.toString(execution),
            subject.toString(execution),
            text.toString(execution)
    )

}

fun Expression.toString(execution: DelegateExecution) = getValue(execution) as String