package aplicacion.dominio.producto

import com.fasterxml.jackson.annotation.JsonView
import aplicacion.excepciones.BusinessException
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import javax.persistence.*

class Lote {
    @Id
    @JsonView(View.Lote::class)var id: Long = 0
    @JsonView(View.Lote::class)var fechaIngreso: LocalDate = LocalDate.now()
    @JsonView(View.Lote::class)var cantidadDeUnidades: Int = 0

    fun tieneMasDe4Meses(): Boolean {
        return fechaIngreso.plusMonths(4).isAfter(LocalDate.now())
    }

    fun descontar(cantidad: Int) {
        this.checkStock(cantidad)
        cantidadDeUnidades -= cantidad
    }

    private fun checkStock(cantidad: Int) {
        if (cantidadDeUnidades < cantidad) throw BusinessException(
            "la compra excede en ${cantidad - cantidadDeUnidades} el stock disponible en el lote $id"
        )
    }
}