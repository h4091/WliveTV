package com.ywl5320.wlivetv.Activity;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ywl5320.wlivetv.BaseActivity;
import com.ywl5320.wlivetv.R;
import com.ywl5320.wlivetv.ijkplayer.Settings;
import com.ywl5320.wlivetv.ijkplayer.media.IjkVideoView;
import com.ywl5320.wlivetv.popupwindow.LiveSizePopupMenu;
import com.ywl5320.wlivetv.util.CommonUtil;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by ywl on 15-10-12.
 */
public class LiveActivity extends BaseActivity {

    private LinearLayout lySystemParent;
    private IjkVideoView videoView;
    private RelativeLayout flVideoView;
    private RelativeLayout rlLoadView;
    private TextView tvLoadmsg;
    private ImageView ivFullScreen;

    private String path = "";
    private Settings mSettings;
    private String title = "";
    private boolean mBackPressed;
    private int count = 0;

    private final int CONNECTION_TIMES = 5;


    private TextView tvTitle;
    private ImageView ivBack;
    private ImageView ivMenu;
    private LinearLayout lyFloor;
    private TextView tvRelierMsg;
    private TextView tvRelief;
    private View vClose;

    private static int SIZE_DEFAULT = 0;
    private static int SIZE_4_3 = 1;
    private static int SIZE_16_9 = 2;
    private int currentSize = SIZE_16_9;


    private int screenWidth = 0;
    private int screenHeight = 0;
    private boolean isShowToolbar = true;

    private LiveSizePopupMenu popupMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        initSystembar();
        path = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        videoView = (IjkVideoView) findViewById(R.id.videoview);
        lySystemParent = (LinearLayout) findViewById(R.id.ly_system_parent);
        flVideoView = (RelativeLayout) findViewById(R.id.fl_videoview);
        rlLoadView = (RelativeLayout) findViewById(R.id.rl_loading);
        tvLoadmsg = (TextView) findViewById(R.id.tv_load_msg);
        ivFullScreen = (ImageView) findViewById(R.id.iv_fullscreen);
        lyFloor = (LinearLayout) findViewById(R.id.ly_floor);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivMenu = (ImageView) findViewById(R.id.iv_menu);
        tvRelierMsg = (TextView) findViewById(R.id.tv_app_msg);
        tvRelief = (TextView) findViewById(R.id.tv_relief);
        vClose = findViewById(R.id.v_close);

        tvTitle.setText(title);
        tvLoadmsg.setText("视频加载中...");
        lySystemParent.setBackgroundColor(getResources().getColor(R.color.colorToolbar));
        ivFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullChangeScreen();
            }
        });
        flVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowToolbar) {
                    isShowToolbar = false;
                    getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
                    ivFullScreen.setImageResource(R.drawable.bg_live_cancel_fullscreen_seletor);
                    hideToolbar(lySystemParent);
                    hideFloor(lyFloor);
                } else {
                    isShowToolbar = true;
                    getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);
                    ivFullScreen.setImageResource(R.drawable.bg_live_cancel_fullscreen_seletor);
                    showToolbar(lySystemParent);
                    showFloor(lyFloor);
                }
            }
        });

        popupMenu = new LiveSizePopupMenu(this);
        popupMenu.setOnItemClickListener(new LiveSizePopupMenu.OnItemClickListener() {
            @Override
            public void onClick(int id) {
                switch (id) {
                    case R.id.tv_4_3:
                        setScreenRate(SIZE_4_3);
                        currentSize = SIZE_4_3;
                        break;
                    case R.id.tv_16_9:
                        setScreenRate(SIZE_16_9);
                        currentSize = SIZE_16_9;
                        break;
                    case R.id.tv_default:
                        setScreenRate(SIZE_DEFAULT);
                        currentSize = SIZE_DEFAULT;
                        break;
                }
            }
        });

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.showLocation(R.id.iv_menu);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)// 横屏
                {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    tvRelierMsg.setText("免责声明");
                } else {
                    LiveActivity.this.finish();
                }
            }
        });

        tvRelierMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)// 横屏
                    showRelier(tvRelief);
            }
        });

//        tvRelief.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideRelier(tvRelief);
//            }
//        });

        vClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vClose.setVisibility(View.GONE);
                hideRelier(tvRelief);
            }
        });

        initVideo();
    }

    public void initVideo() {
        mSettings = new Settings(this);

        screenWidth = CommonUtil.getScreenWidth(this);
        screenHeight = CommonUtil.getScreenHeight(this);


        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        videoView.setVideoURI(Uri.parse(path));

        videoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                videoView.start();
            }
        });
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) flVideoView.getLayoutParams();
        lp.height = CommonUtil.getScreenWidth(this) * 3 / 4;
        lp.width = CommonUtil.getScreenWidth(this);
        flVideoView.setLayoutParams(lp);
        setScreenRate(2);

        videoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                System.out.println("what:" + what);
                switch (what) {
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        rlLoadView.setVisibility(View.VISIBLE);
                        break;
                    case IjkMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        rlLoadView.setVisibility(View.GONE);
                        break;
                }
                return false;
            }
        });

        videoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer mp) {
                System.out.println("onCompletion");
                rlLoadView.setVisibility(View.VISIBLE);
                videoView.stopPlayback();
                videoView.release(true);
                videoView.setVideoURI(Uri.parse(path));
            }
        });

        videoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                if (count > CONNECTION_TIMES) {
                    new AlertDialog.Builder(LiveActivity.this)
                            .setMessage("视频暂时不能播放")
                            .setPositiveButton(R.string.VideoView_error_button,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            /* If we get here, there is no onError listener, so
                                             * at least inform them that the video is over.
                                             */
                                            LiveActivity.this.finish();
                                        }
                                    })
                            .setCancelable(false)
                            .show();
                } else {
                    videoView.stopPlayback();
                    videoView.release(true);
//                    videoView.stopBackgroundPlay();
//                    IjkMediaPlayer.native_profileEnd();
//
//                    IjkMediaPlayer.loadLibrariesOnce(null);
//                    IjkMediaPlayer.native_profileBegin("libijkplayer.so");


                    videoView.setVideoURI(Uri.parse(path));
                }
                System.out.println("replay------" + count++);
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)// 横屏
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            tvRelierMsg.setText("免责声明");
        } else {
            super.onBackPressed();
            mBackPressed = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBackPressed || !videoView.isBackgroundPlayEnabled()) {
            videoView.stopPlayback();
            videoView.release(true);
            videoView.stopBackgroundPlay();
        }
        IjkMediaPlayer.native_profileEnd();
    }

    public static void activityStart(Context context, String url, String title) {
        Intent intent = new Intent(context, LiveActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {//竖屏
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) flVideoView.getLayoutParams();
            lp.height = CommonUtil.getScreenHeight(this);
            lp.width = CommonUtil.getScreenWidth(this);
            flVideoView.setLayoutParams(lp);
            ivFullScreen.setImageResource(R.drawable.bg_live_cancel_fullscreen_seletor);
        } else {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) flVideoView.getLayoutParams();
            lp.height = CommonUtil.getScreenWidth(this) * 3 / 4;
            lp.width = CommonUtil.getScreenWidth(this);
            flVideoView.setLayoutParams(lp);
            ivFullScreen.setImageResource(R.drawable.bg_live_fullscreen_seletor);
        }
        setScreenRate(currentSize);
    }

    public void fullChangeScreen() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)// 切换为竖屏
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            tvRelierMsg.setText("免责声明");
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            tvRelierMsg.setText("");
        }
    }

    public void setScreenRate(int rate) {
        int width = 0;
        int height = 0;
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)// 横屏
        {
            if (rate == SIZE_DEFAULT) {
                width = videoView.getmVideoWidth();
                height = videoView.getmVideoHeight();
            } else if (rate == SIZE_4_3) {
                width = screenWidth / 3 * 4;
                height = screenWidth;
            } else if (rate == SIZE_16_9) {
                width = screenWidth / 9 * 16;
                height = screenWidth;
            }
        } else //竖屏
        {
            if (rate == SIZE_DEFAULT) {
                width = videoView.getmVideoWidth();
                height = videoView.getmVideoHeight();
            } else if (rate == SIZE_4_3) {
                width = screenWidth;
                height = screenWidth * 3 / 4;
            } else if (rate == SIZE_16_9) {
                width = screenWidth;
                height = screenWidth * 9 / 16;
            }
        }
        if (width > 0 && height > 0) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) videoView.getmRenderView().getView().getLayoutParams();
            lp.width = width;
            lp.height = height;
            videoView.getmRenderView().getView().setLayoutParams(lp);
        }
    }

    public void hideToolbar(final View v) {
        ValueAnimator animator;
        animator = ValueAnimator.ofFloat(0, -(CommonUtil.dip2px(this, 55) + CommonUtil.getStatusHeight(this)));
        animator.setTarget(v);
        animator.setDuration(400).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
    }

    public void showToolbar(final View v) {
        ValueAnimator animator;
        if(Build.VERSION.SDK_INT >= 19) {
            animator = ValueAnimator.ofFloat(-(CommonUtil.dip2px(this, 55) + CommonUtil.getStatusHeight(this)), 0);
        }
        else
        {
            animator = ValueAnimator.ofFloat(-CommonUtil.dip2px(this, 55), 0);
        }
        animator.setTarget(v);
        animator.setDuration(500).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
    }

    public void hideFloor(final View v) {
        ValueAnimator animator;
        if(Build.VERSION.SDK_INT >= 19) {
            animator = ValueAnimator.ofFloat(0, (CommonUtil.dip2px(this, 55) + CommonUtil.getStatusHeight(this)));
        }
        else
        {
            animator = ValueAnimator.ofFloat(0, CommonUtil.dip2px(this, 55));
        }

        animator.setTarget(v);
        animator.setDuration(400).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
    }

    public void showFloor(final View v) {
        ValueAnimator animator;
        animator = ValueAnimator.ofFloat(CommonUtil.dip2px(this, 55), 0);
        animator.setTarget(v);
        animator.setDuration(400).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
    }

    public void showRelier(final View v)
    {
        ValueAnimator animator;
        animator = ValueAnimator.ofFloat(0, -CommonUtil.dip2px(this, 172));
        animator.setTarget(v);
        animator.setDuration(400).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
        vClose.setVisibility(View.VISIBLE);
    }

    public void hideRelier(final View v)
    {
        ValueAnimator animator;
        animator = ValueAnimator.ofFloat(-CommonUtil.dip2px(this, 172), 0);
        animator.setTarget(v);
        animator.setDuration(400).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setTranslationY((Float) animation.getAnimatedValue());
            }
        });

    }
}
