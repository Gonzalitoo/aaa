package aplicacion

import aplicacion.repositorios.neo4j.RepoUsuariosGrafito
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@SpringBootApplication
@ComponentScan(basePackages = ["aplicacion"])
@EnableNeo4jRepositories(basePackages = ["aplicacion/repositorios/neo4j"])
@EnableMongoRepositories(basePackages = ["aplicacion/repositorios/mongo"])
@EnableJpaRepositories(basePackages = ["aplicacion/repositorios/mysql"])
@EnableRedisRepositories(basePackages = ["aplicacion/repositorios/redis"])
open class DifficultApp

fun main(args: Array<String>) {
    runApplication<DifficultApp>(*args)
}

object Keys {
    private var keyItemCarrito: Long = 0L

    fun plusItemCarrito(): Long {
        keyItemCarrito++
        return keyItemCarrito
    }
}

