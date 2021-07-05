package org.k9m.warehouse.persistence.converters

import org.k9m.warehouse.api.model.ContainArticlesDto
import org.k9m.warehouse.api.model.ProductDto
import org.k9m.warehouse.persistence.model.Product

fun ProductDto.toModel(): Product = Product(
    id = this.id,
    name = this.name,
    price = this.price
)

fun Product.toDto(quantityAvailable: Long): ProductDto = ProductDto(
    id = this.id,
    name = this.name,
    price = this.price,
    quantityAvailable = quantityAvailable,
    containArticles = this.containArticles?.let { ar -> ar.map { ContainArticlesDto(it.article!!.id, it.amount) } } ?: emptyList()
)
