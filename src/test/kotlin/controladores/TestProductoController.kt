package controladores

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

class TestProductoController : TestInit() {
    @Test fun findById_combo() {
        val comboRespuesta = get("/producto/17")
        val comboEsperado = comboID1()

        JSONAssert.assertEquals(comboEsperado, comboRespuesta, JSONCompareMode.LENIENT)
    }

    @Test fun findById_piso() {
        val pisoRespuesta = get("/producto/1")
        val pisoEsperado = pisoID2()

        JSONAssert.assertEquals(pisoEsperado, pisoRespuesta, JSONCompareMode.LENIENT)
    }

    @Test fun findById_pintura() {
        val pinturaRespuesta = get("/producto/9")
        val pinturaEsperado = pinturaID10()

        JSONAssert.assertEquals(pinturaEsperado, pinturaRespuesta, JSONCompareMode.LENIENT)
    }

    /*  hay tres variables, nombre, puntaje y pais, por lo tanto si
        aplicamos combinatoria, las posibilidades de combinación
        sin repetición son 8, las cuapes son
        1 - (0) nombre (0) puntaje (0) pais
        2 - (0) nombre (0) puntaje (1) pais
        3 - (0) nombre (1) puntaje (0) pais
        4 - (0) nombre (1) puntaje (1) pais
        5 - (1) nombre (0) puntaje (0) pais
        6 - (1) nombre (0) puntaje (1) pais
        7 - (1) nombre (1) puntaje (0) pais
        8 - (1) nombre (1) puntaje (1) pais
    */

    @DisplayName("1 - (0) nombre (0) puntaje (0) pais")
    @Test fun buscarPor_opcion_1() {
        val listaRespuesta = get("/producto/traer")
        val listaEsperada = "$productos"

        JSONAssert.assertEquals(listaEsperada, listaRespuesta, JSONCompareMode.LENIENT)
    }

    @DisplayName("2 - (0) nombre (0) puntaje (1) pais")
    @Test fun buscarPor_opcion_2() {
        val listaRespuesta = get("/producto/traer?paisDeOrigen=China")
        val listaEsperada = "[${productos[7]}]"

        JSONAssert.assertEquals(listaEsperada, listaRespuesta, JSONCompareMode.LENIENT)
    }

    @DisplayName("3 - (0) nombre (1) puntaje (0) pais")
    @Test fun buscarPor_opcion_3() {
        val listaRespuesta = get("//producto/traer?puntaje=5")
        val listaEsperada = "[${productos[6]},${productos[11]},${productos[16]}]"

        JSONAssert.assertEquals(listaEsperada, listaRespuesta, JSONCompareMode.LENIENT)
    }

    @DisplayName("4 - (0) nombre (1) puntaje (1) pais")
    @Test fun buscarPor_opcion_4() {
        val listaRespuesta = get("/producto/traer?puntaje=5&paisDeOrigen=Argentina")
        val listaEsperada = "[${productos[11]},${productos[16]}]"

        JSONAssert.assertEquals(listaEsperada, listaRespuesta, JSONCompareMode.LENIENT)
    }

    @DisplayName("5 - (1) nombre (0) puntaje (0) pais")
    @Test fun buscarPor_opcion_5() {
        val listaRespuesta = get("/producto/traer?nombre=Ca")
        val listaEsperada = "[${productos[1]},${productos[3]}]"

        JSONAssert.assertEquals(listaEsperada, listaRespuesta, JSONCompareMode.LENIENT)
    }

    @DisplayName("6 - (1) nombre (0) puntaje (1) pais")
    @Test fun buscarPor_opcion_6() {
        val listaRespuesta = get("/producto/traer?nombre=x&paisDeOrigen=Argentina")
        val listaEsperada = "[${productos[10]},${productos[15]}]"

        JSONAssert.assertEquals(listaEsperada, listaRespuesta, JSONCompareMode.LENIENT)
    }

    @DisplayName("7 - (1) nombre (1) puntaje (0) pais")
    @Test fun buscarPor_opcion_7() {
        val listaRespuesta = get("/producto/traer?nombre=i&puntaje=4")
        val listaEsperada = "[${productos[5]},${productos[16]}]"

        JSONAssert.assertEquals(listaEsperada, listaRespuesta, JSONCompareMode.LENIENT)
    }

    @DisplayName("8 - (1) nombre (1) puntaje (1) pais")
    @Test fun buscarPor_opcion_8() {
        val listaRespuesta = get("//producto/traer?nombre=lb&puntaje=3&paisDeOrigen=Argentina")
        val listaEsperada = "[${productos[4]},${productos[15]}]"

        JSONAssert.assertEquals(listaEsperada, listaRespuesta, JSONCompareMode.LENIENT)
    }
}