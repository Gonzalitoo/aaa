package aplicacion.dominio

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.persistence.Id

@Document("ClickLog")
class ClickLog {
    @Id
    open lateinit var id: String
     var idUsuario:Long?
     var idProducto:String
     var fechaClick:LocalDateTime
     var productoImagen:String
     var productoNombre:String
     var productoDescripcion:String
     var productoValoracion:Int

    constructor(idProducto:String, idUsuario: Long?,productoImagen:String,productoNombre:String,productoDescripcion:String,productoValoracion:Int){
        this.idUsuario = idUsuario
        this.idProducto = idProducto
        this.fechaClick = LocalDateTime.now()
        this.productoImagen = productoImagen
        this.productoNombre = productoNombre
        this.productoDescripcion = productoDescripcion
        this.productoValoracion = productoValoracion
    }
}

data class ClickLogDTO(val idProducto:String,
                       val idUsuario: Long?,
                       val cantidad: Long,
                       val productoImagen:String,
                       val productoNombre:String,
                       val productoDescripcion:String,
                       val productoValoracion:Int)