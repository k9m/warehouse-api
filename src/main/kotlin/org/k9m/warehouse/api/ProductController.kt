package org.k9m.warehouse.api

import org.k9m.warehouse.api.model.ProductDto
import org.k9m.warehouse.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(val productService: ProductService) : ProductApi {

    override fun getProducts(): ResponseEntity<List<ProductDto>>{
        return ResponseEntity.ok(productService.findAllProductsOnStock())
    }
}

