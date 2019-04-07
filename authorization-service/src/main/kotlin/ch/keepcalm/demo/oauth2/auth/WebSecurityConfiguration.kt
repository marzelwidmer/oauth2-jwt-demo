package ch.keepcalm.demo.oauth2.auth

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl
import org.springframework.security.crypto.password.PasswordEncoder

import javax.sql.DataSource

@EnableWebSecurity
class WebSecurityConfiguration(private val dataSource: DataSource) : WebSecurityConfigurerAdapter() {

    private var passwordEncoder: PasswordEncoder? = null
    private var userDetailsService: UserDetailsService? = null

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder())
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        if (passwordEncoder == null) {
            passwordEncoder = DefaultPasswordEncoderFactories.createDelegatingPasswordEncoder()
        }
        return passwordEncoder
    }

    @Bean
    public override fun userDetailsService(): UserDetailsService {
        if (userDetailsService == null) {
            userDetailsService = JdbcDaoImpl()
            (userDetailsService as JdbcDaoImpl).setDataSource(dataSource)
        }
        return userDetailsService as UserDetailsService
    }

}
