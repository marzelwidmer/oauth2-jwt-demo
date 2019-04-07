package ch.keepcalm.demo.oauth2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.http.ResponseEntity
import java.security.Principal
import org.springframework.hateoas.config.EnableHypermediaSupport
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.add
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


fun main(args: Array<String>) {
    runApplication<AuthorizationService>(*args)
}

@SpringBootApplication
@EnableHypermediaSupport(type = arrayOf(EnableHypermediaSupport.HypermediaType.HAL))
class AuthorizationService


//   _   _    _  _____ _____ ___    _    ____
//  | | | |  / \|_   _| ____/ _ \  / \  / ___|
//  | |_| | / _ \ | | |  _|| | | |/ _ \ \___ \
//  |  _  |/ ___ \| | | |___ |_| / ___ \ ___) |
//  |_| |_/_/   \_\_| |_____\___/_/   \_\____/
//
open class Index : RepresentationModel<Index>()

@RestController
@RequestMapping("/api", produces = [MediaTypes.HAL_JSON_UTF8_VALUE])
@RefreshScope
class IndexController {

    @GetMapping
    fun api(): Index = Index()
            .apply {
                add(UserController::class) {
                    linkTo { whoami(null) } withRel "whoami"
                }
                add(IndexController::class) {
                    linkTo { api() } withRel IanaLinkRelations.SELF
                }
            }
}

@RestController
@RequestMapping("/users", produces = [MediaTypes.HAL_JSON_UTF8_VALUE])
class UserController {

    @GetMapping("/whoami")
    fun whoami(principal: Principal?) = principal?.let { ResponseEntity.ok(it) }


    @GetMapping("/me")
    operator fun get(principal: Principal) = whoami(principal)
}


