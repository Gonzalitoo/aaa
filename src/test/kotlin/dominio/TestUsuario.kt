package dominio

import aplicacion.excepciones.BusinessException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class TestUsuario: TestInit() {
    @DisplayName("compra realizado por un usuario con saldo suficiente")
    @Test fun compra_con_saldo() {
        usuario.comprar(factura27265)
        Assertions.assertEquals(1469705.5, usuario.saldo)
    }

    @DisplayName("compra realizado por un usuario sin saldo suficiente")
    @Test fun compra_sin_saldo() {
        Assertions.assertThrows(
            BusinessException::class.java,
            { usuario.comprar(factura1746670) },
            "Salado insuficiente para esta compra"
        )
    }
}