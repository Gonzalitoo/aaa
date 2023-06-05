package aplicacion.repositorios.mongo


import aplicacion.dominio.producto.DatosDeBusqueda
import aplicacion.dominio.producto.Producto
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*
import org.bson.types.ObjectId

interface RepoProductos : MongoRepository<Producto, String> {

    override fun findAllById(ids: MutableIterable<String>): List<Producto>

    @Query(" { \$and:[{nombre: {'\$regex':?0, '\$options': 'i' }}, {paisDeOrigen: {'\$regex':?1} }, {puntaje: {'\$gte':?2}}] }")
    fun buscarPor(nombre:String, pais:String?, puntaje:Int): List<Producto>
}