package excepciones

import aplicacion.dominio.usuario.Usuario
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import utilidades.clean
import java.util.*

//import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

class TestUsuarioControllerException : TestInit() {
    // #################################################################################################################
    // # dominio de objetos - Mocks                                                                                    #
    // #################################################################################################################
    private fun saveMocks() {
        `when`(
            repoUsuarios.save(Mockito.any(Usuario::class.java))
        ).thenReturn(
            Usuario()
        )
    }

    private fun findByUsuarioNombreAndContraseniaMocks() {
        `when`(
            repoUsuarios.findByUsuarioNombreAndContrasenia("Bonifacio", "1234")
        ).thenReturn(
            Optional.of(1L)
        )
    }

    private fun findByUsuarioNombreAndContrasenia_usuarioInexistenteMocks() {
        `when`(
            repoUsuarios.findByUsuarioNombreAndContrasenia("NN", "333")
        ).thenReturn(
            Optional.empty()
        )
    }

    private fun usuarioBase(): Usuario {
        return Usuario().apply {
            nombre = "Bonifacio"
            apellido = "Gomez"
            edad = 46
            saldo = 1500000.00

            usuarioNombre = "Bonifacio"
            contrasenia = "1234"
            imagen = "9N6dG0I"
        }
    }

    private fun findByIdMocks() {
        `when`(
            repoUsuarios.findById(1L)
        ).thenReturn(
            Optional.of(this.usuarioBase())
        )
    }

    private fun findById_usuarioInexistenteMocks() {
        `when`(
            repoUsuarios.findById(99L)
        ).thenReturn(
            Optional.empty()
        )
    }

    // #################################################################################################################
    // # test sobre el método "create"                                                                                 #
    // #################################################################################################################
    @Test fun creacionDeUsuario() {
        this.saveMocks()

        val crearUsuario = post("/usuario/registrar", clean("""
            {
                "usuarioNombre": "CachitoGonzales",
                "contrasenia": "1234",
                "imagen": "imagen",
                "nombre": "cachito",
                "apellido": "gonzales",
                "edad": 99,
                "saldo": 1000000.0
            }
        """))

        mockMvc.perform(crearUsuario)
            .andExpect(status().isOk)
    }

    @Test fun crearUsuario_jsonMalFormado() {
        this.saveMocks()

        val crearUsuario = post("/usuario/registrar", clean("""
            {
                "usuarioNombre": "CachitoGonzales"
                "contrasenia": "1234",
                "imagen": "imagen",
                "nombre": "cachito",
                "apellido": "gonzales",
                "edad": 99,
                "saldo": 1000000.0
            }
        """))

        mockMvc.perform(crearUsuario)
            .andExpect(status().isBadRequest)
            .andExpect(content().string(clean("""
                {
                    "status": false,
                    "mensaje": "Solicitud JSON con formato incorrecto",
                    "statusCode": 400
                }
            """)))
    }

    @Test fun crearUsuario_jsonFormadoIncorrecto() {
        this.saveMocks()

        val crearUsuario = post("/usuario/registrar", clean("""
            {
                "usuarioNombre": "",
                "contrasenia": "1234",
                "imagen": "imagen",
                "nombre": "cachito",
                "apellido": "gonzales",
                "edad": 99,
                "saldo": 1000000.0
            }
        """))

        mockMvc.perform(crearUsuario)
            .andExpect(status().isBadRequest)
            .andExpect(content().string(clean("""
                {
                    "status": false,
                    "mensaje": "Nodo json incorrecto o inexistente",
                    "statusCode": 400
                }
            """)))
    }

    // #################################################################################################################
    // # test sobre el método "findByUsuarioNombreAndContrasenia"                                                      #
    // #################################################################################################################
    @Test fun ingresoDeUsuario() {
        this.findByUsuarioNombreAndContraseniaMocks()

        val usuarioId = put("/usuario/ingresar", clean("""
            {
                "usuarioNombre": "Bonifacio",
                "contrasenia": "1234"
            }
        """))

        mockMvc.perform(usuarioId)
            .andExpect(status().isOk)
            .andExpect(content().string(clean("1")))
    }

    @Test fun ingresoDeUsuario_jsonMalFormado() {
        this.findByUsuarioNombreAndContraseniaMocks()

        val usuarioId = put("/usuario/ingresar", clean("""
            {
                "usuarioNombre": "Bonifacio"
                "contrasenia": "1234"
            }
        """))

        mockMvc.perform(usuarioId)
            .andExpect(status().isBadRequest)
            .andExpect(content().string(clean("""
                {
                    "status": false,
                    "mensaje": "Solicitud JSON con formato incorrecto",
                    "statusCode": 400
                }
            """)))
    }

    @Test fun ingresoDeUsuario_jsonFormadoIncorrecto() {
        this.findByUsuarioNombreAndContraseniaMocks()

        val usuarioId = put("/usuario/ingresar", clean("""
            {
                "usuarioNombre": "",
                "contrasenia": "1234"
            }
        """))

        mockMvc.perform(usuarioId)
            .andExpect(status().isNotFound)
            .andExpect(content().string(clean("""
                {
                    "status": false,
                    "mensaje": "Usuario inexistente",
                    "statusCode": 404
                }
            """)))
    }

    @Test fun usuarioIngresar_usuarioInexistente() {
        this.findByUsuarioNombreAndContrasenia_usuarioInexistenteMocks()

        val usuarioId = put("/usuario/ingresar", clean("""
            {
                "usuarioNombre": "NN",
                "contrasenia": "333"
            }
        """))

        mockMvc.perform(usuarioId)
            .andExpect(status().isNotFound)
            .andExpect(content().string(clean("""
                {
                    "status": false,
                    "mensaje": "Usuario inexistente",
                    "statusCode": 404
                }
            """)))
    }

    // #################################################################################################################
    // # test sobre el método "findById"                                                                               #
    // #################################################################################################################
    @Test fun usuarioPerfil() {
        this.findByIdMocks()

        val usuarioPerfil = get("/usuario/perfil/1")

        mockMvc.perform(usuarioPerfil)
            .andExpect(status().isOk)
            .andExpect(content().string(
                ObjectMapper().writeValueAsString(this.usuarioBase()))
            )
    }

    @Test fun usuarioPerfil_errorDeTipo() {
        this.findByIdMocks()

        val usuarioPerfil = get("/usuario/perfil/xx")

        mockMvc.perform(usuarioPerfil)
            .andExpect(status().isBadRequest)
            .andExpect(content().string(clean("""
                {
                    "status": false,
                    "mensaje": "uid debe ser de tipo long",
                    "statusCode": 400
                }
            """)))
    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // No hay error para atrapar !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//    @Test fun usuarioPerfil_usuarioInexistente() {
//        this.findById_usuarioInexistenteMocks()
//
//        val usuarioPerfil = get("/usuario/perfil/99")
//
//        mockMvc.perform(usuarioPerfil)
//            .andExpect(status().isNotFound)
//            .andExpect(content().string(clean("""
//                {
//                    "status": false,
//                    "mensaje": "Usuario inexistente",
//                    "statusCode": 404
//                }
//            """)))
//    }

    // #################################################################################################################
    // # test sobre el método "update"                                                                                 #
    // #################################################################################################################
    @Test fun usuarioEditar() {
        this.saveMocks()
        this.findByIdMocks()

        val editarUsuario = put("/usuario/perfil/1/editar", clean("""
            {
                "saldo": 999999999
            }
        """))

        mockMvc.perform(editarUsuario)
            .andExpect(status().isOk)
    }

    @Test fun usuarioEditar_jsonIncorrecto() {
        this.saveMocks()
        this.findByIdMocks()

        val editarUsuario = put("/usuario/perfil/1/editar", clean("""
            {
                "saldo": "cacahuete"
            }
        """))

        mockMvc.perform(editarUsuario)
            .andExpect(status().isBadRequest)
            .andExpect(content().string(clean("""
                {
                    "status": false,
                    "mensaje": "Solicitud JSON con formato incorrecto",
                    "statusCode": 400
                }
            """)))
    }

    @Test fun usuarioEditar_errorDeTipo() {
        this.saveMocks()
        this.findByIdMocks()

        val editarUsuario = put("/usuario/perfil/xxx/editar", clean("""
            {
                "saldo": 999999999
            }
        """))

        mockMvc.perform(editarUsuario)
            .andExpect(status().isBadRequest)
            .andExpect(content().string(clean("""
                {
                    "status": false,
                    "mensaje": "uid debe ser de tipo long",
                    "statusCode": 400
                }
            """)))
    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Corregir el acento !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @Test fun usuarioEditar_errorDeActualizacion() {
        this.saveMocks()
        this.findById_usuarioInexistenteMocks()

        val editarUsuario = put("/usuario/perfil/99/editar", clean("""
            {
                "saldo": 999999999
            }
        """))

        mockMvc.perform(editarUsuario)
            .andExpect(status().isNotFound)
            .andExpect(content().string(clean("""
                {
                    "status": false,
                    "mensaje": "OcurriÃ³ un error mientras se intentaba actualizar los datos de usuario",
                    "statusCode": 404
                }
            """)))
    }
}