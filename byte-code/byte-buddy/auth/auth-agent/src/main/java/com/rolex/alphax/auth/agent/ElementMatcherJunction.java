/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.auth.agent;

import net.bytebuddy.description.NamedElement;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * @author rolex
 * @since 2020
 */
public abstract class ElementMatcherJunction implements ElementMatcher.Junction<NamedElement> {
    @Override
    public <U extends NamedElement> Junction<U> and(ElementMatcher<? super U> elementMatcher) {
        return new Conjunction<>(this, elementMatcher);
    }

    @Override
    public <U extends NamedElement> Junction<U> or(ElementMatcher<? super U> elementMatcher) {
        return new Disjunction<>(this, elementMatcher);
    }
}
