package com.store.repository.orders

import com.store.models.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository: JpaRepository<Order, Int> {
}