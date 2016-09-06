package com.gz.suoyin;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText et;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FloatingActionButton fab;

    /*
     * AsyncTask
     * android提供给我们的一个多线程编程框架,用来进行耗时的操作。防止ANR（Application not responding）
     * 内部实现了Handler和多线程的线程池，线程池会通过根据当前机器运行CUP的个数决定线程池中的线程个数
     * 我们需要定义一个类来继承AsyncTask这个抽象类，并实现其唯一的一个doInBackgroud抽象方法
     *
     * onPreExecute(): 这个方法是在执行异步任务之前的时候执行，并且是在UI Thread当中执行的
     * 通常我们在这个方法里做一些UI控件的初始化的操作，例如弹出ProgressDialog
     *
     * doInBackground(Params… params): 在onPreExecute()方法执行完后，会马上执行这个方法，这个方法就是来处理异步任务的方法
     * Android操作系统会在后台的线程池当中开启一个worker thread来执行这个方法（即在worker thread当中执行），
     * 执行完后将执行结果发送给最后一个 onPostExecute 方法，在这个方法里，我们可以从网络当中获取数据等一些耗时的操作
     *
     * onPostExecute(Result… result): 当异步任务执行完之后，就会将结果返回给这个方法，这个方法也是在UI Thread当中调用的，
     * 我们可以将返回的结果显示在UI控件上
     *
     * onProgressUpdate(Progess… values): 这个方法也是在UI Thread当中执行的，在异步任务执行的时候，有时需要将执行的进度返回给UI界面，
     * 例如下载一张网络图片，我们需要时刻显示其下载的进度，就可以使用这个方法来更新进度。这个方法在调用之前，我们需要在 doInBackground
     * 方法中调用一个 publishProgress(Progress) 的方法来将进度时时刻刻传递给 onProgressUpdate 方法来更新
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.btn);
        registerForContextMenu(btn);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // App Logo
        toolbar.setLogo(R.drawable.ic_icon);
        // 标题
        toolbar.setTitle("索引");
        // 标题颜色
//        toolbar.setTitleTextColor(Color.RED);
        // 副标题
        toolbar.setSubtitle("汉字转拼音");
        // 副标题颜色
//        toolbar.setSubtitleTextColor(Color.GREEN);

        toolbar.setBackgroundColor(Color.BLUE);
        // 隐藏
//        toolbar.setVisibility(View.GONE);

        // 导航栏图标 要設定在 setSupoortActionBar 之后才有作用
        // 否則會出現 back button （但是我没遇到这种情况）
        toolbar.setNavigationIcon(R.drawable.ic_back);

        // 设置后会在右侧显示小圆点的Menu
        // 需要设置在导航图标点击事件之前 否则不响应
        setSupportActionBar(toolbar);


        // 导航图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "退出程序", Snackbar.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, "返回", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // 整个toolbar都会响应的点击事件
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(v, "切换ToolBar颜色", Snackbar.LENGTH_SHORT).setAction("切换", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toolbar.setBackgroundColor(Color.BLACK);
                    }
                }).show();
            }
        });

        // 整个toolbar都会响应的长按事件
//        toolbar.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(MainActivity.this, "长按", Toast.LENGTH_SHORT).show();
//                return true;// true:长按之后只响应LongClick事件 false：按之后先响应LongClick事件后继续响应单击事件
//            }
//        });


        // FloatingActionButton:悬浮按钮(高级ImageButton)
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar:高级Toast 可以与用户交互 setAction 也可以仅仅像一个Toast
                Snackbar.make(view, "切换ToolBar颜色", Snackbar.LENGTH_LONG)
                        .setAction("切换", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                toolbar.setBackgroundColor(Color.BLUE);
                            }
                        }).setActionTextColor(Color.RED).show();

            }
        });

        et = (EditText) findViewById(R.id.edit_text);

//        init();

//        int i = px2dip(this, 1);
//        Snackbar.make(fab, "i==" + i, Snackbar.LENGTH_SHORT).show();

//        Log.i("main", "i==" + i);

        initList();
    }


    public void initList() {
        ArrayList<Good> goods = CreateListUtils.createList();

        Log.i("MainActivity", "goods==" + goods.toString());

        ArrayList<Good> d = SearchUtils.search(goods, "d",null);

        Log.i("首字母查询", "d==" + d.toString());

        ArrayList<Good> m = SearchUtils.search(goods, null, "米");

        Log.i("类型查询", "m==" + m.toString());

        ArrayList<Good> g = SearchUtils.search(goods, "d", "面");

        Log.i("首字母+类型查询", "g==" + g.toString());
    }




    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.menu_tv, menu);

        menu.setHeaderTitle("修改字体");
//        menu.setHeaderIcon(R.drawable.ic_icon);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        Snackbar.make(fab, "" + scale, Snackbar.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this, "scale==" + scale, Toast.LENGTH_SHORT).show();

        Log.i("main", "scale==" + scale);


        return (int) (pxValue / scale + 0.5f);
    }

    // NavigationView MateriaDsign侧滑控件 可定制低
//    private void init() {
//
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
//        mNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);
//
//        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            private MenuItem mPreMenuItem;
//
//            @Override
//            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                if (mPreMenuItem != null)
//                    mPreMenuItem.setChecked(false);
//                menuItem.setChecked(true);
//                mDrawerLayout.closeDrawers();
//                mPreMenuItem = menuItem;
//                return true;
//            }
//        });
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void hanzi2pinyin(View view) {
        String hanzi = et.getText().toString();
        if (hanzi.length() > 0) {
            String s = PinyinUtils.hanzi2pinyin(hanzi);
            et.setText(s);
        }

    }

    public void initPopWindow(ListView listView, View view) {

        // 下拉选择框 显示一个listView 宽：200 高：400
        PopupWindow popupWindow = new PopupWindow(listView, 200, 400);

        // 点击pop意外的区域自动消失  第一步先设置一个透明背景 目的是让让pop有外部区域
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);// 第二步 设置外部区域可被点击

        // pop条目可以被点击 防止获取不到焦点
        popupWindow.setFocusable(true);

        // pop显示在指定view 下面，0，0代表偏移量
        popupWindow.showAsDropDown(view, 0, 0);
//        popupWindow.showAtLocation((View parent, int gravity, int x, int y); // 另一种显示方式 (指定位置)


        // 让 pop 消失的方法
        popupWindow.dismiss();

    }

    public void setActionVisible(Menu menu, boolean enable) {

        try {
            Class<?> aClass = Class.forName("com.android.internal.view.menu.MenuBuilder");
            Method m = aClass.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);

            m.invoke(menu, enable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
