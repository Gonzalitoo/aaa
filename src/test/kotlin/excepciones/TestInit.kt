package excepciones

import aplicacion.DifficultApp
import aplicacion.DifficultBootstrap
import aplicacion.controladores.UsuarioController
import aplicacion.repositorios.mongo.RepoProductos
import aplicacion.repositorios.mysql.RepoUsuarios
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@ContextConfiguration(classes = [DifficultApp::class])
@WebMvcTest(UsuarioController::class)
open class TestInit {
    @Autowired lateinit var mockMvc: MockMvc

    @MockBean lateinit var repoUsuarios: RepoUsuarios
    @MockBean lateinit var repoProductos: RepoProductos
    @MockBean private val difficultBootstrap: DifficultBootstrap? = null

    protected fun get(pathParametros: String): MockHttpServletRequestBuilder {
        return MockMvcRequestBuilders
            .get(pathParametros)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
    }

    protected fun put(pathParametros: String, body: String): MockHttpServletRequestBuilder {
        return MockMvcRequestBuilders
            .put(pathParametros)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(body)
    }

    protected fun post(pathParametros: String, body: String): MockHttpServletRequestBuilder {
        return MockMvcRequestBuilders
            .post(pathParametros)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(body)
    }
}
