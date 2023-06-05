package aplicacion.dominio.producto.simple

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView
import aplicacion.dominio.producto.View
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Entity
import javax.persistence.Id


@Document("Producto")
@TypeAlias("Piso")
class Piso : ProductoSimple() {
    @JsonProperty("medidas_x") var medidasX: Double = 0.00
    @JsonProperty("medidas_y") var medidasY: Double = 0.00
    var transito: Transito = Transito.NORMAL
    var terminacion: Terminacion = Terminacion.BRILLANTE

    override fun recargo(): Double {
        return transito.recargo()
    }
}

enum class Transito {
    ALTO_TRANSITO {
        override fun recargo(): Double = 1.20
    },
    NORMAL {
        override fun recargo(): Double = 1.00
    };

    abstract fun recargo(): Double
}

enum class Terminacion {
    SATINADO, SEMI_SATINADO, BRILLANTE
}