package org.mlodzirazem.mrpanel.server.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

object CacheNames {
    const val USER_AUTH_PROFILE = "user.auth.profile"
}

@Configuration
class CacheConfiguration {
    @Bean
    fun cacheManager(): CacheManager = CaffeineCacheManager().apply {
        registerCustomCache(
            CacheNames.USER_AUTH_PROFILE,
            Caffeine.newBuilder()
                .maximumSize(1024)
                .expireAfterAccess(Duration.ofMinutes(30))
                .build()
        )
    }
}