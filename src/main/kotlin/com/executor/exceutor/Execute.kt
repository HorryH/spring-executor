package com.executor.exceutor

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import java.net.URLDecoder

data class Request @JsonCreator constructor(val data: String)

data class Response(val content: String)

@RestController
class MainController {

    @PostMapping("/java")
    fun execute(@RequestBody() body: String): Response {
        val mapper = jacksonObjectMapper()
        val input =  URLDecoder.decode(body, "UTF-8")
        val request = mapper.readValue<Request>(input)
        return Response(glotRequest("java")!!)
    }

    private fun glotRequest(language: String) : String? {
        val uri = "https://run.glot.io/languages/$language/latest"
        val rest = RestTemplate()
        return rest.getForObject(uri, String::class.java)
    }
}