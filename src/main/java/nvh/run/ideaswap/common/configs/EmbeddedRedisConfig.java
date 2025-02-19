//package nvh.run.ideaswap.common.configs;
//
//import it.ozimov.embedded.redis.EmbeddedRedis;
//import it.ozimov.embedded.redis.configuration.RedisServerConfiguration;
//import jakarta.annotation.PreDestroy;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.io.IOException;
//import java.net.UnknownHostException;
//
//@Configuration
//@Slf4j
//public class EmbeddedRedisConfig {
//
//    private EmbeddedRedis embeddedRedis;
//
//    @Bean
//    public EmbeddedRedis embeddedRedis() throws IOException {
//        embeddedRedis = new EmbeddedRedis(RedisServerConfiguration.builder()
//                .port(6379)
//                .setting("maxmemory 128M")
//                .build());
//        embeddedRedis.start();
//        log.info("Embedded Redis started on port 6379");
//        return embeddedRedis;
//    }
//
//    @PreDestroy
//    public void stopRedis() {
//        if (embeddedRedis != null) {
//            embeddedRedis.stop();
//            log.info("Embedded Redis stopped");
//        }
//    }
//
//    @Bean
//    public JedisConnectionFactory redisConnectionFactory() {
//        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6379);
//        return new JedisConnectionFactory(config, JedisClientConfiguration.defaultConfiguration());
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        return template;
//    }
//}
