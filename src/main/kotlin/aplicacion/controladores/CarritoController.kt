package aplicacion.controladores

import aplicacion.deserealizadores.ItemCarritoDes
import aplicacion.dominio.carrito.ItemCarrito
import aplicacion.servicios.CarritoService
import aplicacion.utilidades.mapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], methods = [RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET])
class CarritoController {
    @Autowired private lateinit var carritoService: CarritoService

    @PostMapping("/carrito/{uid}/agregar")
    fun agregarItem(
        @Validated @RequestBody itemCarrito: ItemCarritoDes,
        @PathVariable uid: Long
    ) {
        carritoService.agregarItem(uid,itemCarrito)
    }

    @DeleteMapping("/carrito/{uid}/quitar/{itemId}")
    fun quitarItem(
        @PathVariable uid: Long,
        @PathVariable itemId: Long
    ) {
        carritoService.quitarItem(uid, itemId)
    }

    @DeleteMapping("/carrito/{uid}/limpiar")
    fun limpiarCarrito(
        @PathVariable uid: Long
    ) {
        carritoService.limpiarCarrito(uid)
    }

    @GetMapping("/carrito/{uid}/items")
    fun items(
        @PathVariable uid: Long
    ): MutableList<ItemCarrito> {
        return carritoService.items(uid)
    }

    @PostMapping("/carrito/{uid}/comprar")
    fun finalizarCompra(
        @PathVariable uid: Long
    ) {
        carritoService.finalizarCompra(uid)
    }
}