package me.kcybulski.fixit.domain.emails

data class EmailRequest(val receiverEmail: String, val receiverName: String,
                        val subject: String, val text: String)