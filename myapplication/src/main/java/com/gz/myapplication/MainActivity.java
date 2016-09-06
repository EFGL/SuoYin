package com.gz.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ice_ipcsdk.SDK;

public class MainActivity extends AppCompatActivity {

    SDK sdk = null;
    test_callback callback;
    mjpeg_callback mjpegCallback;
    public TextView ipText;
    public static TextView plateText;
    public static ImageView plateImage;
    public static ImageView videoStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        myToolbar.setTitleTextColor(Color.WHITE);
//        setSupportActionBar(myToolbar);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
        sdk = new SDK();

        ipText = (TextView) findViewById(R.id.editText);
        plateText = (TextView) findViewById(R.id.test);
        plateImage = (ImageView) findViewById(R.id.imageView);
        videoStream = (ImageView) findViewById(R.id.imageView_video);

        callback = new test_callback();
        mjpegCallback = new mjpeg_callback();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (sdk != null) {
            sdk.ICE_IPCSDK_Close();//  断开连接
            sdk = null;
        }
    }

    public void OnClick(View v) {


        sdk.ICE_IPCSDK_Open(ipText.getText().toString(), null);// 1.连接相机
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
                    info.number = null;
                    info.color = null;

                    Bitmap bmp = BitmapFactory.decodeByteArray(info.picdata, 0, info.picdata.length);
                    plateImage.setImageBitmap(bmp);
                    plateImage.invalidate();
                    bmp = null;

                    msg.obj = null;
                    msg = null;
                    break;
                case 1:
                    byte[] data = (byte[]) msg.obj;
                    Bitmap bmpStream = BitmapFactory.decodeByteArray(data, 0, data.length);
                    videoStream.setImageBitmap(bmpStream);
                    videoStream.invalidate();
                    bmpStream = null;

                    msg.obj = null;
                    msg = null;
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

//   send
//        SDK.TriggerResult triggerResult = sdk.ICE_IPCSDK_Trigger();// 手动触发车牌识别，相机强制执行一次车牌识别。 //  ICE_IPCSDK_Plate 车牌识别事件。 // ICE_IPCSDK_Plate_Bytes 车牌识别事件。
//        if (triggerResult == null) {
//            Toast.makeText(this, "识别失败", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        String num = triggerResult.number;
//        String color = triggerResult.color;
//        Toast.makeText(this, "车牌号:" + num + ",车牌颜色:" + color, Toast.LENGTH_SHORT).show();
//        byte[] photo = triggerResult.picdata;
//
//        BitmapFactory.decodeByteArray(photo, 0, photo.length);
//    }
//
//    public void capture(View view) {
//        sdk.ICE_ICPSDK_SetSnapCfg(true, true);// 配置设备是否抓拍全景图和特写图。
//        SDK.CaptureResult captureResult = sdk.ICE_IPCSDK_Capture();// 手动抓拍。
//        if (captureResult == null) {
//            Toast.makeText(this, "拍照失败", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        byte[] photo = captureResult.picdata;
//        if (photo.length > 0) {
//
//            BitmapFactory.decodeByteArray(photo, 0, photo.length);
//        }
//    }



}
