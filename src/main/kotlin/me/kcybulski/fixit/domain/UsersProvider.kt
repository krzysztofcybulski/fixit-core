package me.kcybulski.fixit.domain

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
@EnableConfigurationProperties(IdentityProperties::class)
class UsersProvider(private val identityProperties: IdentityProperties): UserDetailsService {

    private lateinit var users: Map<String, User>

    @PostConstruct
    fun init() {
        this.users = identityProperties.users.mapValues { User(it.key, it.value.name, it.value.email, it.value.groups) }
    }

    fun getUsers() = users.values

    override fun loadUserByUsername(username: String): UserDetails = users[username]
            ?: throw UsernameNotFoundException(username)

}

@ConstructorBinding
@ConfigurationProperties("identity")
data class IdentityProperties(val users: Map<String, UserProperty>)

@ConstructorBinding
data class UserProperty(val name: String, val email: String, val groups: Set<String>)