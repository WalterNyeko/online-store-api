package com.store.repository.products

import com.store.models.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<Product, Int> {
}