package aplicacion.controladores

import aplicacion.deserealizadores.LoggerDTO
import aplicacion.dominio.ClickLogDTO
import aplicacion.dominio.producto.Producto
import aplicacion.dominio.producto.ProductoDTO
import aplicacion.servicios.ClickLogsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], methods = [RequestMethod.GET, RequestMethod.POST])
class ClickLogController {

    @Autowired
    private lateinit var clickLogsService: ClickLogsService

    @GetMapping("/logs/{id}")
    fun logsUsuario(@PathVariable id:Long): ClickLogDTO {
        return clickLogsService.traerLogByIdUsuario(id)
    }

    @PostMapping("/logs")
    fun crearLog(@RequestBody clickLog: LoggerDTO) {
        return clickLogsService.crearLog(clickLog)
    }

    @GetMapping("/logs/productosMasClickeados")
    fun productosMasClickeados():List<ProductoDTO>{
        return clickLogsService.recomendacionesGeneral()
    }


}