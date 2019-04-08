package ch.keepcalm.demo.oauth2.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.core.io.Resource

@ConfigurationProperties("security")
class SecurityProperties {

    val jwt = JwtProperties()

    class JwtProperties {
        lateinit var keyStore: Resource
        lateinit var keyStorePassword: String
        lateinit var keyPairAlias: String
        lateinit var keyPairPassword: String
    }
}
