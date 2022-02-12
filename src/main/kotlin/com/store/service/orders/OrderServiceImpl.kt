package com.store.service.orders

import com.store.dto.*
import com.store.helpers.enums.OrderStatus
import com.store.helpers.utils.Assembler
import com.store.helpers.utils.Assembler.Companion.assembleOrderDetails
import com.store.helpers.utils.Assembler.Companion.assembleOrderDetailsDto
import com.store.helpers.utils.Assembler.Companion.assembleOrderResponseDto
import com.store.helpers.utils.UserContext
import com.store.models.Order
import com.store.models.OrderDetails
import com.store.models.Product
import com.store.repository.orders.OrderDetailsRepository
import com.store.repository.orders.OrderRepository
import com.store.repository.products.ProductRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import java.util.stream.Collectors

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val orderDetailsRepository: OrderDetailsRepository,
    private val productRepository: ProductRepository
): IOrderService {

    @Value("\${app.discount.loyal-customer-duration}")
    private var loyalCustomerDuration: Int? = 2
    @Value("\${app.discount.loyal-customer-discount}")
    private var loyalCustomerDiscount: Int? = 5;
    @Value("\${app.discount.employee-discount}")
    private var employeeDiscount: Int? = 30;
    @Value("\${app.discount.affiliate-discount}")
    private var affiliateDiscount: Int? = 10;
    @Value("\${app.discount.per-unit-sales-price}")
    private var perUnitSalesPrice: BigDecimal? = BigDecimal(100);
    @Value("\${app.discount.per-unit-sales-discount}")
    private var perUnitSalesDiscount: Int? = 5;


    override fun placeOrder(orderDto: OrderDto?): Any? {
        val order: Order? = orderDto?.let { buildOrder(it) }?.let { orderRepository.save(it) }

        var orderDetails: MutableList<OrderDetails> = mutableListOf()
        for (orderDetailsValue in orderDto?.cartItems?.stream()
            ?.map { cartItemDto -> assembleOrderDetailsDto(cartItemDto) }
            ?.collect(Collectors.toList())!!) {
            val product = orderDetailsValue.product.id?.let { productRepository.findById(it).orElseThrow() }
            orderDetailsValue.product = product?.let { Assembler.assembleProductDto(it) }!!
            orderDetailsValue.orderId = order?.id
            orderDetails.add(assembleOrderDetails(orderDetailsValue))
        }
        orderDetails.stream().forEach { it.order = order }

        orderDetailsRepository.saveAll(orderDetails)
        return order?.let { assembleOrderResponseDto(it) }
    }

    override fun getOrders(): RecordHolder<List<OrderResponseDto>> {
        val orders : List<OrderResponseDto?> = orderRepository.findAll().stream()
            .map { assembleOrderResponseDto(it) }.collect(Collectors.toList())
        return RecordHolder(totalRecords = orders.size, records = orders as List<OrderResponseDto>)
    }

    override fun getOrderById(orderId: Int?): OrderResponseDto? {
        val order = orderId?.let { orderRepository.findById(it).orElseThrow() }
        return order?.let { assembleOrderResponseDto(it) }
    }

    override fun getOrderDetailsForOrder(orderId: Int?): RecordHolder<List<OrderDetailsDto?>?>? {
        val order = orderId?.let { orderRepository.findById(it).orElseThrow() }
        val orderDetails = order?.let { orderDetailsRepository.findByOrder(it) }?.stream()?.map { assembleOrderDetailsDto(it) }
            ?.collect(Collectors.toList())
        return RecordHolder(totalRecords = orderDetails?.size!!, records = orderDetails)
    }

    fun getDurationInYears(first: Date?, last: Date?): Int {
        val firstDate = getCalendar(first)
        val lastDate = getCalendar(last)
        var difference = lastDate[Calendar.YEAR] - firstDate[Calendar.YEAR]
        if (firstDate[Calendar.DAY_OF_YEAR] > lastDate[Calendar.DAY_OF_YEAR]) {
            difference--
        }
        return difference
    }

    private fun getCalendar(date: Date?): Calendar {
        val calendar = Calendar.getInstance(Locale.US)
        calendar.time = date
        return calendar
    }
    fun buildOrder(orderDto: OrderDto): Order {
        var totalCost = BigDecimal.ZERO
        var discountValue = BigDecimal.ZERO

        for (orderDetailsValue in orderDto.cartItems.stream()
            .map { cartItemDto -> assembleOrderDetailsDto(cartItemDto) }
            .collect(Collectors.toList())) {
            val product = orderDetailsValue.product.id?.let { productRepository.findById(it).orElseThrow() }
            totalCost = totalCost.add(
                product?.sellingPrice
                    ?.multiply(BigDecimal(orderDetailsValue.quantity))
            )
            val productCost: BigDecimal = product?.sellingPrice
                ?.multiply(BigDecimal(orderDetailsValue.quantity))?: BigDecimal.ZERO


            if (!product?.productCategory?.name?.lowercase()?.contains("groceries")!!) {

                //Is Employee --> Give Discount
                if (UserContext.getLoggedInUser()?.employee != null) {
                    discountValue = discountValue.add(
                        productCost
                            .multiply(employeeDiscount?.div(100.00)?.let { BigDecimal(it) })
                            .setScale(2, RoundingMode.HALF_DOWN)
                    )
                }

                //Is Affiliate --> Give Discount
                if (UserContext.getLoggedInUser()?.affiliate != null) {
                    discountValue = discountValue.add(
                        productCost
                            .multiply(affiliateDiscount?.div(100.00)?.let { BigDecimal(it) })
                            .setScale(2, RoundingMode.HALF_DOWN)
                    )
                }

                //Is Loyal Customer(Neither Employee nor Affiliate) --> Give Discount
                if (UserContext.getLoggedInUser()?.employee == null && UserContext.getLoggedInUser()
                    ?.affiliate == null && getDurationInYears(UserContext
                        .getLoggedInUser()?.createdDate, Date()) >= loyalCustomerDuration!!
                ) {
                    discountValue = discountValue.add(
                        productCost
                            .multiply(loyalCustomerDiscount?.div(100.00)?.let { BigDecimal(it) })
                    )
                }
            }
        }

        //Non-percentage-based discount
        discountValue = discountValue.add(
            totalCost
                .divide(perUnitSalesPrice)
                .setScale(2, RoundingMode.HALF_DOWN)
                .multiply(perUnitSalesDiscount?.let { BigDecimal(it) })
        )

        return Order(
                id = 0,
                totalCost = totalCost,
                discountedCost = totalCost.subtract(discountValue).setScale(2, RoundingMode.HALF_DOWN),
                user = UserContext.getLoggedInUser()!!,
                orderDate = Date(),
                status = OrderStatus.PENDING
            )
    }
}