package com.rolex.spi;

import com.sun.deploy.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class Test {
    public static void main(String[] args) throws IOException, ParseException {

//        Pattern VARIABLE_REPLACEMENT_PATTERN = Pattern.compile("\\$\\{([a-zA-Z\\-/_.0-9]+)\\}");
//        StringBuilder buffer = new StringBuilder();
//        String value ="${hive.home}/lib/*";
//        int startIndex = 0;
//        ArrayList visitedVariables = new ArrayList();
//        visitedVariables. add("hive.classpath");
//        Matcher matcher = VARIABLE_REPLACEMENT_PATTERN.matcher(value);
//        while (matcher.find(startIndex)) {
//            if (startIndex < matcher.start()) {
//                // Copy everything up front to the buffer
//                buffer.append(value, startIndex, matcher.start());
//            }
//            String subVariable = matcher.group(1);
//            System.out.println(subVariable);
//            startIndex = matcher.end();
//        }

        boolean a = false;

        System.out.println(a | false | false);

        Date date = new Date(1631811600000L);
        Date date1 = new Date(1631725200000L);
        System.out.println(date);
        System.out.println(date1);


        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");
        list.add("eee");
        String s = parseRangeParam1("2021-08-01,2021-08-05,2021-08-09,2021-08-10");
        System.out.println(s);
    }
    private static String parseRangeParam1(String param) {
        String execDateRange = StringUtils.join(getAllRangeDate(parseRangeParam(param)), ",");
        return execDateRange;
    }
    public static List<String> getAllRangeDate(List<BizDateRange> range) {
        Set<String> set = new HashSet<>();
        for (BizDateRange bizDateRange : range) {
            set.addAll(getRangeDate(bizDateRange.getStart(), bizDateRange.getEnd()));
        }
        List<String> list = new ArrayList<>(set);
        Collections.sort(list);
        return list;
    }
    private static List<BizDateRange> parseRangeParam(String param) {
        List<BizDateRange> list = new ArrayList<>();

        String[] split = param.split(",", -1);
        if (split.length % 2 != 0) {
            return list;
        }
        for (int i = 0; i < split.length; i = i + 2) {
            BizDateRange bizDateRange = new BizDateRange();
            bizDateRange.setStart(split[i]);
            bizDateRange.setEnd(split[i + 1]);
            list.add(bizDateRange);
        }
        return list;
    }
    static List<String> getRangeDate(String start, String end) {
        List<String> list = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        long distance = ChronoUnit.DAYS.between(startDate, endDate);
        if (distance == 0) {
            list.add(start);
            return list;
        }
        long limit = distance + 1;
        for (LocalDate d = startDate; ; d = d.plusDays(1)) {
            if (limit-- == 0) {
                break;
            }
            list.add(d.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        return list;
    }

    static class BizDateRange{
        String start;
        String end;

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }
    }
}
