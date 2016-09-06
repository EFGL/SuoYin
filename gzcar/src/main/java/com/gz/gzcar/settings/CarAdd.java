package com.gz.gzcar.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gz.gzcar.BaseActivity;
import com.gz.gzcar.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * 新增
 */
public class CarAdd extends BaseActivity {

    @Bind(R.id.add_carnumber)
    EditText mCarnumber;
    @Bind(R.id.add_cartype)
    EditText mCartype;
    @Bind(R.id.add_carwei)
    EditText mCarwei;
    @Bind(R.id.add_person)
    EditText mPerson;
    @Bind(R.id.add_phone)
    EditText mPhone;
    @Bind(R.id.add_address)
    EditText mAddress;
    @Bind(R.id.add_starttiem)
    EditText mStarttiem;
    @Bind(R.id.add_endtime)
    EditText mEndtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.add_btn_cancle, R.id.add_btn_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_btn_cancle:
                finish();
                break;
            case R.id.add_btn_ok:
                insertData();
                break;
        }
    }

    private void insertData() {

    }
}
