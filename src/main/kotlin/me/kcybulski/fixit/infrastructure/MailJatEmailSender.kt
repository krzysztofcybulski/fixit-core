package me.kcybulski.fixit.infrastructure

import com.mailjet.client.MailjetClient
import com.mailjet.client.MailjetRequest
import com.mailjet.client.resource.Emailv31
import me.kcybulski.fixit.domain.EmailRequest
import me.kcybulski.fixit.domain.EmailSender
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class MailJatEmailSender(private val client: MailjetClient) : EmailSender {

    override fun sendEmail(request: EmailRequest) {
        this.client.post(buildRequest(request))
    }

    private fun buildRequest(request: EmailRequest) = MailjetRequest(Emailv31.resource)
            .property(Emailv31.MESSAGES, JSONArray()
                    .put(JSONObject()
                            .put(Emailv31.Message.FROM, buildFromHeader(request))
                            .put(Emailv31.Message.TO, buildToHeader(request))
                            .put(Emailv31.Message.SUBJECT, request.subject)
                            .put(Emailv31.Message.TEXTPART, request.text)
                    )
            )

    private fun buildToHeader(request: EmailRequest): JSONArray = JSONArray()
            .put(JSONObject()
                    .put("Email", request.receiverEmail)
                    .put("Name", request.receiverName))

    private fun buildFromHeader(request: EmailRequest): JSONObject = JSONObject()
            .put("Email", request.senderEmail)
            .put("Name", request.senderName)

}