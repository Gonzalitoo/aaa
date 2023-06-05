package controladores

import org.junit.jupiter.api.*
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestCarritoControllerComprar : TestInit() {
    @BeforeAll override fun beforeAll() {
        super.beforeAll()

        post("/carrito/$id/agregar", setItemCarritoID1)
        post("/carrito/$id/agregar", setItemCarritoID9)
        post("/carrito/$id/comprar", null)
    }

    @DisplayName("Actualización sin body, espero que no actualize nada")
    @Order(1) @Test fun comprobar_compra_usuario() {
        val usuarioRespuesta = get("/usuario/perfil/$id")

        /* descuento de 93821.625, al saldo inicial de 1500000.0 con resultado 1406178.375 */
        val usuarioEsperado = usuarioBase(
            facturaID9()
        ).replace(
            "\"saldo\":1500000.0,",
            "\"saldo\":1406178.375,"
        )

        JSONAssert.assertEquals(usuarioEsperado, usuarioRespuesta, JSONCompareMode.LENIENT)
    }

    @DisplayName("Actualización sin body, espero que no actualize nada")
    @Order(2) @Test
    fun comprobar_compra_producto() {
        val pisoRespuesta = get("/producto/1")
        val pinturaRespuesta = get("/producto/9")

        /* el descuento de 10 items comprados en el test anterior */
        val pisoEsperado = pisoID2().replace(
            "\"cantidadDeUnidades\": 125",
            "\"cantidadDeUnidades\": 115"
        )

        /* el descuento de 10 items comprados en el test anterior */
        val pinturaEsperado = pinturaID10().replace(
            "\"cantidadDeUnidades\": 217",
            "\"cantidadDeUnidades\": 207"
        )

        JSONAssert.assertEquals(pisoEsperado, pisoRespuesta, JSONCompareMode.LENIENT)
        JSONAssert.assertEquals(pinturaEsperado, pinturaRespuesta, JSONCompareMode.LENIENT)
    }
}