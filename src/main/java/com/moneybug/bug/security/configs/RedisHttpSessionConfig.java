package com.moneybug.bug.security.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800) // 세션 타임 아웃
public class RedisHttpSessionConfig {
}
