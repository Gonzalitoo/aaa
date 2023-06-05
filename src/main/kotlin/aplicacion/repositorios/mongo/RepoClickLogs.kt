package aplicacion.repositorios.mongo

import aplicacion.dominio.ClickLog
import aplicacion.dominio.ClickLogDTO
import aplicacion.dominio.producto.Producto
import aplicacion.dominio.producto.ProductoDTO
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

interface RepoClickLogs: MongoRepository<ClickLog, String> {

    @Aggregation(pipeline = [
        "{ \$match:  {idUsuario: ?0}}",
        "{ \$group:  {_id:\$idProducto," +
                      "idUsuario:{\$first:\$idUsuario}," +
                      "cantidad:{\$sum:1}," +
                      "idProducto: {\$first: \$idProducto}," +
                      "productoImagen:{\$first: \$productoImagen}," +
                      "productoNombre:{\$first: \$productoNombre}," +
                      "productoDescripcion:{\$first: \$productoDescripcion}," +
                      "productoValoracion:{\$first: \$productoValoracion} } }",
        "{ \$sort: {cantidad:-1} }",
        "{ \$limit: 1 }"
        ]
    )
    fun nombre(idUsuario:Long):Optional<ClickLogDTO>


    @Aggregation(pipeline = [
        "{\$group:" +
                "{_id:\$idProducto," +
                "cantidad:{\$sum:1}," +
                "idProducto:{\$first:\$idProducto}," +
                "productoImagen:{\$first:\$productoImagen}," +
                "productoNombre:{\$first:\$productoNombre}," +
                "productoDescripcion:{\$first: \$productoDescripcion} } }," ,
        "{\$sort:{cantidad:-1}}," ,
        "{\$limit:4}"
    ])
    fun productosMasVistos():List<ProductoDTO>

}
