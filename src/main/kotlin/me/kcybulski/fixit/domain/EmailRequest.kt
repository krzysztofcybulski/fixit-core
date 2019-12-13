package me.kcybulski.fixit.domain

data class EmailRequest(val senderEmail: String, val senderName: String,
                        val receiverEmail: String, val receiverName: String,
                        val subject: String, val text: String)