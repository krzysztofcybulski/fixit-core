package me.kcybulski.fixit.config

import me.kcybulski.fixit.domain.users.UsersProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(private val usersProvider: UsersProvider,
                     private val passwordEncoder: PasswordEncoder) : WebSecurityConfigurerAdapter() {


    override fun configure(auth: AuthenticationManagerBuilder) {

        val a = auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)

        usersProvider.getUsers().forEach {
            a.withUser(it.username)
                    .password(passwordEncoder.encode(it.username))
                    .authorities(*it.groups.toTypedArray())
        }
    }

    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests()
                .antMatchers(GET, "/definitions/*/form").permitAll()
                .antMatchers(POST, "/processes").permitAll()
                .anyRequest().authenticated()
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

}