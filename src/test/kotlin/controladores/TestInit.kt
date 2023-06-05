package controladores

import aplicacion.DifficultApp
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Profile
import org.springframework.http.*
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import utilidades.clean
import java.time.LocalDate

@Profile("test") @ActiveProfiles("test") @DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [DifficultApp::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
open class TestInit {
    @LocalServerPort private val port = 0
    @Autowired private val restTemplate: TestRestTemplate? = null

    private val diaCero: LocalDate = LocalDate.now().minusMonths(4L).minusDays(7L)
    protected val productos: MutableList<String> = mutableListOf()
    protected var id: String = ""
    protected var setItemCarritoID1: String = ""
    protected var setItemCarritoID9: String = ""
    protected var getItemCarritoID1: String = ""
    protected var getItemCarritoID9: String = ""

    @BeforeAll open fun beforeAll() {
        val producto1 = get("/producto/1") ?: ""
        val producto9 = get("/producto/9") ?: ""
        val consulta = put("/usuario/ingresar", clean("""
            {
                "usuarioNombre": "Bonifacio",
                "contrasenia": "1234"
            }
        """))

        this.id = consulta?.body.toString()

        this.setItemCarritoID1 = setItemCarrito(producto1)
        this.setItemCarritoID9 = setItemCarrito(producto9)

        this.getItemCarritoID1 = getItemCarrito(producto1, 1L)
        this.getItemCarritoID9 = getItemCarrito(producto9, 2L)
    }

    fun post(pathParametros: String, body: String?): ResponseEntity<String>? {
        return restTemplate?.postForEntity(
            "http://localhost:$port$pathParametros",
            HttpEntity(body, HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }),
            String::class.java,
        )
    }

    fun put(pathParametros: String, body: String?): ResponseEntity<String>? {
        return restTemplate?.exchange(
            "http://localhost:$port$pathParametros",
            HttpMethod.PUT,
            HttpEntity(body, HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }),
            String::class.java
        )
    }

    fun get(pathParametros: String): String? {
        return restTemplate!!.getForObject(
            "http://localhost:$port$pathParametros",
            String::class.java
        )
    }

    fun delete(pathParametros: String) {
        restTemplate?.delete("http://localhost:$port$pathParametros")
    }

    /* dominio de los test */
    fun usuarioBase(vararg factura: String): String {
        val otrasCompras = factura.fold("") { acc, it -> "$acc,$it" }

        return clean("""
            {
                "imagen": "9N6dG0I",
                "apellido": "Gomez",
                "nombre": "Bonifacio",
                "edad": 46,
                "saldo": 1500000.0,
                "comprasRealizadas": [
                    {
                        "ordenDeCompra": 1,
                        "fechaDeCompra": "${LocalDate.now()}",
                        "cantidadDeArticulos": 2,
                        "importeTotal": 2820.42
                    }
                    $otrasCompras
                ]
            }
        """)
    }

    fun facturaID9() = clean("""
        {
            "ordenDeCompra": 9,
            "fechaDeCompra": "${LocalDate.now()}",
            "cantidadDeArticulos": 2,
            "importeTotal": 93821.625
        }
    """)

    fun comboID1() = clean("""
        {
            "imagen": "B7yWM0f",
            "nombre": "combo",
            "descripcion": "pisos y pinturas",
            "puntaje": 1,
            "paisDeOrigen": "Argentina",
            "lotes": [
                {
                    "fechaIngreso": "${diaCero.plusDays(42L)}",
                    "cantidadDeUnidades": 200
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(39L)}",
                    "cantidadDeUnidades": 177
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(40L)}",
                    "cantidadDeUnidades": 51
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(41L)}",
                    "cantidadDeUnidades": 233
                }
            ],
            "items": [
                {
                    "producto": "Cañuelas",
                    "cantidad": 1
                },
                {
                    "producto": "Lourdes",
                    "cantidad": 2
                },
                {
                    "producto": "Cañuelas",
                    "cantidad": 3
                },
                {
                    "producto": "Alberdi",
                    "cantidad": 4
                }
            ],
            "precio": 4242.69,
            "tipo": "Combo"
        }
    """)

    fun pisoID2() = clean("""
        {
            "imagen": "i0QUlrl",
            "nombre": "Cañuelas",
            "descripcion": "Cerámica de interior",
            "puntaje": 0,
            "paisDeOrigen": "Argentina",
            "lotes": [
                {
                    "fechaIngreso": "${diaCero.plusDays(16L)}",
                    "cantidadDeUnidades": 68
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(80L)}",
                    "cantidadDeUnidades": 194
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(128L)}",
                    "cantidadDeUnidades": 245
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(23L)}",
                    "cantidadDeUnidades": 220
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(112L)}",
                    "cantidadDeUnidades": 248
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(48L)}",
                    "cantidadDeUnidades": 75
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(96L)}",
                    "cantidadDeUnidades": 231
                },
                {
                    "fechaIngreso": "$diaCero",
                    "cantidadDeUnidades": 135
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(64L)}",
                    "cantidadDeUnidades": 140
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(32L)}",
                    "cantidadDeUnidades": 3
                }
            ],
            "transito": "NORMAL",
            "terminacion": "BRILLANTE",
            "precio": 1007.1,
            "tipo": "Piso",
            "medidas_x": 50.0,
            "medidas_y": 50.0
        }
    """)

    fun pinturaID10() = clean("""
        {
            "imagen": "OVDWsPw",
            "nombre": "Pro 720",
            "descripcion": "Látex interior / exterior Mate",
            "puntaje": 3,
            "paisDeOrigen": "Argentina",
            "lotes": [
                {
                    "fechaIngreso": "${diaCero.plusDays(40L)}",
                    "cantidadDeUnidades": 246
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(72L)}",
                    "cantidadDeUnidades": 184
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(56L)}",
                    "cantidadDeUnidades": 224
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(88L)}",
                    "cantidadDeUnidades": 127
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(104L)}",
                    "cantidadDeUnidades": 60
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(8L)}",
                    "cantidadDeUnidades": 227
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(24L)}",
                    "cantidadDeUnidades": 247
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(31L)}",
                    "cantidadDeUnidades": 147
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(15L)}",
                    "cantidadDeUnidades": 83
                },
                {
                    "fechaIngreso": "${diaCero.plusDays(120L)}",
                    "cantidadDeUnidades": 12
                }
            ],
            "volumen": 20.0,
            "color": "#FFFFFF",
            "rendimiento": 11.0,
            "precio": 8375.0625,
            "tipo": "Pintura"
        }
    """)

    init {
        productos.apply {
            add(clean("""
                {
                    "imagen": "B7yWM0f",
                    "nombre": "combo",
                    "descripcion": "pisos y pinturas",
                    "puntaje": 1,
                    "paisDeOrigen": "Argentina",
                    "precio": 4242.69,
                    "tipo": "Combo"
                }
            """))
            add(clean("""
                {
                    "imagen": "i0QUlrl",
                    "nombre": "Cañuelas",
                    "descripcion": "Cerámica de interior",
                    "puntaje": 0,
                    "paisDeOrigen": "Argentina",
                    "medidas_x": 50.0,
                    "medidas_y": 50.0,
                    "precio": 1007.1,
                    "tipo": "Piso"
                }
            """))
            add(clean("""
                {
                    "imagen": "ADox8Gd",
                    "nombre": "Lourdes",
                    "descripcion": "Porcelanatos",
                    "puntaje": 1,
                    "paisDeOrigen": "Argentina",
                    "medidas_x": 53.0,
                    "medidas_y": 53.0,
                    "precio": 1813.3200000000002,
                    "tipo": "Piso"
                }
            """))
            add(clean("""
                {
                    "imagen": "X078aKC",
                    "nombre": "Cañuelas",
                    "descripcion": "Cerámicas de interior",
                    "puntaje": 2,
                    "paisDeOrigen": "Argentina",
                    "medidas_x": 50.0,
                    "medidas_y": 50.0,
                    "precio": 1381.3200000000002,
                    "tipo": "Piso"
                }
            """))
            add(clean("""
                {
                    "imagen": "GBQPO2t",
                    "nombre": "Alberdi",
                    "descripcion": "Cerámicas de interior",
                    "puntaje": 3,
                    "paisDeOrigen": "Argentina",
                    "medidas_x": 46.0,
                    "medidas_y": 46.0,
                    "precio": 1250.1000000000001,
                    "tipo": "Piso"
                }
            """))
            add(clean("""
                {
                    "imagen": "w3hU5Rh",
                    "nombre": "Cortines",
                    "descripcion": "Cerámicas de interior",
                    "puntaje": 4,
                    "paisDeOrigen": "Argentina",
                    "medidas_x": 30.0,
                    "medidas_y": 45.0,
                    "precio": 1124.1000000000001,
                    "tipo": "Piso"
                }
            """))
            add(clean("""
                {
                    "imagen": "B9SKC7X",
                    "nombre": "Lume",
                    "descripcion": "Revestimiento para pared",
                    "puntaje": 5,
                    "paisDeOrigen": "Brasil",
                    "medidas_x": 33.0,
                    "medidas_y": 60.0,
                    "precio": 1340.1000000000001,
                    "tipo": "Piso"
                }
            """))
            add(clean("""
                {
                    "imagen": "z4L9wKa",
                    "nombre": "Holztek",
                    "descripcion": "Interior",
                    "puntaje": 1,
                    "paisDeOrigen": "China",
                    "medidas_x": 60.0,
                    "medidas_y": 60.0,
                    "precio": 2834.1,
                    "tipo": "Piso"
                }
            """))
            add(clean("""
                {
                    "imagen": "Xye0Fsj",
                    "nombre": "Egger",
                    "descripcion": "Colocación: Sistema Just Click. Uso recomendado: residencial",
                    "puntaje": 2,
                    "paisDeOrigen": "Alemania",
                    "medidas_x": 19.2,
                    "medidas_y": 129.2,
                    "precio": 1862.1000000000001,
                    "tipo": "Piso"
                }
            """))
            add(clean("""
                {
                    "imagen": "OVDWsPw",
                    "nombre": "Pro 720",
                    "descripcion": "Látex interior / exterior Mate",
                    "puntaje": 3,
                    "paisDeOrigen": "Argentina",
                    "volumen": 20.0,
                    "color": "#FFFFFF",
                    "precio": 8375.0625,
                    "tipo": "Pintura"
                }
            """))
            add(clean("""
                {
                    "imagen": "UWgeFVx",
                    "nombre": "Loxon",
                    "descripcion": "Larga duración Látex interior Mate",
                    "puntaje": 4,
                    "paisDeOrigen": "Argentina",
                    "volumen": 20.0,
                    "color": "#FFFFFF",
                    "precio": 1719.4049999999997,
                    "tipo": "Pintura"
                }
            """))
            add(clean("""
                {
                    "imagen": "InxwUZF",
                    "nombre": "Z10",
                    "descripcion": "Supercubritivo Látex interior Mate",
                    "puntaje": 5,
                    "paisDeOrigen": "Argentina",
                    "volumen": 20.0,
                    "color": "#FFFFFF",
                    "precio": 1719.4049999999997,
                    "tipo": "Pintura"
                }
            """))
            add(clean("""
                {
                    "imagen": "lDRv89C",
                    "nombre": "Satinol",
                    "descripcion": "Esmalte sintético Satinado",
                    "puntaje": 1,
                    "paisDeOrigen": "Argentina",
                    "volumen": 4.0,
                    "color": "#FFFFFF",
                    "precio": 4905.067500000001,
                    "tipo": "Pintura"
                }
            """))
            add(clean("""
                {
                    "imagen": "zFvkLFJ",
                    "nombre": "Chalk Paint",
                    "descripcion": "Pintura de tiza",
                    "puntaje": 2,
                    "paisDeOrigen": "Argentina",
                    "volumen": 1.0,
                    "color": "#A49A9B",
                    "precio": 1598.6924999999999,
                    "tipo": "Pintura"
                }
            """))
            add(clean("""
                {
                    "imagen": "gvXh5YV",
                    "nombre": "Cetol Classic",
                    "descripcion": "Satinado",
                    "puntaje": 3,
                    "paisDeOrigen": "Argentina",
                    "volumen": 1.0,
                    "color": "#A49A9B",
                    "precio": 5899.9275,
                    "tipo": "Pintura"
                }
            """))
            add(clean("""
                {
                    "imagen": "sqUUqQ7",
                    "nombre": "Albalatex Ultralavable",
                    "descripcion": "Látex interior Mate",
                    "puntaje": 4,
                    "paisDeOrigen": "Argentina",
                    "volumen": 20.0,
                    "color": "#FFFFFF",
                    "precio": 22302.97875,
                    "tipo": "Pintura"
                }
            """))
            add(clean("""
                {
                    "imagen": "qkslWvL",
                    "nombre": "Brikol",
                    "descripcion": "Impregnante para ladrillose",
                    "puntaje": 5,
                    "paisDeOrigen": "Argentina",
                    "volumen": 4.0,
                    "color": "#A27A65",
                    "precio": 5916.8025,
                    "tipo": "Pintura"
                }
            """))
        }
    }

    private fun setItemCarrito(json: String, cantidad: Int = 10): String {
        val treeNodes = ObjectMapper().readTree(json)

        return clean("""
            {
                "productoId": ${treeNodes["id"]},
                "loteId": ${treeNodes["lotes"][0]["id"]},
                "cantidad": $cantidad
            }
        """)
    }

    private fun getItemCarrito(json: String, id: Long, cantidad: Int = 10): String {
        val treeNodes = ObjectMapper().readTree(json)

        return clean("""
            {
                "id": $id,
                "productoId": ${treeNodes["id"]},
                "loteId": ${treeNodes["lotes"][0]["id"]},
                "cantidad": $cantidad,
                "precio": ${cantidad * treeNodes["precio"].doubleValue()},
                "nombre": ${treeNodes["nombre"]},
                "descripcion": ${treeNodes["descripcion"]}
            }
        """)
    }
}