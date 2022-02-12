package com.store.service.users

import com.store.dto.ActionResponse
import com.store.dto.LoginDto
import com.store.dto.RecordHolder
import com.store.dto.UserDto
import com.store.models.User
import org.springframework.http.ResponseEntity

interface IUserService {
    fun createUser(userDto: UserDto?): ActionResponse?
    fun authenticateUser(loginDto: LoginDto): ResponseEntity<Any>?
    fun getUsers(): RecordHolder<List<UserDto>>
    fun getUserById(id: Int) : User
}