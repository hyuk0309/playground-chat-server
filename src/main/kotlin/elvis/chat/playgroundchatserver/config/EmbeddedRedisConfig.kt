package elvis.chat.playgroundchatserver.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer

/**
 * when local environment, start embedded redis.
 */
@Profile("local")
@Configuration
class EmbeddedRedisConfig {

    @Value("\${spring.data.redis.port}")
    private var redisPort: Int = 0

    private var redisServer: RedisServer? = null

    @PostConstruct
    fun redisServer() {
        redisServer = RedisServer(redisPort)
        redisServer!!.start()
    }

    @PreDestroy
    fun stopRedis() {
        redisServer?.stop()
    }
}