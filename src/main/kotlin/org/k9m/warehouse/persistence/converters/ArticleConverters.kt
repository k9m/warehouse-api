package org.k9m.warehouse.persistence.converters

import org.k9m.warehouse.api.model.ArticleDto
import org.k9m.warehouse.persistence.model.Article

fun ArticleDto.toModel(): Article = Article(
    this.id,
    this.name,
    this.stock
)

fun Article.toDto(): ArticleDto = ArticleDto(
    id = this.id,
    name = this.name,
    stock = this.stock
)