package xyz.neruxov.datingapp.dao

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import xyz.neruxov.datingapp.dto.model.UserReaction

@Entity
@Table(name = "user_reactions")
data class UserReactionEntity(

    @Id
    @GeneratedValue
    val id: Long = -1,

    val userId: Long = -1,

    val targetId: Long = -1,

    val reaction: Boolean = false

) {

    fun toModel() = UserReaction(
        from = userId,
        reaction = reaction
    )

    override fun hashCode(): Int {
        return (userId.toString() + reaction.toString()).hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as UserReactionEntity

        if (userId != other.userId) return false
        if (reaction != other.reaction) return false

        return true
    }

}