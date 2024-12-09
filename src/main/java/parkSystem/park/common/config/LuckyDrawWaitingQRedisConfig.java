package parkSystem.park.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class LuckyDrawWaitingQRedisConfig extends RedisConfig{

    @Bean
    public RedisConnectionFactory luckyDrawRedisConnectionFactory() {
        return createLettuceConnectionFactory(1);  // Redis DB 선택(럭키 드로우 대기열 Redis)
    }

    @Bean
    @Qualifier("luckyDrawRedisTemplate")
    public RedisTemplate<String, String> luckyDrawRedisTemplate() {

        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(luckyDrawRedisConnectionFactory());
        return redisTemplate;
    }
}
