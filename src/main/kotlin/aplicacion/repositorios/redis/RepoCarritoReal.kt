package aplicacion.repositorios.redis

import aplicacion.dominio.carrito.Carrito
import org.springframework.data.repository.CrudRepository
import java.util.*

interface RepoCarritoReal: CrudRepository<Carrito,Long>{

    override fun findById(uid:Long): Optional<Carrito>
}