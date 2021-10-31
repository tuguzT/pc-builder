package io.github.tuguzt.pcbuilder.presentation.repository

import android.app.Activity
import android.app.Application
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.tuguzt.pcbuilder.domain.model.component.Component
import io.github.tuguzt.pcbuilder.presentation.model.user.User
import io.github.tuguzt.pcbuilder.presentation.repository.mock.MockComponentRepository
import io.github.tuguzt.pcbuilder.presentation.repository.room.RoomComponentRepository
import io.github.tuguzt.pcbuilder.presentation.repository.room.RoomDatabase
import io.github.tuguzt.pcbuilder.presentation.repository.room.RoomUserRepository
import io.github.tuguzt.pcbuilder.presentation.view.observeOnce
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
    val localUserRepository: Repository<String, User>
        get() = pLocalUserRepository!!

    @JvmStatic
    val currentUser: LiveData<User> get() = pCurrentUser

    @JvmStatic
    fun initRoom(application: Application) {
        val roomDatabase = RoomDatabase.getInstance(application)

        val componentRepository = RoomComponentRepository(roomDatabase)
        val userRepository = RoomUserRepository(roomDatabase)

        pLocalComponentRepository = componentRepository
        pLocalUserRepository = userRepository
    }

    @JvmStatic
    fun setUser(user: User, activity: Activity) {
        localUserRepository.findById(user.username).observeOnce {
            if (it == null) {
                localUserRepository.add(user)
            } else {
                localUserRepository.update(user)
            }
            activity.userSharedPreferences.edit {
                putString("username", user.username)
                putString("email", user.email)
                putString("password", user.password)
                putString("imageUri", user.imageUri?.toString())
            }
            pCurrentUser.value = user
        }
    }

    private var pLocalComponentRepository: Repository<String, Component>? = null
    private var pLocalUserRepository: Repository<String, User>? = null

    private val pCurrentUser = MutableLiveData<User>()
}
