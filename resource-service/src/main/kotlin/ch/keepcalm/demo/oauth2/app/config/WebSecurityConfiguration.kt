package ch.keepcalm.demo.oauth2.app.config

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration
