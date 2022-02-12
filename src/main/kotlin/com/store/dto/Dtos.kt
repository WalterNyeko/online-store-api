package com.store.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import javax.validation.constraints.NotNull
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty

@NoArg
@AllOpen
data class ProductCategoryDto(
    val id: Int? = 0,
    val name: String? = "",
    val description: String? = ""
)

@NoArg
@AllOpen
data class ProductDto(
    val id: Int? = 0,
    val name: String? = "",
    val description: String? = "",
    val sellingPrice: BigDecimal? = BigDecimal.ZERO,
    var productCategory: ProductCategoryDto? = null
)

@NoArg
@AllOpen
data class OrderResponseDto(
    val id: Int,
    val status: String,
    val totalCost: BigDecimal,
    val discountedCost: BigDecimal,
    val orderDate: String,
    val orderedBy: Int
)

@NoArg
@AllOpen
data class CartItemDto(
    @field:NotNull val product: ProductDto,
    @field:NotNull val quantity: Int
)

@NoArg
@AllOpen
data class OrderDetailsDto(
    val id: Int? = 0,
    var orderId: Int? = null,
    @field:NotNull var product: ProductDto,
    @field:NotNull val quantity: Int,
    val orderDate: String? = null,
    @field:NotNull val orderedBy: Int
)

@NoArg
@AllOpen
data class OrderDto(
    @field:NotNull @field:NotEmpty var cartItems: List<CartItemDto>
)

@NoArg
@AllOpen
data class AffiliateDto(
    @field:NotNull @field:NotEmpty val affiliateNumber: String,
    @field:NotNull @field:NotEmpty val affiliateCommission: BigDecimal,
    val lineManager: String? = null,
    @field:NotNull
    @field:JsonFormat(shape = JsonFormat.Shape.STRING) val contractStartDate: String,
    @field:NotNull
    @field:JsonFormat(shape = JsonFormat.Shape.STRING) val contractEndDate: String
)

@NoArg
@AllOpen
data class EmployeeDto(
    @field:NotNull @field:NotEmpty val employeeNumber: String,
    val lineManager: String? = null,
    @field:NotNull
    @field:JsonFormat(shape = JsonFormat.Shape.STRING) val employmentDate: String,
    @field:NotNull
    @field:JsonFormat(shape = JsonFormat.Shape.STRING) val contractExpiryDate: String
)

@NoArg
@AllOpen
data class LoginDto(
    @field:NotNull @field:NotEmpty val username: String,
    @field:NotNull @field:NotEmpty val password: String
)

@NoArg
@AllOpen
@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserDto(
    val id: Int?,
    val firstName: String? = null,
    val lastName: String? = null,
    @field:NotNull @field:NotEmpty val username: String,
    @field:NotNull @field:NotEmpty val password: String?,
    val dateOfBirth: String? = null,
    val idType: String? = null,
    val idNumber: String? = null,
    val address: String? = null,
    @field:NotNull @field:NotEmpty val email: String,
    val phoneNumber: String? = null,
    val employee: EmployeeDto? = null,
    val affiliate: AffiliateDto? = null,
    var token: String? = null
)

@NoArg
@AllOpen
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ActionResponse(
    val message: String? = null,
    val resourceId: Int? = null
)