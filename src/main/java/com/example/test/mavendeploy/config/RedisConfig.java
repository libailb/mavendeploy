package com.example.test.mavendeploy.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @author qiyu
 * @date 2018-12-14 14:44
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdl;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdl;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    /**
     * 多实例host&port在此处配置
     */
    @Value("${spring.redis.host.config}")
    private String hostOfConfig;
    @Value("${spring.redis.port.config}")
    private int portOfConfig;
    @Value("${spring.redis.host.sform}")
    private String hostOfSform;
    @Value("${spring.redis.port.sform}")
    private int portOfSform;

    @Value("${spring.redis.password}")
    private String passWord;

    /**
     * 需要借助@Primary来指定默认的连接工厂，否则会报错:
     * 'RedisAutoConfiguration required a single bean, but 2 were found'
     */
    @Bean
    @Primary
    public RedisConnectionFactory redisConnectionFactoryOfConfig() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostOfConfig);
        redisStandaloneConfiguration.setPort(portOfConfig);
        redisStandaloneConfiguration.setDatabase(database);
        if (!passWord.isEmpty()) {
            redisStandaloneConfiguration.setPassword(RedisPassword.of(passWord));
        }
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean(name = "redisTemplateOfConfig")
    public RedisTemplate<String, Object> redisTemplateOfConfig() {
        RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();
        redisTemplateObject.setConnectionFactory(redisConnectionFactoryOfConfig());
        setSerializer(redisTemplateObject);
        redisTemplateObject.afterPropertiesSet();
        return redisTemplateObject;
    }

    private void setSerializer(RedisTemplate<String, Object> template) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
                Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 此处设置om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)后，
        // 处理json格式的value会有问题。
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setKeySerializer(template.getStringSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 如果gms中只有String作为key、value的场景
        // 可以使用这个来更改序列化方式, 即StringRedisSerializer
        /*RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer );
        template.setValueSerializer(stringSerializer );
        template.setHashKeySerializer(stringSerializer );
        template.setHashValueSerializer(stringSerializer );*/
    }
}