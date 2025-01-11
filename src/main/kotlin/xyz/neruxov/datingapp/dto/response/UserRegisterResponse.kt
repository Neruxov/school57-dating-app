package xyz.neruxov.datingapp.dto.response

data class UserRegisterResponse(
    val id: Long,
    val login: String,
    val token: String
)