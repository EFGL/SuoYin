package com.gz.gzcar.settingfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gz.gzcar.R;
import com.nightonke.jellytogglebutton.JellyToggleButton;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Endeavor on 2016/8/8.
 * <p/>
 * 系统设置
 */
public class SettingsFragment extends Fragment {
    @Bind(R.id.tb_togglebutton)
    JellyToggleButton mTogglebutton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

//        initViews();
    }

    private void initViews() {

        mTogglebutton.setBackgroundColor(Color.WHITE);
        mTogglebutton.setText("否", "是");
        mTogglebutton.setTextColor(Color.BLACK);
        mTogglebutton.setTextSize(20);
        mTogglebutton.setTextMarginLeft(3);
        mTogglebutton.setTextMarginRight(3);
        mTogglebutton.setRightThumbColor(Color.GREEN);
        mTogglebutton.setLeftThumbColor(Color.RED);
        mTogglebutton.setChecked(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
