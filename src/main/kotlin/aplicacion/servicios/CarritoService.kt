package aplicacion.servicios

import aplicacion.deserealizadores.ItemCarritoDes
import aplicacion.dominio.carrito.Carrito
import aplicacion.dominio.carrito.ItemCarrito
import aplicacion.dominio.usuario.Factura
import aplicacion.dominio.usuario.ItemFactura
import aplicacion.excepciones.CarritoExcepcion
import aplicacion.repositorios.mongo.RepoProductos
import aplicacion.repositorios.mysql.RepoUsuarios
import aplicacion.repositorios.neo4j.RepoFacturasGrafito
import aplicacion.repositorios.neo4j.RepoUsuariosGrafito
import aplicacion.repositorios.redis.RepoCarritoReal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class CarritoService {
    @Autowired
    private lateinit var repoCarritoReal: RepoCarritoReal
    @Autowired
    private lateinit var repoUsuarios: RepoUsuarios
    @Autowired
    private lateinit var repoProductos: RepoProductos
    @Autowired
    private lateinit var repoFacturasGrafito: RepoFacturasGrafito

    @Transactional
    fun agregarItem(uid: Long, itemCarrito: ItemCarritoDes) {
        this.validarUsuario(uid)
        val carrito = repoCarritoReal.findById(uid).orElse( Carrito(uid) )
        val producto = repoProductos.findById(itemCarrito.productoId).get()
        val itemAActualizar = carrito.items.find{ it.loteId == itemCarrito.loteId && it.productoId == itemCarrito.productoId }
        if( itemAActualizar != null ){
            carrito.items.remove(itemAActualizar)
            itemAActualizar.updateCantidad(ItemCarrito(itemCarrito,producto))
            carrito.items.add(itemAActualizar)
        } // a mejorar
        else{
            carrito.items.add(ItemCarrito(itemCarrito,producto))
        }
        repoCarritoReal.save(carrito)
    }

    @Transactional
    fun quitarItem(uid: Long, itemId: Long) {
        this.validarUsuario(uid)
        val carrito = repoCarritoReal.findById(uid).get()
        val itemABorrar: ItemCarrito? =  carrito.items.find{ it.id == itemId }
        carrito.items.remove(itemABorrar)
        repoCarritoReal.save(carrito)
    }

    @Transactional
    fun limpiarCarrito(uid: Long) {
        this.validarUsuario(uid)
        repoCarritoReal.deleteById(uid)
    }

    @Transactional(readOnly = true)
    fun items(uid: Long): MutableList<ItemCarrito> {
        this.validarUsuario(uid)
        return repoCarritoReal.findById(uid).orElse(Carrito(uid)).items
    }

    @Transactional
    fun finalizarCompra(uid: Long) {
        this.validarUsuario(uid)
        try {
            val user = repoUsuarios.findUsuarioAndFacturaByUsuarioId(uid).get()
            val carrito = repoCarritoReal.findById(uid).get()
            val factura = Factura(crearItemsFacturas(carrito.items),user)
            user.comprar(factura)
            repoUsuarios.save(user)
            repoCarritoReal.delete(carrito)
            repoFacturasGrafito.save(factura)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    private fun validarUsuario(uid: Long) {
        if (!repoUsuarios.existsById(uid)) throw CarritoExcepcion("La solicitud requiere autenticación")
    }

    private fun crearItemsFacturas(itemsCarrito: MutableList<ItemCarrito>): List<ItemFactura> {
        val productos = repoProductos.findAllById(itemsToIds(itemsCarrito))
        val itemsFactura = itemsCarrito.map { ic -> ic.itemFactura(productos) }
        repoProductos.saveAll(productos)
        return itemsFactura
    }

    private fun itemsToIds(itemsCarrito: MutableList<ItemCarrito>): MutableList<String> {
        return itemsCarrito.map { it.productoId } as MutableList<String>
    }
}