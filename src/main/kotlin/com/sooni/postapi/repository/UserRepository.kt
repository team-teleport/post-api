package com.sooni.postapi.repository

import com.sooni.postapi.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
}