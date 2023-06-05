package aplicacion.servicios

import aplicacion.deserealizadores.LoggerDTO
import aplicacion.dominio.ClickLog
import aplicacion.dominio.ClickLogDTO
import aplicacion.dominio.producto.Producto
import aplicacion.dominio.producto.ProductoDTO
import aplicacion.repositorios.mongo.RepoClickLogs
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class ClickLogsService {
    @Autowired
    private lateinit var repoClickLogs: RepoClickLogs

    @Transactional(readOnly = true)
    fun traerLogByIdUsuario(id: Long): ClickLogDTO {
        return repoClickLogs.nombre(id).orElseThrow {
            Exception("El usuario es re gil y no vio ningún producto")
        }
    }
    @Transactional
    fun crearLog(clickLog: LoggerDTO) {
        repoClickLogs.save(ClickLog(clickLog.idProducto,
                                    clickLog.idUsuario,
                                    clickLog.productoImagen,
                                    clickLog.productoNombre,
                                    clickLog.productoDescripcion,
                                    clickLog.productoValoracion))
    }

    @Transactional
    fun recomendacionesGeneral():List<ProductoDTO>{
        return repoClickLogs.productosMasVistos()
    }
}