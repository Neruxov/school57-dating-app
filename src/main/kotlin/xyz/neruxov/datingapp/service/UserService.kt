package xyz.neruxov.datingapp.service

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import xyz.neruxov.datingapp.dao.UserEntity
import xyz.neruxov.datingapp.dto.model.User
import xyz.neruxov.datingapp.dto.request.UserRegisterRequest
import xyz.neruxov.datingapp.dto.response.UserRegisterResponse
import xyz.neruxov.datingapp.exception.type.UnauthorizedException
import xyz.neruxov.datingapp.repository.UserRepository
import java.security.MessageDigest
import kotlin.jvm.optionals.getOrElse

/**
 * @author <a href="https://github.com/Neruxov">Neruxov</a>
 */
@Service
class UserService(
    val userRepository: UserRepository
) {

    fun getAll(pageable: Pageable, token: String): PageImpl<User> {
        getAuthorizationUserEntity(token)

        return PageImpl(userRepository.findAll(pageable).map { it.toModel() })
    }

    fun register(body: UserRegisterRequest): UserRegisterResponse {
        val token = generateToken()

        val userEntity = UserEntity(login = body.login, passwordHash = body.password, token = token)
        val savedUser = userRepository.save(userEntity)

        return UserRegisterResponse(
            id = savedUser.id,
            login = body.login,
            token = token
        )
    }

    fun update(body: User, token: String): User {
        val authorizedUserEntity = getAuthorizationUserEntity(token)

        val userEntity = authorizedUserEntity.copy(
            login = body.login,
            gender = body.gender,
            age = body.age,
            firstName = body.firstName,
            lastName = body.lastName,
            photo = body.photo
        )

        val savedUser = userRepository.save(userEntity)

        return savedUser.toModel()
    }

    private fun generateToken() = (1..32).map { (('a'..'z') + ('A'..'Z') + ('0'..'9')).random() }.joinToString("")

    private val sha256 = MessageDigest.getInstance("SHA-512")

    private fun getPasswordHash(password: String): String {
        val bytes = sha256.digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun getAuthorizationUserEntity(token: String): UserEntity {
        val strippedToken = token.removePrefix("Bearer ")

        return userRepository.findByToken(strippedToken).getOrElse { throw UnauthorizedException("Invalid token") }
    }

}