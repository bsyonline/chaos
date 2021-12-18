/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.auth.agent;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rolex.alphax.model.Response;
import com.rolex.alphax.model.User;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class AuthInterceptor {

    @RuntimeType
    public static Object intercept(@Origin Method method,
                                   @AllArguments Object[] args,
                                   @SuperCall Callable<?> callable) throws Exception {
        long start = System.nanoTime();
        Object result = null;
        try {
            log.info(AuthAgent.getAuthServerPath());
            log.info("MethodName={}", method.getName());
            log.info("ArgsCount={}", method.getParameterCount());
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                log.info("args[{}] type={}, value=", i, method.getParameterTypes()[i].getTypeName(), args[i]);
            }
            Map<String, Object> params = new HashMap<>();
            HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
            String requestURI = request.getRequestURI();
            params.put("requestURI", requestURI);
            Response response = authenticate(params);
            if (response.getCode() == 200) {
                result = callable.call();
                return result;
            }
            return response;
        } finally {
            log.info("ReturnType={}", method.getReturnType().getName());
            log.info("ReturnValue={}", result);
            log.info("{} counts {} ms", method.getName(), (System.nanoTime() - start) / 1000000);
        }
    }

    private static Response authenticate(Map<String, Object> params) {
        String url = AuthAgent.getAuthServerPath();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity entity = new HttpEntity(new Gson().toJson(params), headers);
        ResponseEntity<String> exchange = new RestTemplate().exchange(url, HttpMethod.POST, entity, String.class);
        log.info("request url={}, params={}, responseCode={}, responseBody={}", url, params, exchange.getStatusCode(), exchange.getBody());
        if (exchange.getStatusCode().is2xxSuccessful()) {
            JsonElement jsonElement = JsonParser.parseString(exchange.getBody());
            JsonObject respJsonObject = jsonElement.getAsJsonObject();
            int code = respJsonObject.get("code") == null ? 401 : respJsonObject.get("code").getAsInt();
            String message = respJsonObject.get("message") == null ? "请求未授权" : respJsonObject.get("message").getAsString();
            JsonElement resultJsonObject = respJsonObject.get("result");
            if (code != 200 || resultJsonObject == null) {
                return new Response(code, message);
            }
            JsonObject result = resultJsonObject.getAsJsonObject();
            User user = new Gson().fromJson(result.toString(), User.class);
            return new Response(200, "成功", user);
        }
        return new Response(401, "请求未授权");
    }

}
