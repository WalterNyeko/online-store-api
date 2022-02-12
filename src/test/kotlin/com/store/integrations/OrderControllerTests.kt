package com.store.integrations

import com.store.dto.OrderDto
import com.store.dto.OrderResponseDto
import com.store.helpers.TestHelpers
import org.junit.Assert
import org.junit.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.math.BigDecimal
import java.math.RoundingMode

class OrderControllerTests: TestHelpers() {

    @Test
    fun `should place order and compute discount correctly for new customer`() {
        //Given
        val headers = HttpHeaders()
        headers["Authorization"] = "Bearer " + userDtoOrdinaryUser.token
        val entity: HttpEntity<OrderDto> = HttpEntity(orderDto, headers)

        //When
        val responseEntity: ResponseEntity<OrderResponseDto> = restTemplate.postForEntity(
            "/v1/orders", entity,
            OrderResponseDto::class.java
        )

        //Then
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
        Assert.assertEquals(
            BigDecimal(5700.00).setScale(2, RoundingMode.HALF_DOWN),
            responseEntity.body?.discountedCost
        )
    }

    @Test
    fun `should place order and compute discount correctly for employee`() {
        //Given
        val headers = HttpHeaders()
        headers["Authorization"] = "Bearer " + userDtoEmployee.token
        val entity: HttpEntity<OrderDto> = HttpEntity(orderDto, headers)

        //When
        val responseEntity: ResponseEntity<OrderResponseDto> = restTemplate.postForEntity(
            "/v1/orders", entity,
            OrderResponseDto::class.java
        )

        //Then
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
        Assert.assertEquals(
            BigDecimal(3900.00).setScale(2, RoundingMode.HALF_DOWN),
            responseEntity.body?.discountedCost
        )
    }
    @Test
    fun `should place order and compute discount correctly for affiliate`() {
        //Given
        val headers = HttpHeaders()
        headers["Authorization"] = "Bearer " + userDtoAffiliate.token
        val entity: HttpEntity<OrderDto> = HttpEntity(orderDto, headers)

        //When
        val responseEntity: ResponseEntity<OrderResponseDto> = restTemplate.postForEntity(
            "/v1/orders", entity,
            OrderResponseDto::class.java
        )

        //Then
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
        Assert.assertEquals(
            BigDecimal(5100.00).setScale(2, RoundingMode.HALF_DOWN),
            responseEntity.body?.discountedCost
        )
    }
}