package io.github.tuguzt.pcbuilder.presentation.repository.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import io.github.tuguzt.pcbuilder.presentation.model.user.User
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.user.UserDto

/**
 * Data Access Object for [User].
 *
 * @see User
 */
@Dao
interface UserDao : IDao<UserDto> {
    @Query("SELECT * FROM user WHERE username = :username")
    fun findByUsername(username: String): LiveData<UserDto>
}
