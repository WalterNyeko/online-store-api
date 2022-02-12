package com.store.service.orders

import com.store.dto.OrderDetailsDto
import com.store.dto.OrderDto
import com.store.dto.OrderResponseDto
import com.store.dto.RecordHolder

interface IOrderService {
    fun placeOrder(orderDto: OrderDto?): Any?
    fun getOrders(): RecordHolder<List<OrderResponseDto>>
    fun getOrderById(orderId: Int?): OrderResponseDto?
    fun getOrderDetailsForOrder(orderId: Int?): RecordHolder<List<OrderDetailsDto?>?>?
}