package org.k9m.warehouse.service

import org.k9m.warehouse.api.model.ProductDto
import org.k9m.warehouse.persistence.ArticleRepository
import org.k9m.warehouse.persistence.ContainArticleRepository
import org.k9m.warehouse.persistence.ProductRepository
import org.k9m.warehouse.persistence.converters.toDto
import org.k9m.warehouse.persistence.converters.toModel
import org.k9m.warehouse.persistence.model.ContainArticle
import org.springframework.stereotype.Component

@Component
class ProductService(
    val articleRepository: ArticleRepository,
    val productRepository: ProductRepository,
    val containArticleRepository: ContainArticleRepository) {

    fun saveProduct(productDto: ProductDto) : ProductDto{
        val containArticles = mutableListOf<ContainArticle>()
        val product = productRepository.save(productDto.toModel())
        productDto.containArticles.forEach { ar ->
            val article = articleRepository.findById(ar.articleId).orElseThrow{ ArticleNotFoundException("Error") }
            val containArticle = containArticleRepository.save(ContainArticle(id = null, amount = ar.amount, product = product, article = article))
            containArticles.add(containArticle)
        }

        return productRepository.findById(product.id!!).orElseThrow{ ProductNotFoundException("Error") }.toDto()
    }

    fun findAll(): List<ProductDto> = productRepository.findAll().map { it.toDto() }


}