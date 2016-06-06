package com.ywl5320.wlivetv;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ywl5320.wlivetv.RxBus.RxBus;
import com.ywl5320.wlivetv.util.CommonUtil;

/**
 * Created by ywl on 15-10-11.
 */
public class BaseActivity extends AppCompatActivity{

    private LinearLayout lySystemBar;
    public RxBus rxBus = RxBus.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initSystembar()
    {
        lySystemBar = (LinearLayout)findViewById(R.id.ly_system_bar);
        if(lySystemBar != null) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);

                lySystemBar.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) lySystemBar.getLayoutParams();
                lp.height = CommonUtil.getStatusHeight(this);
                lySystemBar.setLayoutParams(lp);
            }
            //透明状态栏
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4全透明
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                lySystemBar.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) lySystemBar.getLayoutParams();
                lp.height = CommonUtil.getStatusHeight(this);
                lySystemBar.setLayoutParams(lp);

            }
            else
            {
                lySystemBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
