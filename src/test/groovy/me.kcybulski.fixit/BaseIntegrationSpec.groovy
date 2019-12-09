package me.kcybulski.fixit


import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseIntegrationSpec extends Specification {

    @LocalServerPort
    private int port

    def restTemplate = new TestRestTemplate()

    def localUrl(String endpoint) {
        return "http://localhost:$port/" + endpoint
    }

}