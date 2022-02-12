package com.store.helpers

import com.store.dto.*
import com.store.helpers.utils.Assembler
import com.store.models.Product
import com.store.models.ProductCategory
import com.store.repository.orders.OrderDetailsRepository
import com.store.repository.orders.OrderRepository
import com.store.repository.products.ProductCategoryRepository
import com.store.repository.products.ProductRepository
import com.store.service.orders.OrderServiceImpl
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.PropertySource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
class TestHelpers {

    @Autowired
    lateinit var orderService: OrderServiceImpl
    @Autowired
    lateinit var productCategoryRepository: ProductCategoryRepository
    @Autowired
    lateinit var productRepository: ProductRepository
    @Autowired
    lateinit var restTemplate: TestRestTemplate

    lateinit var userDtoOrdinaryUser: UserDto
    lateinit var userDtoEmployee: UserDto
    lateinit var userDtoAffiliate: UserDto
    lateinit var orderDto: OrderDto

    @Before
    fun `set up`() {
        // Sign Up Ordinary User
        userDtoOrdinaryUser = UserDto(
            id = 0,
            username = "john",
            password = "JohnDoe@123!",
            email = "john@gmail.com",
            address = "John Street",
            idType = "NATIONAL_ID",
            idNumber = "CM92005HRF8",
            dateOfBirth = "28/09/1992",
            phoneNumber = "0786277071",
            firstName = "John",
            lastName = "Doe"
        )
        val headers = HttpHeaders()
        val entity = HttpEntity<UserDto>(this.userDtoOrdinaryUser, headers)
        restTemplate.postForEntity("/v1/users/signup", entity, ActionResponse::class.java)


        // Sign Up Employee User
        userDtoEmployee = UserDto(
            id = 0,
            username = "jane",
            password = "JaneDoe@123!",
            email = "jane@gmail.com",
            address = "Jane Street",
            idType = "NATIONAL_ID",
            idNumber = "CM92005HRF8",
            dateOfBirth = "28/09/1992",
            phoneNumber = "0786277071",
            firstName = "Jane",
            lastName = "Doe",
            employee = EmployeeDto(
                employeeNumber = "1234G",
                lineManager = "Okeny Justine",
                employmentDate = "22/01/2022",
                contractExpiryDate = "22/01/2024"
            )
        )
        val entityEmployee = HttpEntity<UserDto>(this.userDtoEmployee, headers)
        restTemplate.postForEntity("/v1/users/signup", entityEmployee, ActionResponse::class.java)

        // Sign Up Employee User
        userDtoAffiliate = UserDto(
            id = 0,
            username = "joan",
            password = "JoanDoe@123!",
            email = "joan@gmail.com",
            address = "Joan Street",
            idType = "NATIONAL_ID",
            idNumber = "CM92005HRF8",
            dateOfBirth = "28/09/1992",
            phoneNumber = "0786277071",
            firstName = "Joan",
            lastName = "Doe",
            affiliate = AffiliateDto(
                affiliateNumber = "1234G",
                affiliateCommission = BigDecimal(0.6),
                lineManager = "Okello Joel",
                contractStartDate = "22/01/2022",
                contractEndDate = "22/01/2024"
            )
        )
        val entityAffiliate = HttpEntity<UserDto>(this.userDtoAffiliate, headers)
        restTemplate.postForEntity("/v1/users/signup", entityAffiliate, ActionResponse::class.java)

        //Login Ordinary User
        val loginDto = LoginDto(
            username = "john",
            password = "JohnDoe@123!"
        )
        val loginEntity = HttpEntity(loginDto, headers)
        val loginResponse = restTemplate.postForEntity(
            "/v1/users/signin", loginEntity,
            UserDto::class.java
        )
        userDtoOrdinaryUser.token = loginResponse.body?.token

        //Login Employee User
        val loginDtoEmployee = LoginDto(
            username = "jane",
            password = "JaneDoe@123!"
        )
        val loginEntityEmployee = HttpEntity(loginDtoEmployee, headers)
        val loginResponseEmployee = restTemplate.postForEntity(
            "/v1/users/signin", loginEntityEmployee,
            UserDto::class.java
        )
        userDtoEmployee.token = loginResponseEmployee.body?.token

        //Login Affiliate User
        val loginDtoAffiliate = LoginDto(
            username = "joan",
            password = "JoanDoe@123!"
        )
        val loginEntityAffiliate = HttpEntity(loginDtoAffiliate, headers)
        val loginResponseAffiliate = restTemplate.postForEntity(
            "/v1/users/signin", loginEntityAffiliate,
            UserDto::class.java
        )
        userDtoAffiliate.token = loginResponseAffiliate.body?.token

        //Setup Product Category
        val productCategory = ProductCategory(
            id = 0,
            name = "Electronics",
            description = "Electrical and Electronics appliances"
        ).let { productCategoryRepository.save(it) }

        //Setup Product
        val product = Product(
            id = 0,
            name = "Nokia Phone",
            description = "Nokia latest phone",
            sellingPrice = BigDecimal(2000),
            productCategory = productCategory
        ).let { productRepository.save(it) }

        //Setup Order
        val cartItems: List<CartItemDto> = listOf(
            CartItemDto(product = Assembler.assembleProductDto(product), quantity = 3)
        )
        orderDto = OrderDto(cartItems)
    }

    @Test
    fun contextLoad() {
    }
}