package controladores

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import utilidades.clean

class TestUsuarioController : TestInit() {
    @Test fun create() {
        val resultado = post("/usuario/registrar", clean("""
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

        Assertions.assertEquals(200, resultado?.statusCodeValue)
    }

    @Test fun findByUsuarioNombreAndContrasenia() {
        val resultado = put("/usuario/ingresar", clean("""
            {
                "usuarioNombre": "Bonifacio",
                "contrasenia": "1234"
            }
        """))

        Assertions.assertEquals("1", resultado?.body.toString())
    }

    @Test fun findById() {
        val usuarioRespuesta = get("/usuario/perfil/$id")
        val usuarioEsperado = usuarioBase()

        JSONAssert.assertEquals(usuarioEsperado, usuarioRespuesta, JSONCompareMode.LENIENT)
    }
}