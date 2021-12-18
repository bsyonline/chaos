/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.principle.ocp;

import jdk.nashorn.internal.ir.annotations.Ignore;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * @author rolex
 * @since 2020
 */
public class Store {
    private final static ArrayList<IGoods> GOODS = new ArrayList<>();

    static {
        GOODS.add(new OffGoods("天龙八部", 3200));
        GOODS.add(new OffGoods("巴黎圣母院", 5600));
        GOODS.add(new OffGoods("悲惨世界", 3500));
        GOODS.add(new OffGoods("金瓶梅", 4300));
    }

    public static void main(String[] args) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(2);
        System.out.println("-----------书店卖出去的书籍记录如下：-----------");
        for (IGoods good : GOODS) {
            System.out.println("商品名称：" + good.getName() + "\t商品价格：" + formatter.format(good.getPrice() / 100.0) + "元");
        }
    }

}
