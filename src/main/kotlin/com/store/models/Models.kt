package com.store.models

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.store.dto.AllOpen
import com.store.dto.NoArg
import com.store.helpers.enums.IdType
import com.store.helpers.enums.OrderStatus
import lombok.EqualsAndHashCode
import java.io.Serializable
import java.math.BigDecimal
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@NoArg
@Entity(name = "user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)val id: Int,
    @Column(unique = true, name = "username")
    val username: String,
    @Column(name = "first_name")
    val firstName: String,
    @Column(name = "last_name")
    val lastName: String,
    @Column(name = "date_of_birth")
    val dateOfBirth: Date,
    @Column(name = "id_type")
    val idType: IdType,
    @Column(name = "id_number")
    val idNumber: String,
    val address: String,
    @Column(unique = true, name = "email")
    val email: String,
    @Column(name = "phone_number")
    val phoneNumber: String,
    val password: String,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "user_affiliate",
        joinColumns = [JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
        )],
        inverseJoinColumns = [JoinColumn(
            name = "affiliate_id",
            referencedColumnName = "id"
        )]
    )
    val affiliate: Affiliate? = null,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "user_employee",
        joinColumns = [JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
        )],
        inverseJoinColumns = [JoinColumn(
            name = "employee_id",
            referencedColumnName = "id"
        )]
    )
    val employee: Employee? = null,
    val createdDate: Date
) : Serializable

@NoArg
@AllOpen
@Entity(name = "employee")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
data class Employee(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)val id: Int,
    @Column(name = "employee_number")
    @field:NotNull @field:NotEmpty val employeeNumber: String,
    @Column(name = "line_manager")
    val lineManager: String? = null,
    @Column(name = "employment_date")
    @field:NotNull @field:JsonSerialize(using = LocalDateTimeSerializer::class)
    @field:JsonFormat(shape = JsonFormat.Shape.STRING) val employmentDate: Date,
    @Column(name = "contract_expiry_date")
    @field:NotNull @field:JsonSerialize(using = LocalDateTimeSerializer::class)
    @field:JsonFormat(shape = JsonFormat.Shape.STRING) val contractExpiryDate: Date,
    @OneToOne(mappedBy = "employee")
    @field:NotNull val user: User? = null
) : Serializable

@NoArg
@AllOpen
@Entity(name = "affiliate")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
data class Affiliate(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)val id: Int,
    @Column(name = "affiliate_number")
    @field:NotNull @field:NotEmpty val affiliateNumber: String,
    @Column(name = "affiliate_commission")
    @field:NotNull @field:NotEmpty val affiliateCommission: BigDecimal,
    @Column(name = "line_manager")
    val lineManager: String? = null,
    @Column(name = "contract_start_date")
    @field:NotNull @field:JsonSerialize(using = LocalDateTimeSerializer::class)
    @field:JsonFormat(shape = JsonFormat.Shape.STRING) val contractStartDate: Date,
    @Column(name = "contract_end_date")
    @field:NotNull @field:JsonSerialize(using = LocalDateTimeSerializer::class)
    @field:JsonFormat(shape = JsonFormat.Shape.STRING) val contractEndDate: Date,
    @OneToOne(mappedBy = "affiliate")
    @field:NotNull val user: User? = null
) : Serializable

@NoArg
@AllOpen
@Entity(name = "product_category")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
data class ProductCategory(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)val id: Int,
    @field:NotNull val name: String,
    val description: String? = null
)

@NoArg
@AllOpen
@Entity(name = "product")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)val id: Int,
    @field:NotNull val name: String,
    val description: String? = null,
    @Column(name = "selling_price")
    @field:NotNull val sellingPrice: BigDecimal,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    var productCategory: ProductCategory
)

@NoArg
@AllOpen
@Entity(name = "orders")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)val id: Int,
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: OrderStatus,
    @Column(name = "total_cost")
    val totalCost: BigDecimal,
    @Column(name = "discounted_cost")
    val discountedCost: BigDecimal,
    @Column(name = "order_date")
    val orderDate: Date,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @field:NotNull val user: User
)

@NoArg
@AllOpen
@Entity(name = "order_details")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
data class OrderDetails(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Int,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    var order: Order?,
    @Column(name = "order_quantity")
    val orderQuantity: Int,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @field:NotNull val user: User
)