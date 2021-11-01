package io.github.tuguzt.pcbuilder.presentation.repository

import android.app.Activity
import android.app.Application
import androidx.core.content.edit
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.model.user.UserSealed
import io.github.tuguzt.pcbuilder.presentation.repository.mock.MockComponentRepository
import io.github.tuguzt.pcbuilder.presentation.repository.room.RoomComponentRepository
import io.github.tuguzt.pcbuilder.presentation.repository.room.RoomDatabase
import io.github.tuguzt.pcbuilder.presentation.view.userSharedPreferences

/**
 * Object for access to all repository types used in the application.
 */
object RepositoryAccess {
    @JvmStatic
    val localComponentRepository: Repository<String, Component>
        get() = pLocalComponentRepository
            ?: MockComponentRepository.also { pLocalComponentRepository = it }

    @JvmStatic
    val currentUser get() = pCurrentUser

    @JvmStatic
    fun initRoom(application: Application) {
        val roomDatabase = RoomDatabase.getInstance(application)

        val componentRepository = RoomComponentRepository(roomDatabase)
        pLocalComponentRepository = componentRepository
    }

    @JvmStatic
    fun setUser(user: UserSealed, activity: Activity) {
        activity.userSharedPreferences.edit {
            putString("username", user.username)
            putString("email", user.email)
            putString("password", user.password)
            putString("imageUri", user.imageUri)
        }
        pCurrentUser = user
    }

    private var pLocalComponentRepository: Repository<String, Component>? = null

    private var pCurrentUser: UserSealed? = null
}
