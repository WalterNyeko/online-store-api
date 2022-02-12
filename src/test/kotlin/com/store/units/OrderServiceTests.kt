package com.store.units

import com.store.helpers.utils.Assembler
import com.store.helpers.utils.UserContext
import com.store.helpers.TestHelpers
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal
import java.math.RoundingMode

class OrderServiceTests: TestHelpers(){

    @Test
    fun `should build order object with correct discount values for ordinary customer`() {
        //Given
        UserContext.setLoggedInUser(Assembler.assembleUser(userDtoOrdinaryUser))

        //When
        val result = orderService.buildOrder(orderDto)

        //Then
        Assert.assertEquals(
            BigDecimal(5700.00).setScale(2, RoundingMode.DOWN),
            result.discountedCost)

    }
    @Test
    fun `should build order object with correct discount values for employee`() {
        //Given
        UserContext.setLoggedInUser(Assembler.assembleUser(userDtoEmployee))

        //When
        val result = orderService.buildOrder(orderDto)

        //Then
        Assert.assertEquals(
            BigDecimal(3900.00).setScale(2, RoundingMode.DOWN),
            result.discountedCost)

    }
    @Test
    fun `should build order object with correct discount values for affiliate`() {
        //Given
        UserContext.setLoggedInUser(Assembler.assembleUser(userDtoAffiliate))

        //When
        val result = orderService.buildOrder(orderDto)

        //Then
        Assert.assertEquals(
            BigDecimal(5100.00).setScale(2, RoundingMode.DOWN),
            result.discountedCost)

    }
}