package aplicacion.servicios


import aplicacion.dominio.producto.Producto
import aplicacion.repositorios.mongo.RepoProductos
import aplicacion.repositorios.neo4j.RepoFacturasGrafito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RecomendacionesService {

    @Autowired
    private lateinit var repoFacturasGrafito: RepoFacturasGrafito
    @Autowired
    private lateinit var repoProductos: RepoProductos


    fun recomendacionProductosByProductoId(productoId:String,usuarioId:Long?): List<Producto> {
        val listaIds:MutableIterable<String> = if (usuarioId != null){
            repoFacturasGrafito.recomendacionProductosByProductoId(productoId, usuarioId)
        }else{
            repoFacturasGrafito.recomendacionProductosByProductoIdSinUsuario(productoId)
        }
        return repoProductos.findAllById(listaIds)
    }
}