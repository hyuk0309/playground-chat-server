package elvis.chat.playgroundchatserver.config

import elvis.chat.playgroundchatserver.pubsub.RedisSubscriber
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    /**
     * set global ChannelTopic
     */
    @Bean
    fun channelTopic() = ChannelTopic("chatroom")

    /**
     * set global redis message listener
     */
    @Bean
    fun listenerAdapter(subscriber: RedisSubscriber) = MessageListenerAdapter(subscriber, "sendMessage")

    /**
     * config redis listener that handle pub/sub message
     */
    @Bean
    fun redisMessageListener(
        connectionFactory: RedisConnectionFactory,
        listenerAdapter: MessageListenerAdapter,
        channelTopic: ChannelTopic,
    ): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(connectionFactory)
        container.addMessageListener(listenerAdapter, channelTopic)
        return container
    }

    /**
     * config redisTemplate in an application
     */
    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = connectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = Jackson2JsonRedisSerializer(String::class.java)
        return redisTemplate
    }
}
