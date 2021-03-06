package org.k9m.warehouse.it.steps

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.assertj.core.api.Assertions.assertThat
import org.k9m.warehouse.api.model.ArticleDto
import org.k9m.warehouse.api.model.ErrorObjectDto
import org.k9m.warehouse.api.model.ProductDto
import org.k9m.warehouse.api.model.SellRequestDto
import org.k9m.warehouse.persistence.ArticleRepository
import org.k9m.warehouse.persistence.ContainArticleRepository
import org.k9m.warehouse.persistence.ProductRepository
import org.k9m.warehouse.persistence.converters.toModel
import org.k9m.warehouse.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.net.URI


class Steps {

    @LocalServerPort
    private val serverPort: Int = 0

    data class Health(val status: String)

    private val restTemplate = RestTemplate()

    @Autowired private lateinit var containsArticleRepository: ContainArticleRepository
    @Autowired private lateinit var articleRepository: ArticleRepository
    @Autowired private lateinit var productRepository: ProductRepository
    @Autowired private lateinit var productService: ProductService
    @Autowired private lateinit var objectMapper: ObjectMapper

    private var lastThrownClientException: HttpClientErrorException? = null
    private var lastThrownException: Exception? = null
    private var saleResponse: SellRequestDto? = null


    @When("^Health endpoint is available on (.*)$")
    fun theSystemStarts(healthEndpoint: String) {
        val response: Health = restTemplate.getForObject("http://localhost:$serverPort/$healthEndpoint")
        assertThat(response.status).isEqualTo("UP")
    }

    @Then("^the database is empty$")
    fun emptyInventory() {
        containsArticleRepository.deleteAll()
        productRepository.deleteAll()
        articleRepository.deleteAll()
    }

    @Then("^these articles are added to the inventory:$")
    fun saveArticles(articles: List<ArticleDto>) {
        articles.forEach { articleRepository.save(it.toModel()) }
    }

    @Then("^these products are created:$")
    fun saveProducts(productsStr: String) {
        val products: List<ProductDto> = objectMapper.readValue(productsStr)
        try {
            products.forEach { productService.saveProduct(it) }
        } catch (e: Exception) {
            lastThrownException = e
        }
    }

    @Then("^calling /articles endpoint should return:$")
    fun retrieveArticles(expectedArticles: List<ArticleDto>) {
        val requestEntity = RequestEntity<Any>(HttpMethod.GET, URI.create("http://localhost:$serverPort/v1/articles"))
        val articles: List<ArticleDto> = restTemplate.exchange(requestEntity, object: ParameterizedTypeReference<List<ArticleDto>>(){}).body!!
        assertThat(articles).containsAll(expectedArticles)
    }

    @Then("^calling /products endpoint should return:$")
    fun retrieveArticles(productsStr: String) {
        val requestEntity = RequestEntity<Any>(HttpMethod.GET, URI.create("http://localhost:$serverPort/v1/products"))
        val expectedProducts: List<ProductDto> = objectMapper.readValue(productsStr)
        val products: List<ProductDto> = restTemplate.exchange(requestEntity, object: ParameterizedTypeReference<List<ProductDto>>(){}).body!!
        assertThat(products).containsAll(expectedProducts)
    }


    @When("^a sale is requested with these details:$")
    fun requestSale(saleRequests: List<SellRequestDto>) {
        val saleRequest = saleRequests.first()
        val requestEntity = RequestEntity<SellRequestDto>(saleRequest, HttpMethod.POST, URI.create("http://localhost:$serverPort/v1/products/sell"))
        try {
            saleResponse =  restTemplate.exchange(requestEntity, object: ParameterizedTypeReference<SellRequestDto>(){}).body!!
        }
        catch (e: HttpClientErrorException) {
            lastThrownClientException = e
        }
    }

    @Then("^this sale sale response should be received:$")
    fun assertSaleResponse(saleRequests: List<SellRequestDto>) {
        val saleRequest = saleRequests.first()
        assertThat(saleRequest).isEqualTo(saleResponse)
    }

    @Then("^a client error should be returned with message: (.*) and status code (\\d+)$")
    fun clientErrorReceived(message: String, statusCode: Int) {
        assertThat(lastThrownClientException).isNotNull
        val errorObject: ErrorObjectDto = objectMapper.readValue(lastThrownClientException!!.responseBodyAsString, ErrorObjectDto::class.java)

        assertThat(errorObject.message).isEqualTo(message)
        assertThat(errorObject.statusCode).isEqualTo(statusCode)
    }

    @Then("^an error should be returned with message: (.*)$")
    fun errorReceived(message: String) {
        assertThat(lastThrownException).isNotNull
        assertThat(lastThrownException!!.message).isEqualTo(message)
    }

}