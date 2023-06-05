package aplicacion.servicios

import aplicacion.dominio.producto.Producto
import aplicacion.excepciones.NotFoundException
import aplicacion.repositorios.mongo.RepoProductos
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductoService {
    @Autowired private lateinit var repoProductos: RepoProductos

    @Transactional(readOnly = true)
    fun findById(idProducto: String): Producto{
        return repoProductos.findById(idProducto)
            .orElseThrow {throw NotFoundException("Producto inexistente")
        }
    }
    @Transactional(readOnly = true)
    fun buscarPor(nombre:String, pais:String?, puntaje:Int): List<Producto> {
       return repoProductos.buscarPor(nombre, pais, puntaje)
    }

}

