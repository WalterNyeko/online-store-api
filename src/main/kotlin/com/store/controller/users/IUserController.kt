package com.store.controller.users

import com.store.dto.ActionResponse
import com.store.dto.LoginDto
import com.store.dto.RecordHolder
import com.store.dto.UserDto
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface IUserController {

    @ApiOperation(value = "Sign in user", notes = "User can be able to login")
    @ApiResponse(code = 200, message = "Successfully signed in", response = UserDto::class)
    @PostMapping(value = ["/signin"])
    fun authenticateUser(@RequestBody loginDto: LoginDto?): ResponseEntity<Any>?

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun geUsers(): RecordHolder<List<UserDto>>

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody userDto: UserDto?): ActionResponse?
}