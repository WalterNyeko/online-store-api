package com.store.controller.orders

import com.store.dto.OrderDetailsDto
import com.store.dto.OrderDto
import com.store.dto.OrderResponseDto
import com.store.dto.RecordHolder
import com.store.helpers.constants.DefaultConstant
import com.store.helpers.constants.ErrorConstants
import com.store.helpers.utils.APIError
import com.store.helpers.utils.Assembler
import com.store.helpers.utils.UserContext
import com.store.service.orders.OrderServiceImpl
import com.store.service.users.UserServiceImpl
import io.jsonwebtoken.Jwts
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/orders")
class OrderControllerImplV1(
    private val orderService: OrderServiceImpl,
    private val userService: UserServiceImpl
): IOrderController {
    override fun placeOrder(orderDto: OrderDto?): Any? {
        val token = UserContext.getToken()
        if (token != null) {
            try{
                val body = Jwts.parser().setSigningKey(DefaultConstant.API_SECRET).parseClaimsJws(token).body

                UserContext.setLoggedInUser(userService.getUserById(body.issuer.toInt()))

            }catch (e: Exception) {
                UserContext.setLoggedInUser(null)
                return Assembler.assembleAuthError()
            }
        }
        return orderService.placeOrder(orderDto)
    }

    override fun getOrders(): RecordHolder<List<OrderResponseDto>> {
        return orderService.getOrders()
    }

    override fun getOrderById(orderId: Int?): OrderResponseDto? {
        return orderService.getOrderById(orderId)
    }

    override fun getOrderDetailsForOrder(orderId: Int?): RecordHolder<List<OrderDetailsDto?>?>? {
        return orderService.getOrderDetailsForOrder(orderId)
    }
}