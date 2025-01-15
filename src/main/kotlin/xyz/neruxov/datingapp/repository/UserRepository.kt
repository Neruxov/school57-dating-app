package xyz.neruxov.datingapp.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import xyz.neruxov.datingapp.dao.UserEntity
import xyz.neruxov.datingapp.dto.model.User
import java.util.*

/**
 * @author <a href="https://github.com/Neruxov">Neruxov</a>
 */
interface UserRepository : CrudRepository<UserEntity, Long> {

    fun findAllByGender(pageable: Pageable, gender: User.Gender): List<UserEntity>

    fun findByToken(token: String): Optional<UserEntity>

}