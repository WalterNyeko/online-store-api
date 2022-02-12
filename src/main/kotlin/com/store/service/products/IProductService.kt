package com.store.service.products

import com.store.dto.ActionResponse
import com.store.dto.ProductCategoryDto
import com.store.dto.ProductDto
import com.store.dto.RecordHolder

interface IProductService {

    //Product Categories
    fun createProductCategory(productCategoryDto: ProductCategoryDto?): ActionResponse?
    fun editProductCategory(productCategoryDto: ProductCategoryDto?, id: Int?): ActionResponse?
    fun deleteProductCategory(id: Int?): ActionResponse?
    fun getProductCategories(): RecordHolder<List<ProductCategoryDto>>

    //Products
    fun createProduct(productDto: ProductDto?): ActionResponse?
    fun editProduct(productDto: ProductDto?, id: Int?): ActionResponse?
    fun deleteProduct(id: Int?): ActionResponse?
    fun getProducts(): RecordHolder<List<ProductDto>>
}