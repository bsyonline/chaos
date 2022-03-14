package com.rolex.master.controller;

import com.rolex.master.manager.ExecutorManager;
import com.rolex.master.service.DispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@RestController
public class TestController {
    @Autowired
    DispatcherService dispatcherService;

    @GetMapping("/test/{msg}")
    public String test(@PathVariable("msg") String msg) {
        dispatcherService.dispatch(msg);
        return "OK";
    }
}
