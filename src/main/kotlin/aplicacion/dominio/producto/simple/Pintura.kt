package aplicacion.dominio.producto.simple

import com.fasterxml.jackson.annotation.JsonView
import aplicacion.dominio.producto.View
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Entity
import javax.persistence.Id


@Document("Producto")
@TypeAlias("Pintura")
class Pintura : ProductoSimple() {
    var volumen: Double = 0.0
    var color: String = "#008000"
    var rendimiento: Double = 0.0

    override fun recargo(): Double {
        return if (rendimiento > 8.00) 1.25 else 1.00
    }
}