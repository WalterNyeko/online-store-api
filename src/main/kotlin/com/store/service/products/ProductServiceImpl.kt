package com.store.service.products

import com.store.dto.*
import com.store.helpers.utils.Assembler
import com.store.repository.products.ProductCategoryRepository
import com.store.repository.products.ProductRepository
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class ProductServiceImpl(
    private val productCategoryRepository: ProductCategoryRepository,
    private val productRepository: ProductRepository
): IProductService {

    override fun createProductCategory(productCategoryDto: ProductCategoryDto?): ActionResponse? {
        val productCategory = productCategoryDto?.let {
            Assembler.assembleProductCategory(it)
        }?.let { productCategoryRepository.save(it) }
        return ActionResponse(resourceId = productCategory?.id)
    }

    override fun editProductCategory(productCategoryDto: ProductCategoryDto?, id: Int?): ActionResponse? {
        TODO("Not yet implemented")
    }

    override fun deleteProductCategory(id: Int?): ActionResponse? {
        TODO("Not yet implemented")
    }

    override fun getProductCategories(): RecordHolder<List<ProductCategoryDto>> {
        val productRepositories : List<ProductCategoryDto?> = productCategoryRepository.findAll().stream()
            .map { Assembler.assembleProductCategoryDto(it) }.collect(Collectors.toList())
        return RecordHolder(totalRecords = productRepositories.size, records = productRepositories as List<ProductCategoryDto>)
    }

    override fun createProduct(productDto: ProductDto?): ActionResponse? {
        val productCategory = productDto?.productCategory?.id?.let { productCategoryRepository.findById(it).orElseThrow() }
        productDto?.productCategory = productCategory?.let { Assembler.assembleProductCategoryDto(it) }
        val product = productDto?.let {
            Assembler.assembleProduct(it)
        }?.let { productRepository.save(it) }

        return ActionResponse(resourceId = product?.id)
    }

    override fun editProduct(productDto: ProductDto?, id: Int?): ActionResponse? {
        TODO("Not yet implemented")
    }

    override fun deleteProduct(id: Int?): ActionResponse? {
        TODO("Not yet implemented")
    }

    override fun getProducts(): RecordHolder<List<ProductDto>> {
        val products : List<ProductDto?> = productRepository.findAll().stream()
            .map { Assembler.assembleProductDto(it) }.collect(Collectors.toList())
        return RecordHolder(totalRecords = products.size, records = products as List<ProductDto>)
    }
}