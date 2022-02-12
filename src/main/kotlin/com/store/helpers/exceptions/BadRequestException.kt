package com.store.helpers.exceptions

import java.lang.RuntimeException
import java.util.logging.Logger

class BadRequestException(private val reason: String?): RuntimeException(reason) {
    private val LOG = Logger.getLogger(BadRequestException::class.java.name)
}