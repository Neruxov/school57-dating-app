package xyz.neruxov.datingapp.controller

import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import xyz.neruxov.datingapp.dto.model.User
import xyz.neruxov.datingapp.dto.request.UserReactRequest
import xyz.neruxov.datingapp.dto.request.UserRegisterRequest
import xyz.neruxov.datingapp.service.UserService

/**
 * @author <a href="https://github.com/Neruxov">Neruxov</a>
 */
@RestController
@RequestMapping("/users")
class UserController(
    val userService: UserService
) {

    @GetMapping
    fun getAll(@PageableDefault(size = 20) pageable: Pageable, @RequestHeader("Authorization") token: String) =
        userService.getAll(pageable, token)

    @PostMapping
    fun register(@RequestBody body: UserRegisterRequest) = userService.register(body)

    @PutMapping
    fun update(@RequestBody body: User, @RequestHeader("Authorization") token: String) = userService.update(body, token)

    @PostMapping("/{id}/reaction")
    fun react(
        @PathVariable id: Long,
        @RequestBody body: UserReactRequest,
        @RequestHeader("Authorization") token: String
    ) = userService.react(id, body, token)

}