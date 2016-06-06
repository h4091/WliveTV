package com.ywl5320.wlivetv.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ywl5320.wlivetv.BaseActivity;
import com.ywl5320.wlivetv.MyApplication;
import com.ywl5320.wlivetv.R;
import com.ywl5320.wlivetv.adapter.ViewPagerAdapter;
import com.ywl5320.wlivetv.fragment.LiveFragment;
import com.ywl5320.wlivetv.fragment.MeFragment;
import com.ywl5320.wlivetv.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

/**
 * Created by ywl on 10/15/15.
 */
public class NavagationActivity extends BaseActivity{

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private List<Fragment> fragments;

    private LiveFragment liveFragment;
    private VideoFragment videoFragment;
    private MeFragment meFragment;

    private ImageView ivliveNormal;
    private ImageView ivliveSelected;

    private ImageView ivvideoNormal;
    private ImageView ivvideoSelected;

    private ImageView ivmyNormal;
    private ImageView ivmySelected;

    private RelativeLayout rlLive;
    private RelativeLayout rlVideo;
    private RelativeLayout rlMe;

    private TextView tvTitle;
    private long firstTime = 0;
    private String bmobkey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.setNavagationActivity(this);
        /**
         * 这里用的是Bmob的免费服务器，真的好用
         */
        //Bmob.initialize(this, bmobkey);
        setContentView(R.layout.activity_main);
        initSystembar();

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        ivliveNormal = (ImageView)findViewById(R.id.iv_live_normal);
        ivliveSelected = (ImageView)findViewById(R.id.iv_live_selected);

        ivvideoNormal = (ImageView)findViewById(R.id.iv_video_normal);
        ivvideoSelected = (ImageView)findViewById(R.id.iv_video_selected);

        ivmyNormal = (ImageView)findViewById(R.id.iv_my_normal);
        ivmySelected = (ImageView)findViewById(R.id.iv_my_selected);

        rlLive = (RelativeLayout)findViewById(R.id.rl_live);
        rlVideo = (RelativeLayout)findViewById(R.id.rl_video);
        rlMe = (RelativeLayout)findViewById(R.id.rl_me);

        tvTitle = (TextView)findViewById(R.id.tv_title);

        ivliveNormal.setAlpha(0.0f);
        ivvideoSelected.setAlpha(0.0f);
        ivmySelected.setAlpha(0.0f);

        viewPager.setOffscreenPageLimit(3);
        tvTitle.setText("电视直播");
        rlLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0, false);
                setCuttentSelected(0);
            }
        });

        rlVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1, false);
                setCuttentSelected(1);
            }
        });

        rlMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2, false);
                setCuttentSelected(2);
            }
        });
        initFragments();

    }

    /**
     * 初始化fragment
     */
    public void initFragments()
    {
        liveFragment = new LiveFragment();
        videoFragment = new VideoFragment();
        meFragment = new MeFragment();

        fragments = new ArrayList<>();
        fragments.add(liveFragment);
        fragments.add(videoFragment);
        fragments.add(meFragment);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setImageViewAlpha(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                setCuttentSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 设置滑动效果
     * @param position
     * @param offset
     */
    public void setImageViewAlpha(int position, float offset)
    {
        if(position == 0)
        {
            ivliveNormal.setAlpha(offset);
            ivliveSelected.setAlpha(1.0f - offset);

            ivvideoNormal.setAlpha(1.0f - offset);
            ivvideoSelected.setAlpha(offset);
        }
        if(position == 1)
        {
            ivvideoNormal.setAlpha(offset);
            ivvideoSelected.setAlpha(1.0f - offset);

            ivmyNormal.setAlpha(1.0f - offset);
            ivmySelected.setAlpha(offset);
        }
    }

    /**
     * 点击tab
     * @param position
     */
    public void setCuttentSelected(int position)
    {
        if(position == 0)
        {
            ivliveNormal.setAlpha(0f);
            ivliveSelected.setAlpha(1.0f);

            ivvideoNormal.setAlpha(1.0f );
            ivvideoSelected.setAlpha(0f);

            ivmyNormal.setAlpha(1.0f);
            ivmySelected.setAlpha(0f);
            tvTitle.setText("电视直播");
        }
        else if(position == 1)
        {
            ivliveNormal.setAlpha(1.0f);
            ivliveSelected.setAlpha(0f);

            ivvideoNormal.setAlpha(0f );
            ivvideoSelected.setAlpha(1.0f);

            ivmyNormal.setAlpha(1.0f);
            ivmySelected.setAlpha(0f);
            tvTitle.setText("在线影视");
        }
        else if(position == 2)
        {
            ivliveNormal.setAlpha(1.0f);
            ivliveSelected.setAlpha(0f);

            ivvideoNormal.setAlpha(1.0f );
            ivvideoSelected.setAlpha(0f);

            ivmyNormal.setAlpha(0f);
            ivmySelected.setAlpha(1.0f);

            tvTitle.setText("设置中心");
        }

    }

    public static void activityStart(Activity context)
    {
        Intent intent = new Intent(context, NavagationActivity.class);
        context.startActivity(intent);
        context.finish();
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    /**
     * 退出提示
     */
    public void exit() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime  > 1200) {//如果两次按键时间间隔大于1秒，则不退出
            Toast.makeText(this, "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            firstTime = secondTime;//更新firstTime
        } else {
            this.finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ImageLoader.getInstance().clearMemoryCache();
    }
}
