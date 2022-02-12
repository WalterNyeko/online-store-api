package com.store.repository.products

import com.store.models.ProductCategory
import org.springframework.data.jpa.repository.JpaRepository

interface ProductCategoryRepository: JpaRepository<ProductCategory, Int> {
}