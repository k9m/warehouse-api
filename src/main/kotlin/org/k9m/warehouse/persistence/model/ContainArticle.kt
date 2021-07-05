package org.k9m.warehouse.persistence.model

import javax.persistence.*

@Entity
data class ContainArticle(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    val amount: Int,

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    val article: Article? = null,

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    val product: Product? = null

)