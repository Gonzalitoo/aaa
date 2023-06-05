package aplicacion.dominio.usuario

import aplicacion.dominio.producto.Lote
import aplicacion.dominio.producto.Producto
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.neo4j.core.schema.*

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GenerationType
import javax.persistence.Id as idJPA
import javax.persistence.GeneratedValue as generatedValueJPA

@Entity
@Node("Factura")
class Factura() {
    @Id @GeneratedValue
    @idJPA @generatedValueJPA(strategy= GenerationType.IDENTITY)
    var ordenDeCompra: Long? = null
    var cantidadDeArticulos: Int = 0
    var importeTotal: Double = 0.00

    @Relationship(type="COMPRA", direction = Relationship.Direction.INCOMING)
    @javax.persistence.Transient @JsonIgnore var user:Usuario = Usuario()

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    var fechaDeCompra: LocalDateTime = LocalDateTime.now()

    @Transient
    @Relationship(type="ITEMS")
    @JsonIgnore var items: List<ItemFactura> = listOf()

    constructor(items: List<ItemFactura>, user:Usuario) : this() {
        //this.user = user
        this.items = items
        this.cantidadDeArticulos = items.sumOf { it.cantidad }
        this.importeTotal = items.sumOf { it.precioCompra }
    }
}

@Node("Articulo")
class ItemFactura(){
    @Id @GeneratedValue
    @JsonIgnore var id: Long? = null

    @JsonIgnore lateinit var producto: Producto

    @JsonIgnore lateinit var lote: Lote

    var cantidad: Int = 0
    var precioCompra: Double = 0.0

    constructor(producto: Producto, lote: Lote, cantidad: Int) : this() {
        this.producto = producto
        this.lote = lote
        this.cantidad = cantidad

        lote.descontar(cantidad)
        precioCompra = cantidad * producto.precio()
    }
}