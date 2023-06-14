package test;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class Lock2 {

    public static void main(String[] args) throws InterruptedException {
        RedissonClient redissonClient = Redisson.create();
        RLock rLock = redissonClient.getLock("test");

        if (rLock.tryLock(200, TimeUnit.MILLISECONDS)) {
            try {
                System.out.println("=====lock2 locked");
                Thread.sleep(1000000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                rLock.unlock();
                System.out.println("=======lock2 unlocked");
            }
        }else {
            System.out.println("=======lock2没有获取到锁");
        }
        redissonClient.shutdown();
    }
}
