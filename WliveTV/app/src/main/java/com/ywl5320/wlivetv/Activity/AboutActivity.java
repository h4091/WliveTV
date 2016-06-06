package com.ywl5320.wlivetv.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ywl5320.wlivetv.BaseActivity;
import com.ywl5320.wlivetv.R;
import com.ywl5320.wlivetv.util.CommonUtil;

/**
 * Created by ywl on 15-10-20.
 */
public class AboutActivity extends BaseActivity {

    private TextView tvVersion;
    private ImageView ivBack;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initSystembar();
        tvVersion = (TextView) findViewById(R.id.tv_version);
        ivBack = (ImageView)findViewById(R.id.iv_back);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvTitle.setText("关于软件");
        tvVersion.setText("版本 V：" + CommonUtil.getAppVersionName(this));

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.this.finish();
            }
        });
    }

    public static void activityStart(Context context)
    {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

}
