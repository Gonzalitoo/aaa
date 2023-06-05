package repositorios

import aplicacion.DifficultApp
import aplicacion.dominio.usuario.Usuario
import aplicacion.repositorios.mysql.RepoUsuarios
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [DifficultApp::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestRepoUsuarios {
    @Autowired private lateinit var repoUsuarios: RepoUsuarios

    @Test fun prueba1() {
//        val usuario = repoUsuarios.findById(1L)
        val u1 = Usuario()
        val u2 = Usuario()

        println(u1 == u2)
//        println(usuario.id)
//        println(usuario.usuarioNombre)
//        println(usuario.contrasenia)
//        println(usuario.nombre)
//        println(usuario.apellido)
//        println(usuario.saldo)
//        println(usuario.imagen)
//        println(usuario.edad)

    }
}