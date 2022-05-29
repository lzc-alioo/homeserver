package com.alioo.monitor.machine.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class CacheConfig {

    @Bean
    public Cache<String, String> tokenCache() {
        Cache<String, String> cache = Caffeine.newBuilder()
                //设置cache的初始大小为10，要合理设置该值
                .initialCapacity(100)
                //设置cache的容量上限
                .maximumSize(10_000)
                //缓存项在创建后，在给定时间内没有被读/写访问，则清除
                .expireAfterWrite(60, TimeUnit.MINUTES)
                //构建cache实例
                .build();


        return cache;
    }


}
