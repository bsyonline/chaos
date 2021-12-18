/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.auth.agent;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.w3c.dom.Element;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class ProtectiveShieldMatcher<T> extends ElementMatcher.Junction.AbstractBase<T> {

    private final ElementMatcher<? super T> matcher;

    public ProtectiveShieldMatcher(ElementMatcher<? super T> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(T t) {
        try {
            boolean matches = this.matcher.matches(t);
            if (matches) {
                log.info("{}", t);
            }
            return matches;
        } catch (Exception e) {
            return false;
        }
    }
}
