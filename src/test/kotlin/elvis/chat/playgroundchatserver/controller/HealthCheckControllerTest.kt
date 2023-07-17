package elvis.chat.playgroundchatserver.controller

import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
internal class HealthCheckControllerTest(
    private val mockMvc: MockMvc,
) : FunSpec({
    test("get /health/info should return server info") {
        mockMvc.get("/health/info").andExpect {
            status { isOk() }
        }.andReturn()
    }
})
