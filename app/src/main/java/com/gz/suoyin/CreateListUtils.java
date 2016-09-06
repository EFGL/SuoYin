package com.gz.suoyin;

import java.util.ArrayList;

/**
 * Created by Endeavor on 2016/8/2.
 */
public class CreateListUtils {

    public static ArrayList<Good> goods;

    public static ArrayList<Good> createList() {
        if (goods == null) {
            goods = new ArrayList<Good>();
        }
        ArrayList<Good> y = new ArrayList<Good>();
        for (int i = 0; i < 10; i++) {
            Good good = new Good();
            good.setType("油");
            good.setLetter("h");
            good.setName("花生油");
            goods.add(good);
        }
        ArrayList<Good> m = new ArrayList<Good>();
        for (int i = 0; i < 10; i++) {
            Good good = new Good();
            good.setType("面");
            good.setLetter("d");
            good.setName("东北面粉");
            goods.add(good);
        }
        ArrayList<Good> d = new ArrayList<Good>();
        for (int i = 0; i < 10; i++) {
            Good good = new Good();
            good.setType("米");
            good.setLetter("d");
            good.setName("东北大米");
            goods.add(good);
        }
        goods.addAll(y);
        goods.addAll(m);
        goods.addAll(d);

        return goods;
    }
}
