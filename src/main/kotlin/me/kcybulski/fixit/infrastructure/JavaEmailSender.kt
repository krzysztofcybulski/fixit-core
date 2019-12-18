package me.kcybulski.fixit.infrastructure

import me.kcybulski.fixit.domain.emails.EmailRequest
import me.kcybulski.fixit.domain.emails.EmailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service


@Service
class JavaEmailSender(private val javaMailSender: JavaMailSender): EmailSender {

    override fun sendEmail(request: EmailRequest) {
        val message = SimpleMailMessage()
        message.setTo(request.receiverEmail)
        message.setSubject(request.subject)
        message.setText(request.text)
        javaMailSender.send(message)
    }

}