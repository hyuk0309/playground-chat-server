package elvis.chat.playgroundchatserver.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthCheckController(
    private val webServerAppCtxt: ServletWebServerApplicationContext,
) {

    @GetMapping("/info")
    fun serverInfo(request: HttpServletRequest) = mapOf(
        "IPAdress" to request.getHeader("X-FORWARDED-FOR"),
        "Port" to webServerAppCtxt.webServer.port.toString()
    )
}
