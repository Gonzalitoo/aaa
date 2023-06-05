package aplicacion.repositorios.mysql

import aplicacion.dominio.usuario.Factura
import aplicacion.dominio.usuario.Usuario
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RepoUsuarios : JpaRepository<Usuario, Long> {
    @EntityGraph(attributePaths = ["facturas"])
    override fun <S : Usuario?> saveAll(entities: MutableIterable<S>): MutableList<S>

    @Query("""
        SELECT u.id FROM Usuario u
        WHERE u.usuarioNombre = :usuarioNombre AND u.contrasenia = :contrasenia 
    """)
    fun findByUsuarioNombreAndContrasenia(
        @Param("usuarioNombre") usuarioNombre: String,
        @Param("contrasenia") contrasenia: String
    ): Optional<Long>


    @Query("""
       SELECT usuario from Usuario usuario
       where usuario.id = :uid
    """)
    override fun findById(@Param("uid") id:Long): Optional<Usuario>

    @Query("""
       SELECT factura from Usuario usuario
       join usuario.facturas as factura
       where usuario.id = :uid
       order by factura.fechaDeCompra desc
    """)
    fun findFacturasByUserId(@Param("uid") id:Long, page: Pageable): List<Factura>

    @Query("""
       SELECT factura from Usuario usuario
       join usuario.facturas as factura
       where usuario.id = :uid
       order by factura.fechaDeCompra desc
    """)
    fun findAllFacturasByUserId(@Param("uid") id:Long): List<Factura>

    @Query("""
        SELECT usuario from Usuario usuario
        join fetch usuario.facturas
        where usuario.id = :uid
    """)
    fun findUsuarioAndFacturaByUsuarioId(@Param("uid") id:Long): Optional<Usuario>
}
