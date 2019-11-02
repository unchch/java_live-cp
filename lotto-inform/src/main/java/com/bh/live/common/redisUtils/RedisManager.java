package com.bh.live.common.redisUtils;

import com.alibaba.fastjson.JSON;
import com.bh.live.common.utils.CommonUtil;
import com.bh.live.common.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * redis工具类
 *
 * @author yq.
 * @version 1.0.0 2019-06-11
 * @since 1.0.0 2019-06-11
 **/

@Configuration
@DependsOn("redisTemplate")
public class RedisManager {

    private static Logger log = LoggerFactory.getLogger(RedisManager.class);

    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    public boolean set(final String key, final String value) {
        boolean result = this.redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    public boolean set(final String key, final Object value) {
        boolean result = this.redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer serializer = RedisManager.this.redisTemplate.getStringSerializer();
                try {
                    connection.set(serializer.serialize(key), serializer.serialize(JacksonUtil.obj2json(value)));
                } catch (Exception var4) {
                    log.error("json异常：", var4);
                }
                return true;
            }
        });
        return result;
    }

    public boolean setByFastJson(final String key, final Object value) {
        boolean result = this.redisTemplate.execute((RedisConnection connection) -> {
            RedisSerializer serializer = RedisManager.this.redisTemplate.getStringSerializer();
            try {
                connection.set(serializer.serialize(key), serializer.serialize(JSON.toJSONString(value)));
            } catch (Exception var4) {
                log.error("Redis 异常", var4);
                throw new RuntimeException(var4.getMessage());
            }
            return true;
        });
        return result;
    }

    public boolean setByFastJson(final String key, final Object value, final long timeout, TimeUnit timeUnit) {
        boolean result = this.redisTemplate.execute((RedisConnection connection) -> {
            RedisSerializer serializer = RedisManager.this.redisTemplate.getStringSerializer();
            try {
                connection.set(serializer.serialize(key), serializer.serialize(JSON.toJSONString(value)));
                RedisManager.this.redisTemplate.expire(key, timeout, timeUnit);
            } catch (Exception var4) {
                log.error("Redis 异常", var4);
                throw new RuntimeException(var4.getMessage());
            }
            return true;
        });
        return result;
    }

    public <T> T getByFastJson(final String key, Class<T> clazz) {
        String str = this.redisTemplate.execute((RedisConnection connection) -> {
            RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
            byte[] value = connection.get(serializer.serialize(key));
            return serializer.deserialize(value);
        });
        if (CommonUtil.isEmpty(str)) {
            return null;
        } else {
            try {
                return JSON.parseObject(str, clazz);
            } catch (Exception var5) {
                log.error("json异常：", var5);
                throw new RuntimeException("Redis 反序列化异常", var5);
            }
        }
    }

    public String get(final String key) {
        String result = this.redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

    public <T> T get(final String key, Class<T> clazz) {
        String str = (String) this.redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return (String) serializer.deserialize(value);
            }
        });
        if (CommonUtil.isEmpty(str)) {
            return null;
        } else {
            try {
                return JacksonUtil.json2obj(str, clazz);
            } catch (Exception var5) {
                log.error("json异常：", var5);
                return null;
            }
        }
    }

    /**
     * 删除方法
     *
     * @param key
     */
    public void del(final String key) {
        this.redisTemplate.execute((RedisConnection conn) -> {
            RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
            return conn.del(new byte[][]{serializer.serialize(key)});
        });
    }

    public Integer ttl(String key) {
        if (this.redisTemplate.hasKey(key)) {
            this.redisTemplate.getExpire(key, TimeUnit.SECONDS);
        }

        return null;
    }

    public void delAll(final String key) {
        this.redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection conn) throws DataAccessException {
                RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
                Set<byte[]> keySet = conn.keys(serializer.serialize(key + "*"));
                if (keySet != null) {
                    Iterator var4 = keySet.iterator();

                    while (var4.hasNext()) {
                        byte[] bytes = (byte[]) var4.next();
                        conn.del(new byte[][]{bytes});
                    }
                }
                return (long) keySet.size();
            }
        });
    }

    public boolean expire(final String key, long expire) {
        return this.redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
    }

    public <T> boolean setList(String key, List<T> list) {
        try {
            String value = JacksonUtil.obj2json(list);
            return this.set(key, value);
        } catch (Exception var5) {
            log.error("json异常：", var5);
            return false;
        }
    }

    public <T> List<T> getList(String key, Class<T> clz) {
        String json = this.get(key);
        if (json != null) {
            try {
                List<T> list = JacksonUtil.json2list(json, clz);
                return list;
            } catch (Exception var6) {
                log.error("json异常：", var6);
            }
        }

        return null;
    }

    public long lpush(final String key, Object obj) {
        try {
            final String value = JacksonUtil.obj2json(obj);
            long result = (Long) this.redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
                    long count = connection.lPush(serializer.serialize(key), new byte[][]{serializer.serialize(value)});
                    return count;
                }
            });
            return result;
        } catch (Exception var6) {
            log.error("json异常：", var6);
            return 0L;
        }
    }

    public long rpush(final String key, Object obj) {
        try {
            final String value = JacksonUtil.obj2json(obj);
            long result = (Long) this.redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
                    long count = connection.rPush(serializer.serialize(key), new byte[][]{serializer.serialize(value)});
                    return count;
                }
            });
            return result;
        } catch (Exception var6) {
            log.error("json异常：", var6);
            return 0L;
        }
    }

    public <T> T hget(String key, Class<T> clz) {
        return this.redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
                Map<byte[], byte[]> data = connection.hGetAll(serializer.serialize(key));
                Map<String, Object> result = new HashMap();
                Iterator var5 = data.entrySet().iterator();

                while (var5.hasNext()) {
                    Map.Entry<byte[], byte[]> entry = (Map.Entry) var5.next();
                    result.put(serializer.deserialize((byte[]) entry.getKey()), serializer.deserialize((byte[]) entry.getValue()));
                }

                try {
                    return JacksonUtil.json2obj(JacksonUtil.obj2json(result), clz);
                } catch (Exception var7) {
                    log.error("json异常：", var7);
                    return null;
                }
            }
        });
    }


    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object,Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }



    public <T> T hget(String key, String field, Class<T> clz) {
        return this.redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
                String value = (String) serializer.deserialize(connection.hGet(serializer.serialize(key), serializer.serialize(field)));

                try {
                    return CommonUtil.isEmpty(value) ? null : JacksonUtil.json2obj(value, clz);
                } catch (Exception var5) {
                    log.error("json异常：", var5);
                    return null;
                }
            }
        });
    }

    public void hset(String key, String field, Object obj) {
        this.redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer serializer = RedisManager.this.redisTemplate.getStringSerializer();

                try {
                    return connection.hSet(serializer.serialize(key), serializer.serialize(field), serializer.serialize(JacksonUtil.obj2json(obj)));
                } catch (Exception var4) {
                    log.error("json异常：", var4);
                    return null;
                }
            }
        });
    }

    public void hdel(String key, String... fields) {
        this.redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
                if (fields == null) {
                    return 0L;
                } else {
                    String[] var3 = fields;
                    int var4 = var3.length;

                    for (int var5 = 0; var5 < var4; ++var5) {
                        String field = var3[var5];
                        connection.hDel(serializer.serialize(key), new byte[][]{serializer.serialize(field)});
                    }

                    return 0L;
                }
            }
        });
    }

    public String lpop(final String key) {
        String result = (String) this.redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
                byte[] res = connection.lPop(serializer.serialize(key));
                return (String) serializer.deserialize(res);
            }
        });
        return result;
    }

    public void expire(String key, int timeout) {
        this.redisTemplate.expire(key, (long) timeout, TimeUnit.SECONDS);
    }

    public Long incr(String key) {
        Long result = (Long) this.redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection conn) throws DataAccessException {
                return conn.incr(key.getBytes());
            }
        });
        return result;
    }

    public boolean has(String key) {
        return this.redisTemplate.hasKey(key);
    }

    public boolean setBitMap(final String key, final long offset, boolean toggle) {
        boolean result = (Boolean) this.redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer serializer = RedisManager.this.redisTemplate.getStringSerializer();

                try {
                    connection.setBit(serializer.serialize(key), offset, toggle);
                } catch (Exception var4) {
                    log.error("位开关设置异常", var4);
                    throw new RuntimeException(var4.getMessage());
                }

                return true;
            }
        });
        return result;
    }

    public long getBitMapCount(final String key) {
        long result = (Long) this.redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
                Long count = 0L;

                try {
                    count = connection.bitCount(serializer.serialize(key));
                    return count;
                } catch (Exception var5) {
                    log.error("位开关统计异常", var5);
                    throw new RuntimeException(var5.getMessage());
                }
            }
        });
        return result;
    }

    public boolean getBitMapToggleState(final String key, final long offset) {
        boolean result = (Boolean) this.redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
                Boolean var3 = false;

                try {
                    return connection.getBit(serializer.serialize(key), offset);
                } catch (Exception var5) {
                    log.error("获取位开关异常", var5);
                    throw new RuntimeException(var5.getMessage());
                }
            }
        });
        return result;
    }

    public Set<String> getKeys(final String keyPrefxi) {
        Set<String> result = (Set) this.redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
                Set<String> result = new HashSet();
                Set middleSet = null;

                try {
                    middleSet = connection.keys(serializer.serialize(keyPrefxi));
                } catch (Exception var7) {
                    log.error("批量获取键值列表异常", var7);
                    throw new RuntimeException(var7.getMessage());
                }

                if (middleSet != null && middleSet.size() > 0) {
                    Iterator var5 = middleSet.iterator();

                    while (var5.hasNext()) {
                        byte[] bytes = (byte[]) var5.next();
                        result.add(serializer.deserialize(bytes));
                    }
                }

                return result;
            }
        });
        return result;
    }

    public <T> List<T> getListByKeys(final Set<String> keys, Class<T> clazz) {
        List result = this.redisTemplate.executePipelined(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
                Iterator var3 = keys.iterator();

                while (var3.hasNext()) {
                    String key = (String) var3.next();
                    connection.get(serializer.serialize(key));
                }

                return null;
            }
        });
        return result;
    }

    public Long zSetAdd(final String key, Set<Tuple> groupTable, final long timeout, TimeUnit timeUnit) {
        Long result = (Long) this.redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
                Long result = 0L;

                try {
                    result = connection.zAdd(serializer.serialize(key), groupTable);
                    if (timeUnit != null && timeout > 0L) {
                        RedisManager.this.redisTemplate.expire(key, timeout, timeUnit);
                    }

                    return result;
                } catch (Exception var5) {
                    log.error("ZSet 批量插入错误", var5);
                    throw new RuntimeException(var5.getMessage());
                }
            }
        });
        return result;
    }

    public Long zSetAdd(final String key, Set<Tuple> groupTable) {
        return this.zSetAdd(key, groupTable, 0L, (TimeUnit) null);
    }

    public List<String> zSetRanking(final String key, Double min, Double max) {
        List<String> result = (List) this.redisTemplate.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisManager.this.redisTemplate.getStringSerializer();
                LinkedList result = new LinkedList();

                try {
                    Set<byte[]> values = null;
                    if (!CommonUtil.isEmpty(min) && !CommonUtil.isEmpty(max)) {
                        values = connection.zRevRangeByScore(serializer.serialize(key), min, max);
                    } else {
                        values = connection.zRevRangeByScore(serializer.serialize(key), RedisZSetCommands.Range.unbounded());
                    }

                    if (CommonUtil.isEmpty(values)) {
                        return null;
                    } else {
                        Iterator var5 = values.iterator();

                        while (var5.hasNext()) {
                            byte[] bytes = (byte[]) var5.next();
                            result.add(serializer.deserialize(bytes));
                        }

                        return result;
                    }
                } catch (Exception var7) {
                    log.error("ZSet 获取排名异常", var7);
                    throw new RuntimeException(var7.getMessage());
                }
            }
        });
        return result;
    }

    public List<String> zSetRanking(final String key) {
        return this.zSetRanking(key, (Double) null, (Double) null);
    }
}

