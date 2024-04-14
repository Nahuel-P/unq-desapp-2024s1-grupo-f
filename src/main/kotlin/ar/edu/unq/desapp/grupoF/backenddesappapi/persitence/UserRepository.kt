package ar.edu.unq.desapp.grupoF.backenddesappapi.persitence

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, Long>{

    override fun findById(id: Long) : Optional<User>
}