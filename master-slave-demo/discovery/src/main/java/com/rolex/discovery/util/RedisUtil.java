package com.rolex.discovery.util;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.rolex.discovery.load.Perf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.rolex.discovery.util.Constants.DELETE_REPLACE;

@Component
@Slf4j
public class RedisUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static RedisUtil redisUtil;

    @PostConstruct
    public void init() {
        //设置序列化Key的实例化对象
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new GenericFastJsonRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericFastJsonRedisSerializer());
        redisUtil = this;
        redisUtil.redisTemplate = this.redisTemplate;

    }

    //=============================common============================
    public static RedisTemplate getRedisTemplate() {
        return redisUtil.redisTemplate;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    @Perf
    public static boolean expire(String key, long time) {
        long startTime = logStart(key, time);
        try {
            if (time > 0) {
                redisUtil.redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return false;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    @Perf
    public static long getExpire(String key) {
        long startTime = logStart(key);
        try {
            return redisUtil.redisTemplate.getExpire(key, TimeUnit.SECONDS);
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    @Perf
    public static boolean hasKey(String key) {
        long startTime = logStart(key);
        try {
            return redisUtil.redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("redis option error!", e);
            return false;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    @Perf
    public static void del(String... key) {
        long startTime = logStart(key);

        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisUtil.redisTemplate.delete(key[0]);
            } else {
                redisUtil.redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }

        logEnd(startTime);
    }

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        long startTime = logStart(key);
        try {
            return key == null ? null : redisUtil.redisTemplate.opsForValue().get(key);
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean set(String key, Object value) {
        long startTime = logStart(key, value);
        try {
            redisUtil.redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return false;
        } finally {
            logEnd(startTime);
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static boolean set(String key, String value, long time) {
        long startTime = logStart(key, value, time);
        try {
            if (time > 0) {
                redisUtil.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return false;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public static long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }

        long startTime = logStart(key, delta);
        try {
            return redisUtil.redisTemplate.opsForValue().increment(key, delta);
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public static long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }

        long startTime = logStart(key, delta);
        try {
            return redisUtil.redisTemplate.opsForValue().increment(key, -delta);
        } finally {
            logEnd(startTime);
        }
    }

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public static Object hget(String key, String item) {
        long startTime = logStart(key, item);
        try {
            return redisUtil.redisTemplate.opsForHash().get(key, item);
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * hMultiGet
     *
     * @param key  键 不能为null
     * @param hashKeys 项 不能为null
     * @return 值
     */
    public static List<Object> hMultiGet(String key, Collection<Object> hashKeys) {
        long startTime = logStart(key, hashKeys);
        try {
            return redisUtil.redisTemplate.opsForHash().multiGet(key, hashKeys);
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> hmget(String key) {
        long startTime = logStart(key);
        try {
            return redisUtil.redisTemplate.opsForHash().entries(key);
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static List<Object> hValues(String key) {
        long startTime = logStart(key);
        try {
            return redisUtil.redisTemplate.opsForHash().values(key);
        } finally {
            logEnd(startTime);
        }

    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public static boolean hmset(String key, Map<String, Object> map) throws Exception {
        long startTime = logStart(key, map);
        try {
            redisUtil.redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return false;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public static boolean hmset(String key, Map<String, Object> map, long time) {
        long startTime = logStart(key, map, time);
        try {
            redisUtil.redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return false;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public static boolean hset(String key, String item, Object value) {
        long startTime = logStart(key, item, value);
        try {
            redisUtil.redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("redis option error!", e);
            throw e;
            //return false;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public static boolean hset(String key, String item, Object value, long time) {
        long startTime = logStart(key, item, value, time);
        try {
            redisUtil.redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return false;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public static void hdel(String key, Object... item) {
        long startTime = logStart(key, item);
        try {
            redisUtil.redisTemplate.opsForHash().delete(key, item);
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public static boolean hHasKey(String key, String item) {
        long startTime = logStart(key, item);
        try {
            return redisUtil.redisTemplate.opsForHash().hasKey(key, item);
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public static double hincr(String key, String item, double by) {
        long startTime = logStart(key, item, by);
        try {
            return redisUtil.redisTemplate.opsForHash().increment(key, item, by);
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public static double hdecr(String key, String item, double by) {
        long startTime = logStart(key, item, by);
        try {
            return redisUtil.redisTemplate.opsForHash().increment(key, item, -by);
        } finally {
            logEnd(startTime);
        }
    }

    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public static Set<Object> sGet(String key) {
        long startTime = logStart(key);
        try {
            return redisUtil.redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("redis option error!", e);
            return null;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static boolean sHasKey(String key, Object value) {
        long startTime = logStart(key, value);
        try {
            return redisUtil.redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("redis option error!", e);
            return false;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSet(String key, Object... values) {
        long startTime = logStart(key, values);
        try {
            return redisUtil.redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("redis option error!", e);
            return 0;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSetAndTime(String key, long time, Object... values) {
        long startTime = logStart(key, time, values);
        try {
            Long count = redisUtil.redisTemplate.opsForSet().add(key, values);
            if (time > 0) expire(key, time);
            return count;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return 0;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long sGetSetSize(String key) {
        long startTime = logStart(key);
        try {
            return redisUtil.redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("redis option error!", e);
            return 0;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static long setRemove(String key, Object... values) {
        long startTime = logStart(key, values);
        try {
            Long count = redisUtil.redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return 0;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 获取最小值
     *
     * @param key
     * @return
     */
    public static double zsetMinScore(String key) {
        long startTime = logStart(key);
        try {
            Set<Object> objects = redisUtil.redisTemplate.opsForZSet().range(key, 0, 1);
            double score = 0;
            for (Object object : objects) {
                double objScore = redisUtil.redisTemplate.opsForZSet().score(key, object);
                if (score == 0) {
                    score = objScore;
                } else if (objScore < score) {
                    score = objScore;
                }
            }
            return score;
        } finally {
            logEnd(startTime);
        }
    }
    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public static List<Object> lGet(String key, long start, long end) {
        long startTime = logStart(key, start, end);
        try {
            return redisUtil.redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("redis option error!", e);
            return null;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 获取指定区间的list内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public static void lTrim(String key, long start, long end) {
        long startTime = logStart(key, start, end);
        try {
            redisUtil.redisTemplate.opsForList().trim(key, start, end);
        } catch (Exception e) {
            log.error("redis option error!", e);
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long lGetListSize(String key) {
        long startTime = logStart(key);
        try {
            return redisUtil.redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("redis option error!", e);
            return 0;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key 键
     * @return
     */
    public static Object lPop(String key) {
        long startTime = logStart(key);
        try {
            return redisUtil.redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            log.error("redis option error!", e);
            return null;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public static Object lGetIndex(String key, long index) {
        long startTime = logStart(key, index);
        try {
            return redisUtil.redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("redis option error!", e);
            return null;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static boolean lSet(String key, Object value) {
        long startTime = logStart(key, value);
        try {
            redisUtil.redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return false;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean lSet(String key, Object value, long time) {
        long startTime = logStart(key, value, time);
        try {
            redisUtil.redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return false;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static boolean lSetAll(String key, List<Object> value) {
        long startTime = logStart(key, value);
        try {
            redisUtil.redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return false;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean lSet(String key, List<Object> value, long time) {
        long startTime = logStart(key, value, time);
        try {
            redisUtil.redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return false;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public static boolean lUpdateIndex(String key, long index, Object value) {
        long startTime = logStart(key, index, value);
        try {
            redisUtil.redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return false;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public static long lRemove(String key, long count, Object value) {
        long startTime = logStart(key, count, value);
        try {
            Long remove = redisUtil.redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return 0;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param index 要删除的下标
     */
    public static void lRemoveIndex(String key, int index) {
        long startTime = logStart(key, index);
        try {
            //开启事务
            redisUtil.redisTemplate.multi();
            redisUtil.redisTemplate.opsForList().set(key, index, DELETE_REPLACE);
            redisUtil.redisTemplate.opsForList().remove(key, 0, DELETE_REPLACE);
            redisUtil.redisTemplate.exec();
        } catch (Exception e) {
            log.error("redis option error!", e);
        } finally {
            logEnd(startTime);
        }
    }

    //============================zset=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public static Set<Object> zGet(String key, int start, int end) {
        long startTime = logStart(key, start, end);
        try {
            return redisUtil.redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            log.error("redis option error!", e);
            return null;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public static Set<Object> zGetRevRange(String key, int start, int end) {
        long startTime = logStart(key, start, end);
        try {
            return redisUtil.redisTemplate.opsForZSet().reverseRange(key, start, end);
        } catch (Exception e) {
            log.error("redis option error!", e);
            return null;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 根据分数获取所有值
     *
     * @param key 键
     * @return
     */
    public static Set<Object> zGetByScore(String key, int min, int max) {
        long startTime = logStart(key, min, max);
        try {
            return redisUtil.redisTemplate.opsForZSet().rangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis option error!", e);
            return null;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 根据分数倒序获取所有值
     *
     * @param key 键
     * @return
     */
    public static Set<Object> zGetRevRangeByScore(String key, int min, int max) {
        long startTime = logStart(key, min, max);
        try {
            return redisUtil.redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis option error!", e);
            return null;
        } finally {
            logEnd(startTime);
        }
    }


    /**
     * 获取有序集合的成员数
     *
     * @param key 键
     * @return
     */
    public static Long zCard(String key) {
        long startTime = logStart(key);
        try {
            return redisUtil.redisTemplate.opsForZSet().zCard(key);
        } catch (Exception e) {
            log.error("redis option error!", e);
            return 0L;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key   键
     * @param value 值 可以是多个
     * @return 成功个数
     */
    public static Boolean zSet(String key, Object value, double score) {
        long startTime = logStart(key, value, score);
        try {
            redisUtil.redisTemplate.opsForZSet().add(key, value, score);
            return true;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return false;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long zGetSize(String key) {
        long startTime = logStart(key);
        try {
            return redisUtil.redisTemplate.opsForZSet().size(key);
        } catch (Exception e) {
            log.error("redis option error!", e);
            return 0;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 批量移除
     *
     * @param key   键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static long zRemoveBatch(String key, Collection<Object> values) {
        long startTime = logStart(key, values);
        try {
            Object[] value = values.toArray();
            Long count = redisUtil.redisTemplate.opsForZSet().remove(key, value);
            return count;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return 0;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 移除值为value的
     *
     * @param key   键
     * @param item 值 可以是多个
     * @return 移除的个数
     */
    public static long zRemove(String key, Object item) {
        long startTime = logStart(key, item);
        try {
            Long count = redisUtil.redisTemplate.opsForZSet().remove(key, item);
            return count;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return 0;
        } finally {
            logEnd(startTime);
        }
    }

    /**
     * 移除值为value的
     *
     * @param key   键
     * @param item 值 可以是多个
     * @return 移除的个数
     */
    public static long zRank(String key, Object item) {
        long startTime = logStart(key, item);
        try {
            log.debug("key: {}, item: {}", key, item);
            Long count = redisUtil.redisTemplate.opsForZSet().rank(key, item);
            log.debug("count: {}", count);
            return count;
        } catch (Exception e) {
            log.error("redis option error!", e);
            return -1;
        } finally {
            logEnd(startTime);
        }
    }

    private static long logStart(Object... args) {
        if (log.isDebugEnabled()) {
            log.debug("ThreadName: {}, Method invoke stack: [{}], Args: [{}]", getCurrentThreadName(), getMethodStacks(3), getArgs(args));
        }

        return System.currentTimeMillis();
    }

    private static void logEnd(long startTime) {
        if (log.isDebugEnabled()) {
            long endTime = System.currentTimeMillis();
            log.debug("ThreadName: {}, Method invoke stack: [{}], Used time: {}ms", getCurrentThreadName(),
                    getMethodStacks(3), endTime - startTime);
        }
    }

    private static String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }

    private static String getMethodStacks(int depth) {
        StackTraceElement elements[] = Thread.currentThread().getStackTrace();

        int skip = 3;
        int newDepth = Math.min(depth, elements.length - skip);
        return Arrays.stream(elements).skip(skip).limit(newDepth).map(e -> e.getClass() + "->" + e.getMethodName())
                .collect(Collectors.joining(", "));
    }

    private static String getArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i > 0) {
                sb.append(",");
            }

            // TODO 需考虑特殊类型，如数组
            sb.append(args[i].toString());
        }

        return sb.toString();
    }
    public static Set sMembers(String key){
        return redisUtil.redisTemplate.opsForSet().members(key);
    }
}