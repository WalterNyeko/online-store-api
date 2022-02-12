package com.store.service.users

import com.store.dto.ActionResponse
import com.store.dto.LoginDto
import com.store.dto.RecordHolder
import com.store.dto.UserDto
import com.store.helpers.constants.DefaultConstant
import com.store.helpers.constants.ErrorConstants
import com.store.helpers.exceptions.BadRequestException
import com.store.helpers.utils.Assembler
import com.store.models.User
import com.store.repository.users.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
    ): IUserService {

    override fun createUser(userDto: UserDto?): ActionResponse? {
        userRepository.findByUsername(username = userDto?.username!!)
            .let { if (it != null) throw BadRequestException(
                ErrorConstants.ENTITY_ALREADY_EXISTS.format(User::class, "username")
            ) }
        val user = userDto?.let { Assembler.assembleUser(it) }?.let { userRepository.save(it) }
        return ActionResponse(resourceId = user?.id)
    }

    override fun authenticateUser(loginDto: LoginDto): ResponseEntity<Any> {
        val user = userRepository.findByUsername(loginDto.username)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Assembler.assembleAuthError())
        if(!validatePasswords(loginDto.password, user.password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Assembler.assembleAuthError())
        var userDto = Assembler.assembleUserDto(user)
        userDto.token =  generateToken(user.id.toString())
        return ResponseEntity.ok().body(userDto)
    }

    override fun getUsers(): RecordHolder<List<UserDto>> {
        val users: List<UserDto?> = userRepository.findAll().stream()
            .map { Assembler.assembleUserDto(it) }.collect(Collectors.toList())
        return RecordHolder(totalRecords = users.size, records = users as List<UserDto>)
    }

    override fun getUserById(id: Int): User {
        return userRepository.findById(id).orElseThrow()
    }

    private fun validatePasswords(
        requestPassword: String,
        originalPassword: String
    ): Boolean = passwordEncoder.matches(requestPassword, originalPassword)


    private fun generateToken(issuer: String): String {
        return Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000))
            .signWith(SignatureAlgorithm.HS512, DefaultConstant.API_SECRET)
            .compact()
    }
}