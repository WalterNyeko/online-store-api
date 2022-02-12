package com.store.helpers.utils

import com.store.helpers.exceptions.BadRequestException
import org.apache.commons.lang3.EnumUtils
import org.springframework.util.CollectionUtils
import java.math.BigDecimal
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.function.Supplier
import java.util.regex.Pattern

class Validator {

    companion object {
        fun notNull(`object`: Any?, message: String?, vararg args: Any?) {
            if (`object` == null) {
                throwBadRequestException(String.format(message!!, *args))
            }
        }

        fun validEmployeeAndAffiliateDetails(employee: Any?, affiliate: Any?, message: String?) {
            if (employee != null && affiliate != null) {
                throwBadRequestException(message)
            }
        }

        fun isGreaterThanZero(value: BigDecimal?, message: String?) {
            if (value == null || value.compareTo(BigDecimal.ZERO) < 1) {
                throw BadRequestException(message)
            }
        }

        fun notEmpty(collection: Collection<*>?, message: String?) {
            if (CollectionUtils.isEmpty(collection)) {
                throwBadRequestException(message)
            }
        }

        fun <T : Enum<T>?> validEnum(clazz: Class<T>?, enumName: String?, message: String?) {
            if (!EnumUtils.isValidEnum(clazz, enumName)) {
                throwBadRequestException(message)
            }
        }

        fun validStringLength(str: String?, minLength: Int, maxLength: Int, message: String?) {
            if (str == null || str.length in (maxLength + 1) until minLength) {
                throwBadRequestException(message)
            }
        }

        fun validEmail(email: String?, message: String?) {
            val emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$"
            val pattern = Pattern.compile(emailRegex)
            if (email == null || !pattern.matcher(email).matches()) {
                throwBadRequestException(message)
            }
        }

        fun validPassword(password: String?, message: String?) {
            val regex = ("^(?=.*[0-9])"
                    + "(?=.*[a-z])(?=.*[A-Z])"
                    + "(?=.*[@#$%^&+=])"
                    + "(?=\\S+$).{8,20}$")
            val pattern = Pattern.compile(regex)
            if (password == null || !pattern.matcher(password).matches()) {
                throwBadRequestException(message)
            }
        }

        private fun <X : Throwable?> throwException(exceptionSupplier: Supplier<out X>) {
            throw exceptionSupplier.get()!!
        }

        private fun throwBadRequestException(message: String?) {
            throwException(Supplier<RuntimeException> { BadRequestException(message) })
        }

        @Throws(ParseException::class)
        fun validateDateIsInTheFuture(date: String?, message: String?) {
            if (date == null) throw BadRequestException(message)
            val current = Date()
            val myFormatString = "dd/MM/yyyy"
            val df = SimpleDateFormat(myFormatString)
            val givenDate = df.parse(date)
            val givenDateTime = givenDate.time
            val next = Date(givenDateTime)
            if (!next.after(current) && next != current) {
                throw BadRequestException(message)
            }
        }
    }
}