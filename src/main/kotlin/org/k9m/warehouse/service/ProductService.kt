package org.k9m.warehouse.service

import org.k9m.warehouse.api.model.ProductDto
import org.k9m.warehouse.persistence.ProductRepository
import org.k9m.warehouse.persistence.converters.toDto
import org.springframework.stereotype.Component

@Component
class ProductService(
    val productTransactions: ProductTransactions,
    val productRepository: ProductRepository) {

    fun saveProduct(productDto: ProductDto) : ProductDto{
        val product = productTransactions.saveProduct(productDto)
        return product.toDto(productTransactions.findAvailableQuantity(product))
    }

    fun findAllProductsOnStock(): List<ProductDto> {
        val products = productRepository.findAll()
        return products.map { it.toDto(productTransactions.findAvailableQuantity(it)) }
    }


}