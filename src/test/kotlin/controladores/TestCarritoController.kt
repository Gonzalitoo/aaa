package controladores

import org.junit.jupiter.api.*
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestCarritoController: TestInit() {
    @Order(1) @Test fun agregarItem() {
        val post = post("/carrito/$id/agregar", setItemCarritoID1)
        val get = get("/carrito/$id/items")

        Assertions.assertEquals(200, post?.statusCodeValue)
        JSONAssert.assertEquals("[$getItemCarritoID1]", get, JSONCompareMode.LENIENT)
    }

    @Order(2) @Test fun quitarItem() {
        val getInicial = get("/carrito/$id/items")

        delete("/carrito/$id/quitar/1")
        delete("/carrito/$id/quitar/1")

        val getFinal = get("/carrito/$id/items")
        val getFinalEsperado = "[]"

        JSONAssert.assertEquals("[$getItemCarritoID1]", getInicial, JSONCompareMode.LENIENT)
        JSONAssert.assertEquals(getFinalEsperado, getFinal, JSONCompareMode.LENIENT)
    }

    @Order(3) @Test fun limpiarCarrito() {
        val postInicial = post("/carrito/$id/agregar", setItemCarritoID1)
        val postFinal = post("/carrito/$id/agregar", setItemCarritoID9)

        val getInicial = get("/carrito/$id/items")
        val getInicialEsperado = """[
            ${getItemCarritoID1.replace("\"id\":1", "\"id\":2")},
            ${getItemCarritoID9.replace("\"id\":2", "\"id\":3")}
        ]"""

        delete("/carrito/$id/limpiar")

        val getFinal = get("/carrito/$id/items")
        val getFinalEsperado = "[]"

        Assertions.assertEquals(200, postInicial?.statusCodeValue)
        Assertions.assertEquals(200, postFinal?.statusCodeValue)
        JSONAssert.assertEquals(getInicialEsperado, getInicial, JSONCompareMode.LENIENT)
        JSONAssert.assertEquals(getFinalEsperado, getFinal, JSONCompareMode.LENIENT)
    }

    @Order(4) @Test fun items() {
        val postInicial = post("/carrito/$id/agregar", setItemCarritoID1)
        val postFinal = post("/carrito/$id/agregar", setItemCarritoID9)

        val get = get("/carrito/$id/items")
        val getEsperado = """[
            ${getItemCarritoID1.replace("\"id\":1", "\"id\":4")},
            ${getItemCarritoID9.replace("\"id\":2", "\"id\":5")}
        ]"""

        Assertions.assertEquals(200, postInicial?.statusCodeValue)
        Assertions.assertEquals(200, postFinal?.statusCodeValue)
        JSONAssert.assertEquals(getEsperado, get, JSONCompareMode.LENIENT)
    }
}