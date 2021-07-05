package org.k9m.warehouse.persistence

import org.k9m.warehouse.persistence.model.Product
import org.springframework.data.repository.CrudRepository

interface ProductRepository : CrudRepository<Product, Long>