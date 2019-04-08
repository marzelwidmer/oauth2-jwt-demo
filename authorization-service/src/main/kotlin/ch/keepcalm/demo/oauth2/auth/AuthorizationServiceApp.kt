package ch.keepcalm.demo.oauth2.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
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


@SpringBootApplication
@EnableHypermediaSupport(type = arrayOf(EnableHypermediaSupport.HypermediaType.HAL))
class AuthorizationServiceApp

fun main(args: Array<String>) {
    runApplication<AuthorizationServiceApp>(*args)
}

//   _   _    _  _____ _____ ___    _    ____
//  | | | |  / \|_   _| ____/ _ \  / \  / ___|
//  | |_| | / _ \ | | |  _|| | | |/ _ \ \___ \
//  |  _  |/ ___ \| | | |___ |_| / ___ \ ___) |
//  |_| |_/_/   \_\_| |_____\___/_/   \_\____/
//
open class Index : RepresentationModel<Index>()

@RestController
@RequestMapping("/", produces = [MediaTypes.HAL_JSON_UTF8_VALUE])
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
@RequestMapping(value = ["/users"], produces = [MediaTypes.HAL_JSON_UTF8_VALUE])
class UserController {

    @GetMapping(value = ["/whoami"])
    fun whoami(principal: Principal?) = principal?.let { ResponseEntity.ok(it) }


    @GetMapping(value = ["/me"])
    operator fun get(principal: Principal) = whoami(principal)
}


