package aplicacion.dominio.usuario

import aplicacion.excepciones.BusinessException
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import javax.persistence.*

import javax.persistence.Id as idJPA
import javax.persistence.GeneratedValue as generatedValueJPA

@Entity @Node("Usuario")
class Usuario {
    @Id
    @idJPA @generatedValueJPA(strategy= GenerationType.IDENTITY)
    @JsonIgnore var id: Long? = null
    @JsonIgnore var usuarioNombre: String = ""
    @JsonIgnore var contrasenia: String = ""

    var nombre: String? = null
    var apellido: String? = null
    var saldo: Double? = null
    var imagen: String = ""
    var edad: Int = 0

    @JsonProperty("comprasRealizadas")
    @OneToMany(cascade = [CascadeType.MERGE,CascadeType.PERSIST]) @JoinColumn(name="usuario_id")
    @org.springframework.data.annotation.Transient
    var facturas: MutableList<Factura> = mutableListOf()

    fun comprar(factura: Factura) {
        factura.user = this
        this.checkSaldo(factura)
        saldo = calcularSaldo(factura)
        facturas.add(factura)
    }

    private fun calcularSaldo(factura: Factura): Double {
        return saldo?.minus(factura.importeTotal) ?: 0.0
    }

    private fun checkSaldo(factura: Factura) {
        if (calcularSaldo(factura) < 0.0) throw BusinessException(
            "Saldo insuficiente para esta compra"
        )
    }
}