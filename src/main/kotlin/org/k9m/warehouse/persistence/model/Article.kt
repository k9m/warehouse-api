package org.k9m.warehouse.persistence.model

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Article(
    @Id
    val id: Long,
    val name: String,
    val stock: Int,

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "article")
    val product: List<ContainArticle>? = null

)