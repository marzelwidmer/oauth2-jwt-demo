package ch.keepcalm.demo.oauth2.auth

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder

import java.util.HashMap

internal object DefaultPasswordEncoderFactories {

    fun createDelegatingPasswordEncoder(): PasswordEncoder {
        val encodingId = "bcrypt"
        val encoders = HashMap<String, PasswordEncoder>()
        encoders[encodingId] = BCryptPasswordEncoder()
        encoders["ldap"] = org.springframework.security.crypto.password.LdapShaPasswordEncoder()
        encoders["MD4"] = org.springframework.security.crypto.password.Md4PasswordEncoder()
        encoders["MD5"] = org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5")
        encoders["noop"] = org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance()
        encoders["pbkdf2"] = Pbkdf2PasswordEncoder()
        encoders["scrypt"] = SCryptPasswordEncoder()
        encoders["SHA-1"] = org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-1")
        encoders["SHA-256"] = org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256")
        encoders["sha256"] = org.springframework.security.crypto.password.StandardPasswordEncoder()

        val delegatingPasswordEncoder = DelegatingPasswordEncoder(encodingId, encoders)
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(BCryptPasswordEncoder(10))
        return delegatingPasswordEncoder
    }

}
