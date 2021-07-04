package org.k9m.warehouse.it.steps

import io.cucumber.datatable.DataTable
import io.cucumber.java.DataTableType
import org.k9m.warehouse.api.model.ArticleDto

@DataTableType
fun articleDto(table: DataTable): List<ArticleDto> {
    return table.asMaps().map { row ->
        ArticleDto(
            id = row["id"]?.toLong(),
            name = row["name"]!!,
            stock = row["stock"]!!.toLong(),
        )
    }
}