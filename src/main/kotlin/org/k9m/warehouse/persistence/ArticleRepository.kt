package org.k9m.warehouse.persistence

import org.k9m.warehouse.persistence.model.Article
import org.springframework.data.repository.CrudRepository

interface ArticleRepository : CrudRepository<Article, Long>