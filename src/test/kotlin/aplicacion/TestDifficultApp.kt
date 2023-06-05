//package aplicacion
//
//import org.hamcrest.Matchers.containsString
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
//
//import org.junit.jupiter.api.Test
//
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.test.web.servlet.MockMvc
//
//@SpringBootTest(classes = [DifficultApp::class])
//@AutoConfigureMockMvc
//open class TestDifficultApp {
//    @Autowired private lateinit var mockMvc: MockMvc
//
//    @Test @Throws(Exception::class)
//    open fun shouldReturnDefaultMessage() {
//        mockMvc.perform(
//            get("/hola_mundo"))
//            .andDo(print())
//            .andExpect(status().isOk)
//            .andExpect(content().string(containsString("Hola Mundo"))
//        )
//    }
//}

