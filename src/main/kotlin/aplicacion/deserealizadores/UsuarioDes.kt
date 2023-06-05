package aplicacion.deserealizadores

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

class UsuarioCreacionDes {
    @NotEmpty var usuarioNombre: String = ""
    @NotEmpty var contrasenia: String = ""
    @NotEmpty var imagen: String = ""
    @NotEmpty var nombre: String = ""
    @NotEmpty var apellido: String = ""
    @Positive var edad: Int = 0
    @Positive var saldo: Double = 0.0
}

class UsuarioActualizacionDes(
    var nombre: String = "",
    var apellido: String = "",
    var saldo: Double = 0.0
)

class UsuarioLoginDes{
    var usuarioNombre: String = ""
    var contrasenia: String = ""
}