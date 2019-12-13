package me.kcybulski.fixit.domain

interface EmailSender {

    fun sendEmail(request: EmailRequest)

}