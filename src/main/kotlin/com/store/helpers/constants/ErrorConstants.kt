package com.store.helpers.constants

class ErrorConstants {
    companion object {
        const val INVALID_CATEGORY_NAME = "Invalid category name"
        const val INVALID_DESCRIPTION = "Invalid description"
        const val INVALID_SELLING_PRICE = "Invalid selling price"
        const val ENTITY_DOES_NOT_EXISTS = "%s with that %s does not exist"
        const val INVALID_CART_ITEMS = "Invalid order items, cart must contain at least one item"
        const val ENTITY_ALREADY_EXISTS = "%s with that %s already exists"
        const val USER_NOT_FOUND = "User not found"
        const val UNAUTHORIZED = "Unauthorized"
        const val MISSING_AUTHORIZATION_HEADER = "Missing authorization header"
        const val MALFORMED_JWT_SIGNATURE = "Malformed JWT signature"
        const val INVALID_EMAIL = "Invalid email address"
        const val INVALID_PASSWORD = "Invalid password"
        const val INVALID_DATE_OF_BIRTH = "Invalid date of birth"
        const val INVALID_ID_TYPE = "Unknown ID type"
        const val INVALID_ID_VALUE = "Invalid ID value"
        const val INVALID_EMPLOYEE_AFFILIATE_INFO = "User cannot be both employee and an affiliate at the same time"
        const val INVALID_CREDENTIALS = "Invalid login credentials"
        const val PROVIDE_VALID_CREDENTIALS = "Please provide valid credentials"
    }
}