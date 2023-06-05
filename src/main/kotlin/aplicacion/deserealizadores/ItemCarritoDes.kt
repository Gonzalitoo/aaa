package aplicacion.deserealizadores

import javax.validation.constraints.Positive

class ItemCarritoDes {
    var productoId: String = ""
    @Positive var loteId: Long = 0
    @Positive var cantidad: Int = 0
}