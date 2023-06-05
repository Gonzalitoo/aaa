package aplicacion.dominio

import aplicacion.dominio.producto.Lote
import aplicacion.dominio.producto.Producto
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@MappedSuperclass
open class Item {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore var id: Long? = null

    @OneToOne
    @JsonIgnore lateinit var producto: Producto

    @OneToOne
    @JsonIgnore lateinit var lote: Lote

    var cantidad: Int = 0
}