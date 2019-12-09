package me.kcybulski.fixit.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class User(private val username: String, val name: String, val email: String, val groups: Set<String>) : UserDetails {

    override fun getUsername(): String = username

    override fun getPassword(): String = email

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = groups
            .map { SimpleGrantedAuthority(it) }
            .toMutableList()

    override fun isEnabled(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

}