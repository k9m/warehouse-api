package org.k9m.warehouse.it.steps

import io.cucumber.datatable.DataTable
import io.cucumber.java.DataTableType
import org.k9m.warehouse.api.model.ArticleDto
import org.k9m.warehouse.api.model.SellRequestDto

@DataTableType
fun articleDto(table: DataTable): List<ArticleDto> {
    return table.asMaps().map { row ->
        ArticleDto(
            id = row["id"]!!.toLong(),
            name = row["name"]!!,
            stock = row["stock"]!!.toInt(),
        )
    }
}

@DataTableType
fun sellRequestDto(table: DataTable): List<SellRequestDto> {
    return table.asMaps().map { row ->
        SellRequestDto(
            productId = row["productId"]!!.toLong(),
            quanityRequested = row["quantityRequested"]!!.toInt()
        )
    }
}