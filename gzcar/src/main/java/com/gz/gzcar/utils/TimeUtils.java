package com.gz.gzcar.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Endeavor on 2016/7/6.
 */
public class TimeUtils {

    public static void showdatrdialog(Context context,final TextView view){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String datetime=dateFormat.format(new Date());
        String [] splitdate=datetime.split("-");
        DatePickerDialog dpd=new DatePickerDialog(context, 2, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                int mou=arg2+1;
                int data=arg3;
                String strmou="";
                String strdata="";
                if(mou<10){
                    strmou="0"+mou;
                }else {
                    strmou=mou+"";
                }

                if(data<10){
                    strdata="0"+data;
                }else {
                    strdata=data+"";
                }
                view.setText(arg1+"-"+strmou+"-"+strdata);
            }
        }, Integer.parseInt(splitdate[0]), Integer.parseInt(splitdate[1])-1, Integer.parseInt(splitdate[2]));


        dpd.show();
    }


}
