package com.gz.suoyin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Endeavor on 2016/8/2.
 */
public class SearchUtils {

    private static ArrayList<Good>  newList;
    private static ArrayList<Good> searchByletter(List<Good> goods, String letter) {
        if (newList == null) {
            newList = new ArrayList<Good>();
        } else {
            newList.clear();
        }

        for (int i = 0; i < goods.size(); i++) {
            String l = goods.get(i).getLetter();
            if (l.equals(letter)) {

                newList.add(goods.get(i));
            }
        }

        return newList;
    }

    private static ArrayList<Good> searchByType(List<Good> goods, String type) {
        if (newList == null) {
            newList = new ArrayList<Good>();
        } else {
            newList.clear();
        }

//        Log.i("letter内部查询", "goods.size==" + goods.size());
        for (int i = 0; i < goods.size(); i++) {
            String l = goods.get(i).getType();
            if (l.equals(type)) {

                newList.add(goods.get(i));
            }
        }

        return newList;
    }

    /*
     *
     * 说明：
     *      单一条件查询时，其他条件为 null
     * 参数：
     *      List<Good> goods ； 查询的集合
     *      String letter ：查询条件 首字母 小写
     *      String type ：查询条件 种类 汉字
     *
     */
    public static ArrayList<Good> search(List<Good> goods,String letter, String type) {

        if(letter!=null&&type==null){
            ArrayList<Good> result = searchByletter(goods, letter);
            return result;
        }

        if(letter==null&&type!=null){
            ArrayList<Good> result = searchByType(goods, type);
            return result;
        }

        if (letter!=null&&type!=null){
            ArrayList<Good> l = searchByletter(goods, letter);

//        Log.i("letter查询","l=="+l.toString());

            ArrayList<Good> temp = new ArrayList<Good>();
            temp.addAll(l);
            ArrayList<Good> result = searchByType(temp, type);

//        Log.i("type查询","gs=="+gs.toString());

            temp.clear();
            temp=null;
            return result;
        }

        return null;
    }

}
