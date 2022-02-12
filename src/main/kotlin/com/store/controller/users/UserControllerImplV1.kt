package com.store.controller.users

import com.store.dto.ActionResponse
import com.store.dto.LoginDto
import com.store.dto.RecordHolder
import com.store.dto.UserDto
import com.store.service.users.UserServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users")
class UserControllerImplV1(
    private val userService: UserServiceImpl
    ) : IUserController {

    override fun authenticateUser(loginDto: LoginDto?): ResponseEntity<Any>? {
        return loginDto?.let { userService.authenticateUser(it) }
    }

    override fun geUsers(): RecordHolder<List<UserDto>> {
        return userService.getUsers()
    }

    override fun createUser(userDto: UserDto?): ActionResponse? {
        return userService.createUser(userDto)
    }
}
