package dominio

import aplicacion.dominio.producto.Lote
import aplicacion.dominio.producto.simple.Pintura
import aplicacion.dominio.producto.simple.Piso
import aplicacion.dominio.producto.simple.Terminacion
import aplicacion.dominio.producto.simple.Transito
import aplicacion.dominio.usuario.Factura
import aplicacion.dominio.usuario.ItemFactura
import aplicacion.dominio.usuario.Usuario
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDate

open class TestInit {
    protected lateinit var usuario:Usuario
    protected lateinit var producto0:Piso
    protected lateinit var producto1:Pintura
    protected lateinit var producto2:Piso
    protected lateinit var item0:ItemFactura
    protected lateinit var item1:ItemFactura
    protected lateinit var item2:ItemFactura
    protected lateinit var factura27265:Factura
    protected lateinit var factura1746670:Factura

    @BeforeEach
    fun init() {
        usuario = Usuario().apply {
            usuarioNombre = "Bonifacio"
            contrasenia = "1234"
            saldo = 1500000.0
        }

        producto0 = Piso().apply {
            id ="2"
            imagen = "001"
            nombre = "Cañuelas"
            descripcion = "Cerámica de interior"
            puntaje = 0
            paisDeOrigen = "Argentina"
            lotes = mutableSetOf(
                Lote().apply {
                    id = 8414
                    fechaIngreso = LocalDate.parse("2021-11-19")
                    cantidadDeUnidades = 125
                },
                Lote().apply {
                    id = 9613
                    fechaIngreso = LocalDate.parse("2021-12-05")
                    cantidadDeUnidades = 68
                }
            )
            transito = Transito.NORMAL
            terminacion = Terminacion.BRILLANTE
            precioBase = 1119.00
            medidasX = 50.0
            medidasY = 50.0
        }

        producto1 = Pintura().apply {
            id = "10"
            imagen = "009"
            nombre = "Pro 720"
            descripcion = "Látex interior / exterior Mate"
            puntaje = 3
            paisDeOrigen = "Argentina"
            lotes = mutableSetOf(
                Lote().apply {
                    id = 4121
                    fechaIngreso = LocalDate.parse("2021-11-27")
                    cantidadDeUnidades = 217
                },
                Lote().apply {
                    id = 1323
                    fechaIngreso = LocalDate.parse("2021-12-13")
                    cantidadDeUnidades = 247
                }
            )
            volumen = 20.0
            color = "#FFFFFF"
            rendimiento = 11.0
            precioBase = 1528.36
        }

        producto2 = Piso().apply {
            id ="2"
            imagen = "001"
            nombre = "Cañuelas"
            descripcion = "Cerámica de interior"
            puntaje = 0
            paisDeOrigen = "Argentina"
            lotes = mutableSetOf(
                Lote().apply {
                    id = 8414
                    fechaIngreso = LocalDate.parse("2021-11-19")
                    cantidadDeUnidades = 125
                },
                Lote().apply {
                    id = 9613
                    fechaIngreso = LocalDate.parse("2021-12-05")
                    cantidadDeUnidades = 68
                }
            )
            transito = Transito.NORMAL
            terminacion = Terminacion.BRILLANTE
            precioBase = 5111900.00
            medidasX = 50.0
            medidasY = 50.0
        }

        item0 = ItemFactura(
            producto0,
            producto0.lotes.first(),
            10
        )

        item1 = ItemFactura(
            producto1,
            producto1.lotes.first(),
            10
        )

        item2 = ItemFactura(
            producto2,
            producto2.lotes.first(),
            1
        )

        factura27265 = Factura(listOf(item0, item1))
        factura1746670 = Factura(listOf(item0, item1, item2))
    }
}