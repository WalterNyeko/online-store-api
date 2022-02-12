package com.store.helpers.utils

import com.store.helpers.constants.DefaultConstant.Companion.API_SECRET
import com.store.helpers.constants.DefaultConstant.Companion.AUTHORIZATION
import com.store.repository.users.UserRepository
import io.jsonwebtoken.Jwts
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class Interceptor(
    private val userRepository: UserRepository? = null
): HandlerInterceptor {

    val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val token = request.getHeader(AUTHORIZATION)?.replace("Bearer ", "")
        if (token != null) {
            UserContext.setToken(token)
        }
        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        log.info("[postHandle][$request]")
    }


    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        log.info("[afterCompletion][request]")
    }

}