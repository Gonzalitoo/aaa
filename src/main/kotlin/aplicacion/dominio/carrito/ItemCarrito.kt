package aplicacion.dominio.carrito

import aplicacion.Keys
import aplicacion.deserealizadores.ItemCarritoDes
import aplicacion.dominio.producto.Lote
import aplicacion.dominio.producto.Producto
import aplicacion.dominio.usuario.ItemFactura
import aplicacion.dominio.usuario.Usuario
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import javax.persistence.GeneratedValue

@RedisHash("ItemCarrito")
class ItemCarrito {
    @Id
    var id: Long = Keys.plusItemCarrito()
    var productoId: String = ""
    var loteId: Long = 0
    var cantidad: Int = 0
    var imagen: String = ""
    var precio: Double = 0.0
    var nombre: String = ""
    var descripcion: String = ""

    constructor(){
    }
    constructor(item:ItemCarritoDes,producto:Producto){
        this.loteId = item.loteId
        this.productoId = item.productoId
        this.cantidad = item.cantidad
        this.nombre = producto.nombre
        this.imagen = producto.imagen
        this.descripcion = producto.descripcion
        this.precio = producto.precio() * this.cantidad
    }

    fun itemFactura(lista: List<Producto>): ItemFactura {
        val producto: Producto = lista.first { it.id == productoId }
        val lote: Lote = producto.lotePorId(loteId)

        return ItemFactura(producto, lote, cantidad)
     }

    fun updateCantidad(item: ItemCarrito) {
        this.cantidad += item.cantidad
        this.precio += item.precio
    }
}