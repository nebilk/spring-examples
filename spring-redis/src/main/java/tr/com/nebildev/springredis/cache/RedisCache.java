package tr.com.nebildev.springredis.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisCache {

    private final RedisTemplate<String, Object> redisTemplate;

    public void putCache(String key, Object obj, int timeout, TimeUnit timeUnit){

        final Optional<Object> cache = getFromCache(key);
        if (!cache.isPresent()) {
            redisTemplate.opsForValue().set(key, obj);
            redisTemplate.expire(key, timeout, timeUnit);
        }
    }

    public Optional<Object> getFromCache(String key){

        final Object cachedValue = redisTemplate.opsForValue().get(key);

        if(Objects.isNull(cachedValue)){
            return Optional.empty();
        }

        return Optional.of(cachedValue);
    }

    public void deleteCache(String key){
        redisTemplate.delete(key);
    }
}