package me.kcybulski.fixit.config

import com.mailjet.client.ClientOptions
import com.mailjet.client.MailjetClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EmailConfiguration(private val majlejtConfiguration: MajljetConfiguration) {

    @Bean
    fun emailClient(): MailjetClient = MailjetClient(majlejtConfiguration.apiKey, majlejtConfiguration.apiSecret, ClientOptions("v3.1"))
}


@ConstructorBinding
@ConfigurationProperties("email.majljet")
data class MajljetConfiguration(val apiKey: String, val apiSecret: String)