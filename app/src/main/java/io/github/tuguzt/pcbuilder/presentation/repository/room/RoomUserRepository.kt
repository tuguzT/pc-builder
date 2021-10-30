package io.github.tuguzt.pcbuilder.presentation.repository.room

import androidx.lifecycle.LiveData
import io.github.tuguzt.pcbuilder.presentation.model.user.User
import io.github.tuguzt.pcbuilder.presentation.repository.Repository
import io.github.tuguzt.pcbuilder.presentation.repository.room.dto.user.UserDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Room repository of [users][User].
 *
 * @see User
 */
internal class RoomUserRepository(private val roomDatabase: RoomDatabase) :
    Repository<String, User> {

    private val userDao get() = roomDatabase.userDao

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override val allData = userDao.getAll()

    override fun findById(id: String): LiveData<out User> = userDao.findByUsername(username = id)

    override fun add(item: User) {
        val user = UserDto(item)
        coroutineScope.launch {
            userDao.insert(user)
        }
    }

    override fun update(item: User) {
        val user = UserDto(item)
        coroutineScope.launch {
            userDao.update(user)
        }
    }

    override fun remove(item: User) {
        val user = UserDto(item)
        coroutineScope.launch {
            userDao.delete(user)
        }
    }

    override fun clear() {
        coroutineScope.launch {
            userDao.deleteAll()
        }
    }
}
