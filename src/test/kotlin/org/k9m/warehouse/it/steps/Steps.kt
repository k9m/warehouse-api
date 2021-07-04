package org.k9m.warehouse.it.steps

import io.cucumber.java.Before
import io.cucumber.java.en.When
import org.assertj.core.api.Assertions.assertThat
import org.k9m.warehouse.util.log
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject


class Steps {

    @LocalServerPort
    private val serverPort: Int = 0

    data class Health(val status: String)

    private var restTemplate = RestTemplate()

    @When("^Health endpoint is available on (.*)$")
    fun theSystemStarts(healthEndpoint: String) {
        val response: Health = restTemplate.getForObject("http://localhost:$serverPort/$healthEndpoint")

        assertThat(response.status).isEqualTo("UP")
        log.info("System started");
    }
}