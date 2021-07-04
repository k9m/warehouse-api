package org.k9m.warehouse.it.steps

import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.assertj.core.api.Assertions.assertThat
import org.k9m.warehouse.api.model.ArticleDto
import org.k9m.warehouse.persistence.ArticleRepository
import org.k9m.warehouse.persistence.converters.toModel
import org.k9m.warehouse.util.log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.net.URI


class Steps {

    @LocalServerPort
    private val serverPort: Int = 0

    data class Health(val status: String)

    private val restTemplate = RestTemplate()

    @Autowired private lateinit var articleRepository: ArticleRepository


    @When("^Health endpoint is available on (.*)$")
    fun theSystemStarts(healthEndpoint: String) {
        val response: Health = restTemplate.getForObject("http://localhost:$serverPort/$healthEndpoint")

        assertThat(response.status).isEqualTo("UP")
        log.info("System started");
    }

    @Then("^the inventory is empty$")
    fun emptyInventory() {
        articleRepository.deleteAll()
    }

    @Then("^these articles are added to the inventory:$")
    fun saveArticles(articlesRequest: List<ArticleDto>) {
        articlesRequest.forEach { articleRepository.save(it.toModel()) }
    }

    @Then("^calling /articles endpoint should return:$")
    fun retrieveArticles(expectedArticles: List<ArticleDto>) {
        val requestEntity = RequestEntity<Any>(HttpMethod.GET, URI.create("http://localhost:$serverPort/v1/articles"))
        val articles: List<ArticleDto> = restTemplate.exchange(requestEntity, object: ParameterizedTypeReference<List<ArticleDto>>(){}).body!!
        assertThat(articles).containsAll(expectedArticles)
    }

}