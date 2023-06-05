package aplicacion.controladores

import aplicacion.deserealizadores.UsuarioActualizacionDes
import aplicacion.deserealizadores.UsuarioCreacionDes
import aplicacion.deserealizadores.UsuarioLoginDes
import aplicacion.dominio.usuario.Usuario
import aplicacion.servicios.UsuarioService
import aplicacion.utilidades.mapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], methods = [RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET])
class UsuarioController {
    @Autowired private lateinit var usuarioService: UsuarioService

    @PostMapping("/usuario/registrar")
    fun create(
        @Validated @RequestBody usuario: UsuarioCreacionDes
    ) {
        usuarioService.create(mapper(usuario, Usuario::class))
    }

    @PutMapping("/usuario/ingresar")
    fun findByUsuarioNombreAndContrasenia(
        @RequestBody usuario:UsuarioLoginDes
    ): Long? {
        return usuarioService.findByUsuarioNombreAndContrasenia(usuario.usuarioNombre,usuario.contrasenia)
    }

    @GetMapping("/usuario/perfil/{uid}")
    fun findById(
        @PathVariable uid: Long
    ): Usuario {
        return usuarioService.findById(uid)
    }

    @PutMapping("/usuario/perfil/{uid}/editar")
    fun update(
        @RequestBody usuario: UsuarioActualizacionDes,
        @PathVariable uid: Long
    ) {
        usuarioService.update(uid, usuario)
    }
}