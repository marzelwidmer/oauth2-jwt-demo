package ch.keepcalm.demo.oauth2.res.auth

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder
import java.util.HashMap
import javax.sql.DataSource

@EnableWebSecurity
class WebSecurityConfiguration(private val dataSource: DataSource) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService())
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
    fun passwordEncoder(): PasswordEncoder = delegatingPasswordEncoder()

}

private fun delegatingPasswordEncoder(): PasswordEncoder {
    val idForEncode = "bcrypt"
    val encoders = HashMap<String, PasswordEncoder>()
    encoders[idForEncode] = BCryptPasswordEncoder()
    encoders["pbkdf2"] = Pbkdf2PasswordEncoder()
    encoders["scrypt"] = SCryptPasswordEncoder()

    // deprecated encoders
    encoders["ldap"] = org.springframework.security.crypto.password.LdapShaPasswordEncoder()
    encoders["MD4"] = org.springframework.security.crypto.password.Md4PasswordEncoder()
    encoders["MD5"] = org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5")
    encoders["noop"] = org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance()
    encoders["SHA-1"] = org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-1")
    encoders["SHA-256"] = org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256")
    encoders["sha256"] = org.springframework.security.crypto.password.StandardPasswordEncoder()

    val passwordEncoder = DelegatingPasswordEncoder(idForEncode, encoders)
    passwordEncoder.setDefaultPasswordEncoderForMatches(BCryptPasswordEncoder(10))
    return passwordEncoder
}
