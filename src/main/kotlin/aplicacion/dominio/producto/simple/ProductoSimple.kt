package aplicacion.dominio.producto.simple

import aplicacion.dominio.producto.Producto
import javax.persistence.Entity
import javax.persistence.Inheritance

//@Entity @Inheritance
abstract class ProductoSimple : Producto() {
    override fun precio(): Double {
        return super.precio() * recargo()
    }

    abstract fun recargo(): Double
}