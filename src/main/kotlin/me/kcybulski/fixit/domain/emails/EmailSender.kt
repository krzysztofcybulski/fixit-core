package me.kcybulski.fixit.domain.emails

interface EmailSender {

    fun sendEmail(request: EmailRequest)

}