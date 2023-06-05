package aplicacion

import aplicacion.dominio.producto.Lote
import aplicacion.dominio.producto.compuesto.Combo
import aplicacion.dominio.producto.simple.*
import aplicacion.dominio.usuario.Factura
import aplicacion.dominio.usuario.ItemFactura
import aplicacion.dominio.usuario.Usuario
import aplicacion.repositorios.mongo.RepoProductos
import aplicacion.repositorios.mysql.RepoUsuarios
import aplicacion.repositorios.neo4j.RepoFacturasGrafito
import aplicacion.repositorios.neo4j.RepoUsuariosGrafito
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.math.abs
import kotlin.math.cos

@Service
open class DifficultBootstrap : InitializingBean {
    @Autowired private lateinit var repoUsuarios: RepoUsuarios
    @Autowired private lateinit var repoFacturasGrafito: RepoFacturasGrafito
    @Autowired private lateinit var repoProductos: RepoProductos

    private var listaDeProductos: MutableList<ProductoSimple> = mutableListOf()
    private var listaDeUsuarios: MutableList<Usuario> = mutableListOf()
    private var listaDeLotes: MutableList<Lote> = mutableListOf()

    private val log: Logger = LoggerFactory.getLogger(DifficultBootstrap::class.java)
    private var cantidadDeLotes: Int = 160

    private fun instanciarProductos() {
        listaDeProductos.add(Piso().apply {
            imagen = "i0QUlrl"
            nombre = "Cañuelas"
            descripcion = "Cerámica de interior"
            medidasX = 50.0
            medidasY = 50.0
            puntaje = 0
            paisDeOrigen = "Argentina"
            transito = Transito.NORMAL
            terminacion = Terminacion.BRILLANTE
            precioBase = 1119.00
        })

        listaDeProductos.add(Piso().apply {
            imagen = "ADox8Gd"
            nombre = "Lourdes"
            descripcion = "Porcelanatos"
            medidasX = 53.0
            medidasY = 53.0
            puntaje = 1
            paisDeOrigen = "Argentina"
            transito = Transito.ALTO_TRANSITO
            terminacion = Terminacion.SATINADO
            precioBase = 1679.00
        })

        listaDeProductos.add(Piso().apply {
            imagen = "X078aKC"
            nombre = "Cañuelardo"
            descripcion = "Cerámicas de interior"
            medidasX = 50.0
            medidasY = 50.0
            puntaje = 2
            paisDeOrigen = "Argentina"
            transito = Transito.ALTO_TRANSITO
            terminacion = Terminacion.SATINADO
            precioBase = 1279.00
        })

        listaDeProductos.add(Piso().apply {
            imagen = "GBQPO2t"
            nombre = "Alberdi"
            descripcion = "Cerámicas de interior"
            medidasX = 46.0
            medidasY = 46.0
            puntaje = 3
            paisDeOrigen = "Argentina"
            transito = Transito.NORMAL
            terminacion = Terminacion.SEMI_SATINADO
            precioBase = 1389.00
        })

        listaDeProductos.add(Piso().apply {
            imagen = "w3hU5Rh"
            nombre = "Cortines"
            descripcion = "Cerámicas de interior"
            medidasX = 30.0
            medidasY = 45.0
            puntaje = 4
            paisDeOrigen = "Argentina"
            transito = Transito.NORMAL
            terminacion = Terminacion.SEMI_SATINADO
            precioBase = 1249.00
        })

        listaDeProductos.add(Piso().apply {
            imagen = "B9SKC7X"
            nombre = "Lume"
            descripcion = "Revestimiento para pared"
            medidasX = 33.0
            medidasY = 60.0
            puntaje = 5
            paisDeOrigen = "Brasil"
            transito = Transito.NORMAL
            terminacion = Terminacion.SATINADO
            precioBase = 1489.00
        })

        listaDeProductos.add(Piso().apply {
            imagen = "z4L9wKa"
            nombre = "Holztek"
            descripcion = "Interior"
            medidasX = 60.0
            medidasY = 60.0
            puntaje = 1
            paisDeOrigen = "China"
            transito = Transito.NORMAL
            terminacion = Terminacion.BRILLANTE
            precioBase = 3149.00
        })

        listaDeProductos.add(Piso().apply {
            imagen = "Xye0Fsj"
            nombre = "Egger"
            descripcion = "Colocación: Sistema Just Click. Uso recomendado: residencial"
            medidasX = 19.2
            medidasY = 129.2
            puntaje = 2
            paisDeOrigen = "Alemania"
            transito = Transito.NORMAL
            terminacion = Terminacion.SEMI_SATINADO
            precioBase = 2069.00
        })

        listaDeProductos.add(Pintura().apply {
            imagen = "OVDWsPw"
            nombre = "Pro 720"
            descripcion = "Látex interior / exterior Mate"
            puntaje = 3
            paisDeOrigen = "Argentina"
            volumen = 20.0
            rendimiento = 11.0
            color = "#FFFFFF"
            precioBase = 7444.50
        })

        listaDeProductos.add(Pintura().apply {
            imagen = "UWgeFVx"
            nombre = "Loxon"
            descripcion = "Larga duración Látex interior Mate"
            puntaje = 4
            paisDeOrigen = "Argentina"
            volumen = 20.0
            rendimiento = 13.0
            color = "#FFFFFF"
            precioBase = 1528.36
        })

        listaDeProductos.add(Pintura().apply {
            imagen = "InxwUZF"
            nombre = "Z10"
            descripcion = "Supercubritivo Látex interior Mate"
            puntaje = 5
            paisDeOrigen = "Argentina"
            volumen = 20.0
            rendimiento = 13.0
            color = "#FFFFFF"
            precioBase = 1528.36
        })

        listaDeProductos.add(Pintura().apply {
            imagen = "lDRv89C"
            nombre = "Satinol"
            descripcion = "Esmalte sintético Satinado"
            puntaje = 1
            paisDeOrigen = "Argentina"
            volumen = 4.0
            rendimiento = 13.5
            color = "#FFFFFF"
            precioBase = 4360.06
        })

        listaDeProductos.add(Pintura().apply {
            imagen = "zFvkLFJ"
            nombre = "Chalk Paint"
            descripcion = "Pintura de tiza"
            puntaje = 2
            paisDeOrigen = "Argentina"
            volumen = 1.0
            rendimiento = 13.0
            color = "#A49A9B"
            precioBase = 1421.06
        })

        listaDeProductos.add(Pintura().apply {
            imagen = "gvXh5YV"
            nombre = "Cetol Classic"
            descripcion = "Satinado"
            puntaje = 3
            paisDeOrigen = "Argentina"
            volumen = 1.0
            rendimiento = 15.0
            color = "#A49A9B"
            precioBase = 5244.38
        })

        listaDeProductos.add(Pintura().apply {
            imagen = "sqUUqQ7"
            nombre = "Albalatex (choreo)"
            descripcion = "Látex interior Mate"
            puntaje = 4
            paisDeOrigen = "Argentina"
            volumen = 20.0
            rendimiento = 14.0
            color = "#FFFFFF"
            precioBase = 19824.87
        })

        listaDeProductos.add(Pintura().apply {
            imagen = "qkslWvL"
            nombre = "Brikol"
            descripcion = "Impregnante para ladrillose"
            puntaje = 5
            paisDeOrigen = "Argentina"
            volumen = 4.0
            rendimiento = 13.0
            color = "#A27A65"
            precioBase = 5259.38
        })
    }

    private fun instanciarUsuarios() {
        listaDeUsuarios.add(Usuario().apply {
            nombre = "Clemente"
            apellido = "Lopez"
            edad = 34
            saldo = 1200000.00

            usuarioNombre = "Clemente"
            contrasenia = "1234"
            imagen = "LziS4xI"
        })

        listaDeUsuarios.add(Usuario().apply {
            nombre = "Bonifacio"
            apellido = "Gomez"
            edad = 46
            saldo = 1500000.00

            usuarioNombre = "Bonifacio"
            contrasenia = "1234"
            imagen = "9N6dG0I"
        })


        listaDeUsuarios.add(Usuario().apply {
            nombre = "Dalmacio"
            apellido = "Martinez"
            edad = 44
            saldo = 800000.00

            usuarioNombre = "Dalmacio"
            contrasenia = "1234"
            imagen = "4CdVATz"
        })

        listaDeUsuarios.add(Usuario().apply {
            nombre = "Emeterio"
            apellido = "Garcia"
            edad = 25
            saldo = 2500000.00

            usuarioNombre = "Emeterio"
            contrasenia = "1234"
            imagen = "rUsv2PM"
        })

        listaDeUsuarios.add(Usuario().apply {
            nombre = "Taciana"
            apellido = "Moyano"
            edad = 27
            saldo = 3500000.00

            usuarioNombre = "Taciana"
            contrasenia = "1234"
            imagen = "v7W72v7"
        })

        listaDeUsuarios.add(Usuario().apply {
            nombre = "Ursula"
            apellido = "Campos"
            edad = 19
            saldo = 500000.00

            usuarioNombre = "Ursula"
            contrasenia = "1234"
            imagen = "1gLAag5"
        })

        listaDeUsuarios.add(Usuario().apply {
            nombre = "Valentina"
            apellido = "Soto"
            edad = 23
            saldo = 1500000.00

            usuarioNombre = "Valentina"
            contrasenia = "1234"
            imagen = "8XqxdXM"
        })

        listaDeUsuarios.add(Usuario().apply {
            nombre = "Zeferina"
            apellido = "Chávez"
            edad = 23
            saldo = 2300000.00

            usuarioNombre = "Zeferina"
            contrasenia = "1234"
            imagen = "gMP268W"
        })
    }

    private fun instanciasLotes() {
        var diaCero: LocalDate = LocalDate.now().minusMonths(4L).minusDays(7L)

        for (i in 1..cantidadDeLotes + 5) {
            listaDeLotes.add(Lote().apply {
                id = i.toLong()
                fechaIngreso = diaCero
                cantidadDeUnidades = (250 * abs(cos(i.toDouble()))).toInt() + 1
            })

            if (diaCero.isEqual(LocalDate.now())) {
                diaCero = LocalDate.now().minusMonths(4L)
            }

            diaCero = diaCero.plusDays(1L)
        }
    }

    private fun iniciarDatos() {
        val fechaCompra = LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
        val listaDeReSetUsuarios = mutableListOf( /* reset saldos */
            /*  Bonifacio.saldo = */ 1500000.00,
            /* Clemente.saldo =   */ 1200000.00,
            /* Dalmacio.saldo =   */  800000.00,
            /* Emeterio.saldo =   */ 2500000.00,
            /* Taciana.saldo =    */ 3500000.00,
            /* Ursula.saldo =     */  500000.00,
            /* Valentina.saldo =  */ 1500000.00,
            /* Zeferina.saldo =   */ 2300000.00
        )

        this.instanciarUsuarios()
        this.instanciarProductos()
        this.instanciasLotes()

        /* persistencia de productos */ /* esto me resulta tan hibernate */
        (0..159).forEach { listaDeProductos[it % 16].agregarLote(listaDeLotes[it]) }
        val productoTemp = repoProductos.saveAll(listaDeProductos).sortedBy { it.id }

        /* creación y persistencia de combo */
        repoProductos.save(Combo().apply {
            imagen = "B7yWM0f"
            nombre = "combo"
            descripcion = "pisos y pinturas"
            puntaje = 1
            paisDeOrigen = "Argentina"

            (0..3).forEach {
                val productoSimple = productoTemp[it]
                agregarProductoCombo(productoSimple, productoSimple.lotes.first(), it + 1)
                agregarLote(listaDeLotes[160 + it])
            }
        })

//        for(i in 1..1000){
//            repoProductos.save(Combo().apply {
//                imagen = "B7yWM0f"
//                nombre = "combo "+i.toString()
//                descripcion = "pisos y pinturas"
//                puntaje = 1
//                paisDeOrigen = "Argentina"
//
//                (0..3).forEach {
//                    val productoSimple = productoTemp[it]
//                    agregarProductoCombo(productoSimple, productoSimple.lotes.first(), it + 1)
//                    agregarLote(Lote().apply{
//                        id= (i + cantidadDeLotes + it).toLong()
//                        fechaIngreso=LocalDate.now()
//                        cantidadDeUnidades=2
//                    })
//                }
//            })
//          cantidadDeLotes+=4
//        }



        /* persistencia en repos */ /* esto me resulta tan hibernate */
        (0..7).forEach {
            val item0 = ItemFactura(productoTemp[it], productoTemp[it].lotes.first(), 1)
            val item1 = ItemFactura(productoTemp[it + 1], productoTemp[it + 1].lotes.first(), 1)
            listaDeUsuarios[it].comprar(
                Factura(listOf(item0, item1),listaDeUsuarios[it]).apply { fechaDeCompra = fechaCompra.minusDays(it.toLong()) }
            )
            listaDeUsuarios[it].saldo = listaDeReSetUsuarios[it]
        }
        repoUsuarios.saveAll(listaDeUsuarios)
        val listaFacturas = mutableListOf<Factura>()
        (0..7).forEach{
            listaFacturas.addAll(listaDeUsuarios[it].facturas)
        }
        repoFacturasGrafito.saveAll(listaFacturas)

    }

    override fun afterPropertiesSet() {
        log.info("**********************************************************************************************")
        log.info("Running initialization")
        log.info("**********************************************************************************************")
        this.iniciarDatos()
        log.info("**********************************************************************************************")
        log.info("Finish initialization")
        log.info("**********************************************************************************************")
    }
}