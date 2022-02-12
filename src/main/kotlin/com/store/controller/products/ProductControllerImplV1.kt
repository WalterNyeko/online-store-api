package com.store.controller.products

import com.store.dto.ActionResponse
import com.store.dto.ProductCategoryDto
import com.store.dto.ProductDto
import com.store.dto.RecordHolder
import com.store.service.products.ProductServiceImpl
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/products")
class ProductControllerImplV1(
    private val productService: ProductServiceImpl
): IProductController {

    override fun createProductCategory(productCategoryDto: ProductCategoryDto?): ActionResponse? {
        return productService.createProductCategory(productCategoryDto)
    }

    override fun editProductCategory(productCategoryDto: ProductCategoryDto?, id: Int?): ActionResponse? {
        TODO("Not yet implemented")
    }

    override fun deleteProductCategory(id: Int?): ActionResponse? {
        TODO("Not yet implemented")
    }

    override fun getProductCategories(): RecordHolder<List<ProductCategoryDto>> {
        return productService.getProductCategories()
    }

    override fun createProduct(productDto: ProductDto?): ActionResponse? {
        return productService.createProduct(productDto)
    }

    override fun editProduct(productDto: ProductDto?, id: Int?): ActionResponse? {
        TODO("Not yet implemented")
    }

    override fun deleteProduct(id: Int?): ActionResponse? {
        TODO("Not yet implemented")
    }

    override fun getProducts(): RecordHolder<List<ProductDto>> {
        return productService.getProducts()
    }
}