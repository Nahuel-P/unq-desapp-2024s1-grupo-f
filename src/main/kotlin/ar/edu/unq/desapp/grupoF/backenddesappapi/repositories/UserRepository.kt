package ar.edu.unq.desapp.grupoF.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.UserExchange
import org.springframework.stereotype.Repository
import java.util.concurrent.atomic.AtomicLong

@Repository
class UserRepository : IUserRepository {
    private val users = HashMap<Long, UserExchange>()
    private val counter = AtomicLong()

    override fun registerUser(user: UserExchange): UserExchange {
        val id = counter.incrementAndGet()
        user.id = id
        users[id] = user
        return user
    }

    override fun getUsers(): Map<Long, UserExchange> {
        return users
    }
}