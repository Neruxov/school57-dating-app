package xyz.neruxov.datingapp.dao

import jakarta.persistence.*
import xyz.neruxov.datingapp.dto.model.User
import xyz.neruxov.datingapp.dto.model.User.Gender

@Entity
@Table(name = "users")
data class UserEntity(

    @Id
    @GeneratedValue
    val id: Long = -1,

    val login: String = "",

    @Enumerated(EnumType.STRING)
    val gender: Gender = Gender.MALE,

    val age: Int = -1,

    val firstName: String = "",

    val lastName: String = "",

    val photo: String = "",

    val passwordHash: String = "",

    val token: String = "",

    @OneToMany(mappedBy = "userId", cascade = [CascadeType.ALL], orphanRemoval = true)
    val reactions: MutableSet<UserReactionEntity> = mutableSetOf()

) {

    fun toModel() = User(
        id = id,
        login = login,
        gender = gender,
        age = age,
        firstName = firstName,
        lastName = lastName,
        photo = photo,
    )

}
