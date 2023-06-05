package aplicacion.dominio.producto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.neo4j.core.schema.Node

@JsonView(View.ProductoLista::class)
@Document("Producto")
abstract class Producto {
    @Id
    @JsonView(View.ProductoRecomendado::class) open lateinit var id: String
    @JsonView(View.ProductoRecomendado::class) var imagen: String = ""
    @JsonView(View.ProductoRecomendado::class) open var nombre: String = ""
    @JsonView(View.ProductoRecomendado::class) open var descripcion: String = ""
    open var puntaje: Int = 0
    open var paisDeOrigen: String = ""

    @JsonIgnore
    open var precioBase: Double = 0.0

    @JsonProperty
    @JsonView(View.ProductoConLote::class)
    open var lotes: MutableSet<Lote> = mutableSetOf()

    fun agregarLote(lote: Lote) {
        lotes.add(lote)
    }

    fun quitarLote(lote: Lote) {
        lotes.remove(lote)
    }

    fun lotePorId(loteId: Long): Lote {
        return lotes.first { it.id == loteId }
    }

    @JsonProperty
    @JsonView(View.ProductoRecomendado::class) open fun precio(): Double {
        return precioBase * descuento()
    }

    open fun descuento(): Double {
        return if (hayProductoConMasDe4Meses()) 0.90 else 1.00
    }


    open fun hayProductoConMasDe4Meses(): Boolean {
        return lotes.any { it.tieneMasDe4Meses() }
    }

    @JsonProperty
    fun tipo(): String? {
        return this::class.simpleName
    }
}

data class ProductoDTO(val idProducto:String,
                       val productoImagen:String,
                       val productoNombre:String,
                       val productoDescripcion:String,
                       )