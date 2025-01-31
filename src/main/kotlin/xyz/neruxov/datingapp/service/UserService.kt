package xyz.neruxov.datingapp.service

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import xyz.neruxov.datingapp.dao.UserEntity
import xyz.neruxov.datingapp.dao.UserReactionEntity
import xyz.neruxov.datingapp.dto.model.User
import xyz.neruxov.datingapp.dto.request.UserReactRequest
import xyz.neruxov.datingapp.dto.request.UserRegisterRequest
import xyz.neruxov.datingapp.dto.response.UserRegisterResponse
import xyz.neruxov.datingapp.exception.type.StatusCodeException
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
        val user = getAuthorizationUserEntity(token)

        val users = userRepository.findAllByGender(pageable, user.gender.opposite())

        return PageImpl(users.map { it.toModel() })
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

    fun react(userId: Long, request: UserReactRequest, token: String) {
        val authorizedUserEntity = getAuthorizationUserEntity(token)
        if (authorizedUserEntity.id == userId) throw StatusCodeException(400, "You can't react to yourself")

        val targetUser = userRepository.findById(userId)
            .getOrElse { throw StatusCodeException(404, "User not found") }

        targetUser.reactions += UserReactionEntity(
            userId = targetUser.id,
            targetId = authorizedUserEntity.id,
            reaction = request.reaction
        )

        userRepository.save(targetUser)
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