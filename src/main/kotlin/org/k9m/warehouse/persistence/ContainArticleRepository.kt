package org.k9m.warehouse.persistence

import org.k9m.warehouse.persistence.model.ContainArticle
import org.springframework.data.repository.CrudRepository

interface ContainArticleRepository : CrudRepository<ContainArticle, Long>