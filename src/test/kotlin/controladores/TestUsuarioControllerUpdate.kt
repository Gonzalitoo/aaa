package controladores

import org.junit.jupiter.api.*
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import utilidades.clean

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestUsuarioControllerUpdate : TestInit() {
    @DisplayName("Actualización sin body, espero que no actualize nada")
    @Order(1) @Test fun update_0() {
        val getInicial = get("/usuario/perfil/$id")
        put("/usuario/perfil/$id/editar" , null)
        val getFinal = get("/usuario/perfil/$id")
        val getInicialEsperado = usuarioBase()

        JSONAssert.assertEquals(getInicialEsperado, getInicial, JSONCompareMode.LENIENT)
        JSONAssert.assertEquals(getInicialEsperado, getFinal, JSONCompareMode.LENIENT)
    }

    @DisplayName("Un usuario carga la sube (saldo)")
    @Order(2) @Test fun update_1() {
        val getInicial = get("/usuario/perfil/$id")

        put("/usuario/perfil/$id/editar", clean("""
            {
                "saldo": 3000000.0
            }
        """))

        val getFinal = get("/usuario/perfil/$id")

        val getInicialEsperado = usuarioBase()
        val getFinalEsperado = usuarioBase().replace(
        "\"saldo\":1500000.0,",
        "\"saldo\":3000000.0,"
        )

        JSONAssert.assertEquals(getInicialEsperado, getInicial, JSONCompareMode.LENIENT)
        JSONAssert.assertEquals(getFinalEsperado, getFinal, JSONCompareMode.LENIENT)
    }

    @DisplayName("Un usuario sa cambia el nombre")
    @Order(3) @Test fun update_2() {
        val getInicial = get("/usuario/perfil/$id")

        put("/usuario/perfil/$id/editar", clean("""
            {
                "nombre": "Malefic"
            }
        """))

        val getFinal = get("/usuario/perfil/$id")

        val getInicialEsperado = usuarioBase().replace(
            "\"saldo\":1500000.0,",
            "\"saldo\":3000000.0,"
        )

        val getFinalEsperado = getInicialEsperado.replace(
            "\"nombre\":\"Bonifacio\",",
            "\"nombre\":\"Malefic\","
        )

        JSONAssert.assertEquals(getInicialEsperado, getInicial, JSONCompareMode.LENIENT)
        JSONAssert.assertEquals(getFinalEsperado, getFinal, JSONCompareMode.LENIENT)
    }

    @DisplayName("Un usuario sa cambia el apellido")
    @Order(4) @Test fun update_3() {
        val getInicial = get("/usuario/perfil/$id")

        put("/usuario/perfil/$id/editar", clean("""
            {
                "apellido": "Malefic"
            }
        """))

        val getFinal = get("/usuario/perfil/$id")

        val getInicialEsperado = usuarioBase().replace(
            "\"saldo\":1500000.0,",
            "\"saldo\":3000000.0,"
        ).replace(
            "\"nombre\":\"Bonifacio\",",
            "\"nombre\":\"Malefic\","
        )

        val getFinalEsperado = getInicialEsperado.replace(
            "\"apellido\":\"Gomez\",",
            "\"apellido\":\"Malefic\","
        )

        JSONAssert.assertEquals(getInicialEsperado, getInicial, JSONCompareMode.LENIENT)
        JSONAssert.assertEquals(getFinalEsperado, getFinal, JSONCompareMode.LENIENT)
    }

    @DisplayName("Un usuario sa cambia el apellido y carga 1500000")
    @Order(5) @Test fun update_4() {
        val getInicial = get("/usuario/perfil/$id")

        put("/usuario/perfil/$id/editar", clean("""
            {
                "nombre": "MaleficN",
                "saldo": 4500000
            }
        """))

        val getFinal = get("/usuario/perfil/$id")

        val getInicialEsperado = usuarioBase().replace(
            "\"saldo\":1500000.0,",
            "\"saldo\":3000000.0,"
        ).replace(
            "\"nombre\":\"Bonifacio\",",
            "\"nombre\":\"Malefic\","
        ).replace(
            "\"apellido\":\"Gomez\",",
            "\"apellido\":\"Malefic\","
        )

        val getFinalEsperado = getInicialEsperado.replace(
            "\"saldo\":3000000.0,",
            "\"saldo\":4500000.0,"
        ).replace(
            "\"nombre\":\"Malefic\",",
            "\"nombre\":\"MaleficN\","
        )

        JSONAssert.assertEquals(getInicialEsperado, getInicial, JSONCompareMode.LENIENT)
        JSONAssert.assertEquals(getFinalEsperado, getFinal, JSONCompareMode.LENIENT)
    }

    @DisplayName("Un usuario sa cambia el nombre y carga 1500000")
    @Order(6) @Test fun update_5() {
        val getInicial = get("/usuario/perfil/$id")

        put("/usuario/perfil/$id/editar", clean("""
            {
                "apellido": "MaleficN",
                "saldo": 6000000
            }
        """))

        val getFinal = get("/usuario/perfil/$id")

        val getInicialEsperado = usuarioBase().replace(
            "\"saldo\":1500000.0,",
            "\"saldo\":4500000.0,"
        ).replace(
            "\"nombre\":\"Bonifacio\",",
            "\"nombre\":\"MaleficN\","
        ).replace(
            "\"apellido\":\"Gomez\",",
            "\"apellido\":\"Malefic\","
        )

        val getFinalEsperado = getInicialEsperado.replace(
            "\"saldo\":4500000.0,",
            "\"saldo\":6000000.0,"
        ).replace(
            "\"apellido\":\"Malefic\",",
            "\"apellido\":\"MaleficN\","
        )

        JSONAssert.assertEquals(getInicialEsperado, getInicial, JSONCompareMode.LENIENT)
        JSONAssert.assertEquals(getFinalEsperado, getFinal, JSONCompareMode.LENIENT)
    }

    @DisplayName("Un usuario sa cambia el nombre y el apellido")
    @Order(7) @Test fun update_6() {
        val getInicial = get("/usuario/perfil/$id")

        put("/usuario/perfil/$id/editar", clean("""
            {
                "nombre": "MaleficNN",
                "apellido": "MaleficNN"
            }
        """))

        val getFinal = get("/usuario/perfil/$id")

        val getInicialEsperado = usuarioBase().replace(
            "\"saldo\":1500000.0,",
            "\"saldo\":6000000.0,"
        ).replace(
            "\"nombre\":\"Bonifacio\",",
            "\"nombre\":\"MaleficN\","
        ).replace(
            "\"apellido\":\"Gomez\",",
            "\"apellido\":\"MaleficN\","
        )

        val getFinalEsperado = getInicialEsperado.replace(
            "\"nombre\":\"MaleficN\",",
            "\"nombre\":\"MaleficNN\","
        ).replace(
            "\"apellido\":\"MaleficN\",",
            "\"apellido\":\"MaleficNN\","
        )

        JSONAssert.assertEquals(getInicialEsperado, getInicial, JSONCompareMode.LENIENT)
        JSONAssert.assertEquals(getFinalEsperado, getFinal, JSONCompareMode.LENIENT)
    }

    @DisplayName("Un usuario sa cambia el nombre, el apellido y quito 4500000")
    @Order(8) @Test fun update_7() {
        val getInicial = get("/usuario/perfil/$id")

        put("/usuario/perfil/$id/editar", clean("""
            {
                "nombre": "Bonifacio",
                "apellido": "Gomez",
                "saldo": 1500000
            }
        """))

        val getFinal = get("/usuario/perfil/$id")

        val getInicialEsperado = usuarioBase().replace(
        "\"saldo\":1500000.0,",
        "\"saldo\":6000000.0,"
        ).replace(
        "\"nombre\":\"Bonifacio\",",
        "\"nombre\":\"MaleficNN\","
        ).replace(
        "\"apellido\":\"Gomez\",",
        "\"apellido\":\"MaleficNN\","
        )

        val getFinalEsperado = getInicialEsperado.replace(
            "\"saldo\":6000000.0,",
            "\"saldo\":1500000.0,"
        ).replace(
            "\"nombre\":\"MaleficNN\",",
            "\"nombre\":\"Bonifacio\","
        ).replace(
            "\"apellido\":\"MaleficNN\",",
            "\"apellido\":\"Gomez\","
        )

        JSONAssert.assertEquals(getInicialEsperado, getInicial, JSONCompareMode.LENIENT)
        JSONAssert.assertEquals(getFinalEsperado, getFinal, JSONCompareMode.LENIENT)
    }
}