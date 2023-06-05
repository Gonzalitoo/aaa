package aplicacion.repositorios.neo4j

import aplicacion.dominio.usuario.Factura
import aplicacion.dominio.usuario.Usuario
import org.springframework.data.neo4j.repository.Neo4jRepository

interface RepoUsuariosGrafito : Neo4jRepository<Usuario, Long> {
}

