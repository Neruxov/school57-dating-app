package xyz.neruxov.datingapp.dto.model

import xyz.neruxov.datingapp.dao.UserEntity

data class User(
    val id: Long,
    val login: String,
    val gender: Gender,
    val age: Int,
    val firstName: String,
    val lastName: String,
    val photo: String,
) {

    fun toEntity() = UserEntity(
        id = id,
        login = login,
        gender = gender,
        age = age,
        firstName = firstName,
        lastName = lastName,
        photo = photo
    )

    enum class Gender {
        MALE, FEMALE
    }

}