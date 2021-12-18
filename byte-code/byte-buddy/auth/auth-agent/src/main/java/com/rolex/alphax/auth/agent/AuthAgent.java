/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.auth.agent;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.nameContains;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class AuthAgent {
    private static String authServerPath;

    public static String getAuthServerPath() {
        return authServerPath;
    }

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        authServerPath = agentArgs;
        log.info("auth server path: {}", authServerPath);
        final ByteBuddy byteBuddy = new ByteBuddy();
        AgentBuilder builder = new AgentBuilder.Default(byteBuddy).ignore(
                nameStartsWith("net.bytebuddy.")
                        .or(nameStartsWith("org.slf4j."))
                        .or(nameStartsWith("org.springframework."))
                        .or(nameStartsWith("org.groovy."))
                        .or(nameContains("javassist"))
                        .or(nameContains(".asm."))
                        .or(nameContains(".reflectasm."))
                        .or(nameStartsWith("sun.reflect."))
                        .or(ElementMatchers.isSynthetic()));
        builder.type(classMatch())
                .transform(new Transformer())
                .with(new Listener())
                .installOn(instrumentation);
    }

    private static ElementMatcher<? super TypeDescription> classMatch() {
        ElementMatcher.Junction judge = new ElementMatcherJunction() {
            @Override
            public boolean matches(NamedElement target) {
                return true;
            }
        };
//        judge = judge.and(ElementMatchers.isAnnotatedWith(ElementMatchers.named("org.springframework.web.bind.annotation.RestController"))
//                .or(ElementMatchers.isAnnotatedWith(ElementMatchers.named("org.springframework.stereotype.Controller"))));
        judge = judge.and(ElementMatchers.isAnnotatedWith(ElementMatchers.anyOf(Controller.class, RestController.class)));
        return new ProtectiveShieldMatcher(judge);
    }

    static class Transformer implements AgentBuilder.Transformer {
        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
            return builder.method(methodMatch())
                    .intercept(MethodDelegation.to(AuthInterceptor.class));
        }

        private ElementMatcher<? super MethodDescription> methodMatch() {
            ElementMatcher.Junction judge = new ElementMatcherJunction() {
                @Override
                public boolean matches(NamedElement target) {
                    return true;
                }
            };
            judge = judge.and(ElementMatchers.isAnnotatedWith(
                    ElementMatchers.anyOf(RequestMapping.class,
                            GetMapping.class,
                            PostMapping.class,
                            PatchMapping.class,
                            PutMapping.class,
                            DeleteMapping.class))
//                    .or(ElementMatchers.isAnnotatedWith(ElementMatchers.named("org.springframework.web.bind.annotation.PostMapping")))
//                    .or(ElementMatchers.isAnnotatedWith(ElementMatchers.named("org.springframework.web.bind.annotation.GetMapping")))
            );
            return new ProtectiveShieldMatcher(judge);
        }
    }

    static class Listener implements AgentBuilder.Listener {

        @Override
        public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

        }

        @Override
        public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {

        }

        @Override
        public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {

        }

        @Override
        public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {

        }

        @Override
        public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

        }
    }
}
