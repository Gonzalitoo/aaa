package aplicacion.servicios

import aplicacion.deserealizadores.UsuarioActualizacionDes
import aplicacion.dominio.usuario.Factura
import aplicacion.dominio.usuario.Usuario
import aplicacion.excepciones.NotFoundException
import aplicacion.repositorios.mysql.RepoUsuarios
import aplicacion.repositorios.neo4j.RepoUsuariosGrafito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UsuarioService {
    @Autowired private lateinit var repoUsuarios: RepoUsuarios
    @Autowired private lateinit var repoUsuariosGrafito: RepoUsuariosGrafito

    @Transactional
    fun create(usuario: Usuario) {
        repoUsuarios.save(usuario)
        repoUsuariosGrafito.save(usuario)

    }

    @Transactional(readOnly = true)
    fun findByUsuarioNombreAndContrasenia(usuarioNombre: String, contrasenia: String): Long? {
        return repoUsuarios.findByUsuarioNombreAndContrasenia(usuarioNombre, contrasenia).orElseThrow{
            throw NotFoundException("Usuario inexistente")
        }
    }

    @Transactional(readOnly = true)
    fun findById(uid: Long): Usuario {
        val user =  repoUsuarios.findById(uid).get()
        user.facturas  = repoUsuarios.findFacturasByUserId(uid,PageRequest.of(0, 5)) as MutableList<Factura>
        return user
    }

    @Transactional
    fun update(uid: Long, usuario: UsuarioActualizacionDes) {
        repoUsuarios.findById(uid).map { usuarioRepo ->
            if (usuario.nombre != "") usuarioRepo.nombre = usuario.nombre
            if (usuario.apellido != "") usuarioRepo.apellido = usuario.apellido
            if (usuario.saldo > 0) usuarioRepo.saldo = usuario.saldo
            repoUsuariosGrafito.save(usuarioRepo)
            repoUsuarios.save(usuarioRepo)
        }.orElseThrow {
            throw NotFoundException("Ocurrió un error mientras se intentaba actualizar los datos de usuario")
        }
    }
}
