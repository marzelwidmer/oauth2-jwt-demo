package ch.keepcalm.demo.oauth2.auth

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import javax.sql.DataSource
import org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder
import org.apache.tomcat.jni.User.username
import org.bouncycastle.cms.RecipientId.password
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service


@EnableWebSecurity
class WebSecurityConfiguration(private val dataSource: DataSource) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(UserDetailsServiceImpl())
                .passwordEncoder(passwordEncoder())
    }

    @Bean
    public override fun userDetailsService(): UserDetailsService =
            JdbcDaoImpl().also {
                it.setDataSource(dataSource)
            }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

}

@Service
class UserDetailsServiceImpl : UserDetailsService {
    // @deprecated Using this method is not considered safe for production, but is
    //	 * acceptable for demos and getting started. For production purposes, ensure the
    //	 * password is encoded externally. See the method Javadoc for additional details.
    //	 * There are no plans to remove this support. It is deprecated to indicate
    //	 * that this is considered insecure for production purposes.
    @Suppress("DEPRECATION")
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails? {
        return if (username == "user") {
            User.withDefaultPasswordEncoder()
                    .username("user")
                    .password("pass")
                    .roles("USER")
                    .build()
        } else {
            null
        }
    }
}