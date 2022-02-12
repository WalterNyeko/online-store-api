package com.store.helpers.utils

import com.store.models.User

class UserContext {
    companion object {
        private val appUser: ThreadLocal<User> = ThreadLocal<User>()
        private val token: ThreadLocal<String> = ThreadLocal<String>()
        fun setLoggedInUser(loggedInUser: User?) = appUser.set(loggedInUser)
        fun getLoggedInUser(): User? = appUser.get()
        fun setToken(jwt: String) = token.set(jwt)
        fun getToken(): String? = token.get()
    }
}