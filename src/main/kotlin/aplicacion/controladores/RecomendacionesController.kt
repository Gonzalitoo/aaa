package aplicacion.controladores

import aplicacion.dominio.producto.Producto
import aplicacion.dominio.producto.View
import aplicacion.servicios.RecomendacionesService
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], methods = [RequestMethod.GET])
class RecomendacionesController {

    @Autowired
    private lateinit var recomendacionesService: RecomendacionesService

    @JsonView(View.ProductoRecomendado::class)
    @GetMapping(value= ["/producto/{pid}/usuario/{uid}", "/producto/{pid}/usuario"])
    fun recomendacionProductos(
        @PathVariable pid: String,
        @PathVariable(required = false) uid: Long?
    ): List<Producto> {
        return recomendacionesService.recomendacionProductosByProductoId(pid,uid)
    }


}