package org.k9m.warehouse.persistence.model

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Product(
    @Id
    val id: Long,
    val name: String,
    val price: Double,

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    val containArticles: List<ContainArticle>? = null
)