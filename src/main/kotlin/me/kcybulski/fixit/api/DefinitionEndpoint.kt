package me.kcybulski.fixit.api

import me.kcybulski.fixit.domain.DefinitionService
import org.springframework.http.MediaType.APPLICATION_XML_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("definitions")
class DefinitionEndpoint(private val definitionService: DefinitionService) {

    @GetMapping("{key}", produces = [APPLICATION_XML_VALUE])
    fun getDefinition(@PathVariable key: String) = definitionService.getResource(key)

    @PostMapping("{key}", consumes = [APPLICATION_XML_VALUE])
    fun updateDefinition(@PathVariable key: String, @RequestBody body: String) = definitionService.update(key, body)

    @GetMapping("{key}/form")
    fun getForm(@PathVariable key: String) = definitionService.getForm(key)

}