package com.gz.gzcar.settings;

import android.os.Bundle;

import com.gz.gzcar.BaseActivity;
import com.gz.gzcar.R;
import com.gz.gzcar.weight.MyPullText;

import java.util.ArrayList;

public class UserAddActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);

        MyPullText id = (MyPullText) findViewById(R.id.user_id);// 类型
        MyPullText type = (MyPullText) findViewById(R.id.user_type);// 顺序

        ArrayList<String> idlist = new ArrayList<>();
        idlist.add("张三");
        idlist.add("李四");
        id.setPopList(idlist);
        id.setText("张三");

        ArrayList<String> typelist = new ArrayList<>();
        typelist.add("操作员");
        typelist.add("管理员");
        type.setPopList(typelist);
        type.setText("操作员");

    }

}
