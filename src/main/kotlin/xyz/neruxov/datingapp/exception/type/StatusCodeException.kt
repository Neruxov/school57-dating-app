package xyz.neruxov.datingapp.exception.type

/**
 * @author <a href="https://github.com/Neruxov">Neruxov</a>
 */
class StatusCodeException(
    val statusCode: Int,
    private val reason: String
) : RuntimeException(
    "$statusCode $reason"
) {

    fun toMap(): Map<String, Any> {
        return mapOf(
            "reason" to reason
        )
    }

}