package com.gz.gzcar.settingfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gz.gzcar.R;
import com.gz.gzcar.settings.ParkingAddActivity;

/**
 * Created by Endeavor on 2016/8/8.
 *
 * 车位管理
 */
public class ParkingManagerFragment extends Fragment implements View.OnClickListener {

    private Button add;
    private Button delete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_parking_manager,container,false);

        add = (Button) view.findViewById(R.id.parking_add);
        delete = (Button) view.findViewById(R.id.parking_delete);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        add.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.parking_add:

                startActivity(new Intent(getActivity(),ParkingAddActivity.class));
                break;
            case R.id.parking_delete:

                break;
        }
    }
}
