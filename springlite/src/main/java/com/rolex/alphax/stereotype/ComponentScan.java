/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.alphax.stereotype;

import java.lang.annotation.*;

/**
 * @author rolex
 * @since 2019
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface ComponentScan {
    @AliasFor("basePackages")
    String[] value() default {};

    @AliasFor("value")
    String[] basePackages() default {};

}
