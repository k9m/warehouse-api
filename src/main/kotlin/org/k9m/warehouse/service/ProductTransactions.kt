package org.k9m.warehouse.service

import org.k9m.warehouse.api.model.ProductDto
import org.k9m.warehouse.persistence.ArticleRepository
import org.k9m.warehouse.persistence.ContainArticleRepository
import org.k9m.warehouse.persistence.ProductRepository
import org.k9m.warehouse.persistence.converters.toModel
import org.k9m.warehouse.persistence.model.ContainArticle
import org.k9m.warehouse.persistence.model.Product
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class ProductTransactions(
    val articleRepository: ArticleRepository,
    val productRepository: ProductRepository,
    val containArticleRepository: ContainArticleRepository) {

    @Transactional(propagation = Propagation.SUPPORTS)
    fun saveProduct(productDto: ProductDto): Product {
        val product = productRepository.save(productDto.toModel())
        productDto.containArticles.forEach { ar ->
            val article = articleRepository.findById(ar.articleId).orElseThrow{ ArticleNotFoundException("Article not found with id: ${ar.articleId}") }
            containArticleRepository.save(ContainArticle(id = null, amount = ar.amount, product = product, article = article))
        }

        return productRepository.findById(product.id).orElseThrow{ ProductNotFoundException("Product not found with id: ${product.id}") }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    fun findAvailableQuantity(product: Product) : Long{
        val quantitiesAvailable = mutableListOf<Long>()
        product.containArticles?.forEach { ar ->
            val article = articleRepository.findById(ar.article!!.id).orElseThrow{ ArticleNotFoundException("Article not found with id: ${ar.article.id}") }
            quantitiesAvailable.add(article.stock / ar.amount)
        }

        return quantitiesAvailable.minOrNull()!!
    }

}