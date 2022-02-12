package com.store.repository.orders

import com.store.models.Order
import com.store.models.OrderDetails
import org.springframework.data.jpa.repository.JpaRepository

interface OrderDetailsRepository: JpaRepository<OrderDetails, Int> {
    fun findByOrder(order: Order): List<OrderDetails>
}