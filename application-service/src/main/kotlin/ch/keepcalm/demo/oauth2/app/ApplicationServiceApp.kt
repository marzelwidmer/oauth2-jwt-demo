package ch.keepcalm.demo.oauth2.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.config.EnableHypermediaSupport
import org.springframework.hateoas.server.mvc.add
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@SpringBootApplication
@EnableHypermediaSupport(type = arrayOf(EnableHypermediaSupport.HypermediaType.HAL))
class ApplicationServiceApp() {}

fun main(args: Array<String>) {
    runApplication<ApplicationServiceApp>(*args)
}

//   _   _    _  _____ _____ ___    _    ____
//  | | | |  / \|_   _| ____/ _ \  / \  / ___|
//  | |_| | / _ \ | | |  _|| | | |/ _ \ \___ \
//  |  _  |/ ___ \| | | |___ |_| / ___ \ ___) |
//  |_| |_/_/   \_\_| |_____\___/_/   \_\____/
//
open class Index : RepresentationModel<Index>()

@RestController
@RequestMapping(value = ["/"], produces = [MediaTypes.HAL_JSON_UTF8_VALUE])
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
@RequestMapping("/api", produces = [MediaTypes.HAL_JSON_UTF8_VALUE])
class UserController {

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping(value = ["/whoami"])
    fun whoami(principal: Principal?) = principal?.let { ResponseEntity.ok(it) }

}


@RestController
@RequestMapping(value = ["/me"])
class MeController {

    @GetMapping
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    operator fun get(principal: Principal): ResponseEntity<Principal> {
        return ResponseEntity.ok(principal)
    }
}
