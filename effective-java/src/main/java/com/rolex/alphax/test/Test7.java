package com.rolex.alphax.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.concurrent.atomic.AtomicInteger;

import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.replace;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@PrepareForTest
@RunWith(PowerMockRunner.class)
public class Test7 {
    @Before
    public void before() {
        AtomicInteger value = new AtomicInteger(1);
        replace(method(Integer.class, "intValue")).with(
                (proxy, method, args) -> value.getAndIncrement()
        );
    }

    @Test
    public void test() {
        Integer a = 1;
        if (a == 1 && a == 2 && a == 3) {
            System.out.println("success");
        }
    }
}