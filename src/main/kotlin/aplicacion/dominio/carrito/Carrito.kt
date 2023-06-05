package aplicacion.dominio.carrito


import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash("Carrito100%Real")
class Carrito {
    @Id @Indexed
    var id : Long? = 0
    var items : MutableList<ItemCarrito> = mutableListOf()


    constructor(uid:Long){
        this.id = uid
    }
    constructor(){

    }
}