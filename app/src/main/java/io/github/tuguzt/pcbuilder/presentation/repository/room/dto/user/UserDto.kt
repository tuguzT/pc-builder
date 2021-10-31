package io.github.tuguzt.pcbuilder.presentation.repository.room.dto.user

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import io.github.tuguzt.pcbuilder.presentation.model.user.User
import io.github.tuguzt.pcbuilder.presentation.model.user.UserOrdinal

/**
 * Data Transfer Object for [User].
 *
 * @see User
 */
@Entity(tableName = "user")
data class UserDto(
    @PrimaryKey override val username: String,
    override val email: String,
    override val password: String,
    @ColumnInfo(name = "image_uri") var imageUriString: String?,
) : UserOrdinal(username, email, password, imageUriString?.let { Uri.parse(it) }) {
    @Ignore
    override var imageUri = super.imageUri

    /**
     * Constructs [UserDto] from the [User].
     *
     * @see User
     */
    constructor(u: User) : this(u.username, u.email, u.password, u.imageUri?.toString())
}
