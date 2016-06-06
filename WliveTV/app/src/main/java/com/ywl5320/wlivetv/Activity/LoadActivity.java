package com.ywl5320.wlivetv.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ywl5320.wlivetv.BaseActivity;
import com.ywl5320.wlivetv.MainActivity;
import com.ywl5320.wlivetv.R;

/**
 * Created by ywl on 15-10-17.
 */
public class LoadActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window=LoadActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        setContentView(R.layout.activity_load);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NavagationActivity.activityStart(LoadActivity.this);
            }
        }, 1500);
    }
}
