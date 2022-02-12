package com.store.controller.products

import com.store.dto.ActionResponse
import com.store.dto.ProductCategoryDto
import com.store.dto.ProductDto
import com.store.dto.RecordHolder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

interface IProductController {

    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    fun createProductCategory(
        @RequestBody productCategoryDto: ProductCategoryDto?
    ): ActionResponse?

    @PutMapping("/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun editProductCategory(
        @RequestBody productCategoryDto: ProductCategoryDto?,
        @PathVariable id: Int?
    ): ActionResponse?

    @DeleteMapping("/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteProductCategory(
        @PathVariable id: Int?
    ): ActionResponse?

    @GetMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    fun getProductCategories(): RecordHolder<List<ProductCategoryDto>>

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(
        @RequestBody productDto: ProductDto?
    ): ActionResponse?

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun editProduct(
        @RequestBody productDto: ProductDto?,
        @PathVariable id: Int?
    ): ActionResponse?

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteProduct(
        @PathVariable id: Int?
    ): ActionResponse?

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getProducts(): RecordHolder<List<ProductDto>>
}