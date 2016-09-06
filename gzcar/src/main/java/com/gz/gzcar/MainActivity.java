package com.gz.gzcar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.gz.gzcar.settings.SettingActivity;

import ice_ipcsdk.SDK;

public class MainActivity extends BaseActivity {

    private Context mContext = this;
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;

    SDK sdk = null;
    test_callback callback;
    mjpeg_callback mjpegCallback;
    public static TextView plateText;
    public static TextView plateText2;
    public static ImageView plateImage;
    public static ImageView plateImage2;
    public static ImageView videoStream;
    public static ImageView videoStream2;
    private String ip = "192.168.10.169";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();


        initCapter();

    }

    private void initCapter() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
        sdk = new SDK();


        plateText = (TextView) findViewById(R.id.test);
        plateText2 = (TextView) findViewById(R.id.test2);
        plateImage = (ImageView) findViewById(R.id.imageView);
        plateImage2 = (ImageView) findViewById(R.id.imageView2);
        videoStream = (ImageView) findViewById(R.id.imageView_video);
        videoStream2 = (ImageView) findViewById(R.id.imageView_video2);

        callback = new test_callback();
        mjpegCallback = new mjpeg_callback();

        sdk.ICE_IPCSDK_Open(ip, null);// 1.连接相机
        sdk.ICE_ICPSDK_SetPlateCallback(callback);// 35.设置断网续传事件
        sdk.ICE_IPCSDK_SetMJpegallback_Static(mjpegCallback);// 37.设置mjpeg码流回调
    }

    public static class PlateInfo {
        public String number;
        public String color;
        public byte[] picdata;
        public String strIP;
    }


    // 27.车牌识别事件(车牌号和颜色为utf-8编码)
    public static class test_callback implements SDK.IPlateCallback_Bytes {
        public void ICE_IPCSDK_Plate(String strIP, byte[] strNumber, byte[] strColor,
                                     byte[] bPicData, int nOffset, int nLen, int nOffsetCloseUp, int nLenCloseUp,
                                     int nPlatePosLeft, int nPlatePosTop, int nPlatePosRight, int nPlatePosBottom,
                                     float fPlateConfidence, int nVehicleColor, int nPlateType, int nVehicleDir,
                                     int nAlarmType, int nReserved1, int nReserved2, int nReserved3, int nReserved4) {

            Log.i("mt", "ICE_IPCSDK_Plate执行了...");
            PlateInfo info = new PlateInfo();
            try {
                info.number = new String(strNumber, "GBK");
                info.color = new String(strColor, "GBK");
                info.strIP=strIP;

            } catch (Exception e) {
                e.printStackTrace();
            }
            info.picdata = new byte[nLen];
            System.arraycopy(bPicData, nOffset, info.picdata, 0, nLen);

            Message msg = new Message();
            msg.obj = info;
            msg.what = 0;

            myHandler.sendMessage(msg);
        }
    }

    // 36.mjpeg码流回调
    public static class mjpeg_callback implements SDK.IMJpegCallback_Static {
        public void ICE_IPCSDK_MJpeg(String strIP, byte[] bData, int length) {// 参数：1.相机ip  2.mjpeg数据 3.jpg图片长度
            byte[] bMjpegData = new byte[length];
            System.arraycopy(bData, 0, bMjpegData, 0, length);
            Message msg = new Message();
            msg.obj = bMjpegData;
            msg.what = 1;
            myHandler.sendMessage(msg);

        }

    }


    public static Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    PlateInfo info = (PlateInfo) msg.obj;
                    plateText.setText(info.number + ", " + info.color+",ip:"+info.strIP);
                    plateText2.setText(info.number + ", " + info.color+",ip:"+info.strIP);
                    info.number = null;
                    info.color = null;

                    Bitmap bmp = BitmapFactory.decodeByteArray(info.picdata, 0, info.picdata.length);
                    plateImage.setImageBitmap(bmp);
                    plateImage2.setImageBitmap(bmp);
                    plateImage.invalidate();
                    plateImage2.invalidate();
                    bmp = null;

                    msg.obj = null;
                    msg = null;
                    break;
                case 1:
                    byte[] data = (byte[]) msg.obj;
                    Bitmap bmpStream = BitmapFactory.decodeByteArray(data, 0, data.length);
                    videoStream.setImageBitmap(bmpStream);
                    videoStream2.setImageBitmap(bmpStream);
                    videoStream.invalidate();
                    videoStream2.invalidate();
                    bmpStream = null;

                    msg.obj = null;
                    msg = null;
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void setting(View view) {
        startActivity(new Intent(this, SettingActivity.class));
    }

    public void query(View view) {
        startActivity(new Intent(this, SrarchActivity.class));
    }

    public void exchange(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }



    public void setBasIn(BaseAnimatorSet bas_in) {
        this.mBasIn = bas_in;
    }

    public void setBasOut(BaseAnimatorSet bas_out) {
        this.mBasOut = bas_out;
    }
    @Override
    public void onBackPressed() {
        final NormalDialog dialog = new NormalDialog(mContext);
        dialog.content("亲,真的要走吗?再看会儿吧~(●—●)")//
//                .contentTextColor()
                .style(NormalDialog.STYLE_TWO)//
                .title("温馨提示")
//                .titleTextColor(Color.RED)
                .titleTextSize(23)//
                .btnText("继续看看", "残忍退出")//
                .btnTextColor(Color.parseColor("#383838"), Color.parseColor("#D4D4D4"))//
                .btnTextSize(16f, 16f)//
                .showAnim(mBasIn)//
                .dismissAnim(mBasOut)//
//                .widthScale(0.5f)
                .heightScale(0.5f)
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.superDismiss();
                        finish();
                    }
                });
    }
}
