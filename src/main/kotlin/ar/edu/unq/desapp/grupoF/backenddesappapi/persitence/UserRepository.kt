package ar.edu.unq.desapp.grupoF.backenddesappapi.persitence

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import org.springframework.stereotype.Repository
import java.util.concurrent.atomic.AtomicLong

@Repository
class UserRepository : IUserRepository {
    private val users = HashMap<Long, User>()
    private val counter = AtomicLong()

    override fun registerUser(user: User): User {
        val id = counter.incrementAndGet()
        user.id = id
        users[id] = user
        return user
    }

    override fun getUsers(): Map<Long, User> {
        return users
    }
}