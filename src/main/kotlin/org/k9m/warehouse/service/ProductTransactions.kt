package org.k9m.warehouse.service

import org.k9m.warehouse.api.model.ProductDto
import org.k9m.warehouse.api.model.SellRequestDto
import org.k9m.warehouse.persistence.ArticleRepository
import org.k9m.warehouse.persistence.ContainArticleRepository
import org.k9m.warehouse.persistence.ProductRepository
import org.k9m.warehouse.persistence.converters.toModel
import org.k9m.warehouse.persistence.model.Article
import org.k9m.warehouse.persistence.model.ContainArticle
import org.k9m.warehouse.persistence.model.Product
import org.k9m.warehouse.service.exception.ArticleNotFoundException
import org.k9m.warehouse.service.exception.NoStockException
import org.k9m.warehouse.service.exception.ProductNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ProductTransactions(
    private val articleRepository: ArticleRepository,
    private val productRepository: ProductRepository,
    private val containArticleRepository: ContainArticleRepository) {

    @Transactional
    fun saveProduct(productDto: ProductDto): Product {
        val product = productRepository.save(productDto.toModel())
        productDto.containArticles.forEach { ar ->
            val article = articleRepository.findById(ar.articleId).orElseThrow{ ArticleNotFoundException("Article not found with id: ${ar.articleId}") }
            containArticleRepository.save(ContainArticle(id = null, amount = ar.amount, product = product, article = article))
        }

        return productRepository.findById(product.id).orElseThrow{ ProductNotFoundException("Product not found with id: ${product.id}") }
    }

    @Transactional
    fun findAvailableQuantity(product: Product) : Int?{
        val quantitiesAvailable = mutableListOf<Int>()
        product.containArticles?.forEach { ar ->
            val article = articleRepository.findById(ar.article!!.id).orElseThrow{ ArticleNotFoundException("Article not found with id: ${ar.article.id}") }
            quantitiesAvailable.add(article.stock / ar.amount)
        }

        return quantitiesAvailable.minOrNull()
    }

    @Transactional(rollbackFor = [ProductNotFoundException::class, ArticleNotFoundException::class, NoStockException::class])
    fun sellProductIfAvailable(sellRequestDto: SellRequestDto){
        val productId = sellRequestDto.productId
        val product = productRepository.findById(productId).orElseThrow{ ProductNotFoundException("Product not found with id: $productId") }
        product.containArticles?.forEach { ar ->
            val article = articleRepository.findById(ar.article!!.id).orElseThrow{ ArticleNotFoundException("Article not found with id: ${ar.article.id}") }
            val nrArticlesNeeded = sellRequestDto.quanityRequested * ar.amount
            if(nrArticlesNeeded > article.stock){
                throw NoStockException("Not enough stock for article with id: ${article.id} ($nrArticlesNeeded/${article.stock} needed/have)")
            }
            else{
                val updatedArticle = Article(article.id, article.name, article.stock - nrArticlesNeeded)
                articleRepository.save(updatedArticle)
            }
        }
    }

}