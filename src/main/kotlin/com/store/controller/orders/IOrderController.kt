package com.store.controller.orders

import com.store.dto.OrderDetailsDto
import com.store.dto.OrderDto
import com.store.dto.OrderResponseDto
import com.store.dto.RecordHolder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

interface IOrderController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun placeOrder(@RequestBody orderDto: OrderDto?): Any?

    @GetMapping
    fun getOrders(): RecordHolder<List<OrderResponseDto>>

    @GetMapping("/{orderId}")
    fun getOrderById(@PathVariable orderId: Int?): OrderResponseDto?

    @GetMapping("/{orderId}/details")
    fun getOrderDetailsForOrder(@PathVariable orderId: Int?): RecordHolder<List<OrderDetailsDto?>?>?

}