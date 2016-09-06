package com.gz.gzcar.settingfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gz.gzcar.R;
import com.gz.gzcar.settings.UserAddActivity;
import com.gz.gzcar.settings.UserUpdateActivity;

/**
 * Created by Endeavor on 2016/8/8.
 *
 * 用户管理
 */
public class UserFragment extends Fragment implements View.OnClickListener {

    private Button add;
    private Button delete;
    private Button update;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user,container,false);

        add = (Button) view.findViewById(R.id.user_add);
        delete = (Button) view.findViewById(R.id.user_delete);
        update = (Button) view.findViewById(R.id.user_update);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_add:

                startActivity(new Intent(getActivity(),UserAddActivity.class));
                break;
            case R.id.user_update:

                startActivity(new Intent(getActivity(),UserUpdateActivity.class));
                break;
            case R.id.user_delete:

                break;
        }
    }
}
