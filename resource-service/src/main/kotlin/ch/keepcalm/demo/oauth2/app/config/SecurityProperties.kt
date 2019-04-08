package ch.keepcalm.demo.oauth2.app.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.core.io.Resource

@ConfigurationProperties("security")
class SecurityProperties {

    val jwt = JwtProperties()

    class JwtProperties {
        lateinit var publicKey: Resource
    }
}

