package com.store.helpers.utils

import com.store.dto.*
import com.store.helpers.constants.ErrorConstants
import com.store.helpers.enums.IdType
import com.store.models.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.text.SimpleDateFormat
import java.util.*

class Assembler {
    companion object {
        private val formatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        fun assembleUser(userDto: UserDto) : User {
            return User(
                id = 0,
                username = userDto.username,
                password = BCryptPasswordEncoder().encode(userDto.password!!),
                firstName = userDto.firstName!!,
                lastName = userDto.lastName!!,
                dateOfBirth = formatter.parse(userDto.dateOfBirth),
                email = userDto.email,
                idType = IdType.valueOf(userDto.idType!!),
                idNumber = userDto.idNumber!!,
                phoneNumber = userDto.phoneNumber!!,
                address = userDto.address!!,
                employee = userDto.employee?.let { assembleEmployee(it) },
                affiliate = userDto.affiliate?.let { assembleAffiliate(it) },
                createdDate = Date()
            )
        }

        fun assembleUserDto(user: User) : UserDto {
            return UserDto(
                id = user.id,
                username = user.username,
                password = null,
                firstName = user.firstName!!,
                lastName = user.lastName!!,
                dateOfBirth = formatter.format(user.dateOfBirth),
                email = user.email,
                idType = user.idType.toString(),
                idNumber = user.idNumber!!,
                phoneNumber = user.phoneNumber!!,
                address = user.address!!,
                employee = user.employee?.let { assembleEmployeeDto(it) },
                affiliate = user.affiliate?.let { assembleAffiliateDto(it) }
            )
        }

        fun assembleAuthError() : APIError {
            return APIError(
                message = ErrorConstants.PROVIDE_VALID_CREDENTIALS,
                reason = ErrorConstants.INVALID_CREDENTIALS
            )
        }

        private fun assembleAffiliate(affiliateDto: AffiliateDto) : Affiliate {
            return Affiliate(
                id = 0,
                affiliateNumber = affiliateDto.affiliateNumber,
                affiliateCommission =  affiliateDto.affiliateCommission,
                lineManager = affiliateDto.lineManager,
                contractStartDate = formatter.parse(affiliateDto.contractStartDate),
                contractEndDate = formatter.parse(affiliateDto.contractEndDate),
                user = UserContext.getLoggedInUser()
            )
        }

        private fun assembleEmployee(employeeDto: EmployeeDto) : Employee {
            return Employee(
                id = 0,
                employeeNumber = employeeDto.employeeNumber,
                lineManager = employeeDto.lineManager,
                employmentDate = formatter.parse(employeeDto.employmentDate),
                contractExpiryDate = formatter.parse(employeeDto.contractExpiryDate),
                user = UserContext.getLoggedInUser()
            )
        }

        private fun assembleAffiliateDto(affiliate: Affiliate) : AffiliateDto {
            return AffiliateDto(
                affiliateNumber = affiliate.affiliateNumber,
                affiliateCommission =  affiliate.affiliateCommission,
                lineManager = affiliate.lineManager,
                contractStartDate = formatter.format(affiliate.contractStartDate).toString(),
                contractEndDate = formatter.format(affiliate.contractEndDate).toString()
            )
        }

        private fun assembleEmployeeDto(employee: Employee) : EmployeeDto {
            return EmployeeDto(
                employeeNumber = employee.employeeNumber,
                lineManager = employee.lineManager,
                employmentDate = formatter.format(employee.employmentDate).toString(),
                contractExpiryDate = formatter.format(employee.contractExpiryDate).toString()
            )
        }

        fun assembleProductCategory(productCategoryDto: ProductCategoryDto) : ProductCategory {
            return ProductCategory(
                id = (if (productCategoryDto?.id!! > 0) productCategoryDto?.id else 0)!!,
                name = productCategoryDto?.name!!,
                description = productCategoryDto.description
            )
        }

        fun assembleProductCategoryDto(productCategory: ProductCategory) : ProductCategoryDto {
            return ProductCategoryDto(
                id = productCategory.id,
                name = productCategory.name,
                description = productCategory.description
            )
        }

        fun assembleProduct(productDto: ProductDto) : Product {
            return Product(
                id = productDto?.id!!,
                name = productDto?.name!!,
                description = productDto.description,
                sellingPrice = productDto?.sellingPrice!!,
                productCategory = assembleProductCategory(productDto.productCategory!!)
            )
        }

        fun assembleProductDto(product: Product) : ProductDto {
            return ProductDto(
                id = product.id,
                name = product.name,
                description = product.description,
                sellingPrice = product.sellingPrice,
                productCategory = assembleProductCategoryDto(product.productCategory)
            )
        }

        fun assembleOrderDetailsDto(cartItemDto: CartItemDto) : OrderDetailsDto {
            return OrderDetailsDto(
                product = cartItemDto.product,
                quantity = cartItemDto.quantity,
                orderedBy = UserContext.getLoggedInUser()?.id!!
            )
        }

        fun assembleOrderDetailsDto(orderDetails: OrderDetails) : OrderDetailsDto {
            return OrderDetailsDto(
                id = orderDetails.id,
                product = assembleProductDto(orderDetails.product),
                quantity = orderDetails.orderQuantity,
                orderedBy = orderDetails.user.id,
                orderDate = formatter.format(orderDetails.order?.orderDate),
                orderId = orderDetails.order?.id
            )
        }

        fun assembleOrderDetails(orderDetailsDto: OrderDetailsDto) : OrderDetails {
            return OrderDetails(
                id = orderDetailsDto?.id!!,
                product = assembleProduct(orderDetailsDto.product),
                orderQuantity = orderDetailsDto.quantity,
                user = UserContext.getLoggedInUser()!!,
                order = null
            )
        }

        fun assembleOrderResponseDto(order: Order) : OrderResponseDto {
            return OrderResponseDto(
                id = order.id,
                status = order.status.toString(),
                totalCost = order.totalCost,
                discountedCost = order.discountedCost,
                orderDate = formatter.format(order.orderDate),
                orderedBy = order.user.id
            )
        }
    }
}