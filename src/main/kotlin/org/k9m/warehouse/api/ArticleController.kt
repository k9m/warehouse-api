package org.k9m.warehouse.api

import org.k9m.warehouse.api.model.ArticleDto
import org.k9m.warehouse.persistence.ArticleRepository
import org.k9m.warehouse.persistence.converters.toDto
import org.k9m.warehouse.persistence.converters.toModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(val articleRepository: ArticleRepository) : ArticleApi {

    override fun getArticles(): ResponseEntity<List<ArticleDto>>{
        return ResponseEntity.ok(articleRepository.findAll().map { it.toDto() })
    }

    override fun saveArticle(articleDto: ArticleDto): ResponseEntity<ArticleDto> {
        return ResponseEntity.ok(articleRepository.save(articleDto.toModel()).toDto())
    }
}

