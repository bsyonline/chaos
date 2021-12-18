package com.rolex.spi.impl;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Slf4j
public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("2021-08-08 00:00:00");
        list.add("2021-08-01 00:00:00");

        System.out.println(list);
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        System.out.println(list);

        String val = "twenty-one\r\n16:37:08.970 [main] INFO:+User+logged+out%3dbadguy";
        log.info("aaaaaa-{}", val);
        log.info("aaaaaa-"+ val);
    }
}