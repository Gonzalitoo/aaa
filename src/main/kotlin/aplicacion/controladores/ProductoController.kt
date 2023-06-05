package aplicacion.controladores


import aplicacion.dominio.producto.Producto
import aplicacion.dominio.producto.View
import aplicacion.servicios.ProductoService
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], methods = [RequestMethod.GET,RequestMethod.POST])
class ProductoController {
    @Autowired private lateinit var productoService: ProductoService

    @JsonView(View.DetalleProducto::class)
    @GetMapping("/producto/{idProducto}")
    fun findById(
       @PathVariable idProducto: String
    ): Producto {
        return productoService.findById(idProducto)
    }
    @JsonView(View.ProductoLista::class)
    @GetMapping("/producto/traer")
    fun buscarPor(
        @RequestParam(value = "nombre", required = false) nombre: String,
        @RequestParam(value = "puntaje", required = false, defaultValue = "0") puntaje: Int,
        @RequestParam(value = "paisDeOrigen", required = false, defaultValue = "") paisDeOrigen: String?
    ): List<Producto> {
        return productoService.buscarPor(nombre, paisDeOrigen, puntaje)
    }
}

