package aplicacion.repositorios.neo4j

import aplicacion.dominio.producto.Producto
import aplicacion.dominio.usuario.Factura
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query


interface RepoFacturasGrafito : Neo4jRepository<Factura, Long> {

    @Query("match (p:Producto{id: \$idProducto})--(a:Articulo)--(f:Factura)--(a2:Articulo)-->(productos:Producto)" +
            "where exists {" +
            "match (p:Producto{id: \$idProducto})--(a:Articulo)--(f:Factura)--(u:Usuario) " +
            "where not u.id = \$idUsuario" +
            "} " +
            "return productos.id"
    )
    fun recomendacionProductosByProductoId(idProducto:String, idUsuario:Long?):MutableIterable<String>


    @Query("match (p:Producto{id: \$idProducto})--(a:Articulo)--(f:Factura)--(a2:Articulo)-->(productos:Producto)" +
            "return productos.id"
    )
    fun recomendacionProductosByProductoIdSinUsuario(idProducto:String):MutableIterable<String>
}

