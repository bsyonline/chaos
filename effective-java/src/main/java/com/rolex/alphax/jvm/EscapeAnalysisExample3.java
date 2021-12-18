/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.jvm;


/**
 * 标量替换
 *
 * @author rolex
 * @since 2020
 */
public class EscapeAnalysisExample3 {
    public static void main(String[] args) {
        new EscapeAnalysisExample3().show();
    }

    public void optimizedShow() {
        int x = 1;
        int y = 2;
        System.out.println("point=(" + x + ", " + y + ")");
    }

    public void show() {
        Point point = new Point();
        point.x = 1;
        point.y = 2;
        System.out.println("point=(" + point.x + ", " + point.y + ")");
    }

    class Point {
        int x;
        int y;
    }
}
