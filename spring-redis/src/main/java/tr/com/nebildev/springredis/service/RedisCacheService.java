package tr.com.nebildev.springredis.service;

import org.springframework.stereotype.Service;
import tr.com.nebildev.springredis.cache.RedisCache;
import tr.com.nebildev.springredis.entity.City;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheService {
    private final RedisCache redisCache;

    public RedisCacheService(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    public void putCityToCache(String code, City city){
        redisCache.putCache(code, city, 24, TimeUnit.HOURS);
    }

    public Optional<Object> getCityFromCache(String code){
        return redisCache.getFromCache(code);
    }

    public void deleteCityFromCache(String code){
        redisCache.deleteCache(code);
    }
}