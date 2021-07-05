package org.k9m.warehouse.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.k9m.warehouse.api.model.ProductDto
import org.k9m.warehouse.persistence.ArticleRepository
import org.k9m.warehouse.persistence.model.Article
import org.k9m.warehouse.service.ProductService
import org.k9m.warehouse.util.FileUtil
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class WarehouseInitialiser(
    val objectMapper: ObjectMapper,
    val articleRepository: ArticleRepository,
    val productService: ProductService
) {

    @PostConstruct
    private fun initWarehouse(){
        val inventory: List<Article> = objectMapper.readValue(FileUtil().readFileToString("classpath:data/inventory.json"))
        inventory.forEach { articleRepository.save(it) }

        val products: List<ProductDto> = objectMapper.readValue(FileUtil().readFileToString("classpath:data/products.json"))
        products.forEach { productService.saveProduct(it) }
    }
}