package com.ywl5320.wlivetv.fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.ywl5320.wlivetv.Activity.LiveActivity;
import com.ywl5320.wlivetv.Entity.LiveBean;
import com.ywl5320.wlivetv.MyApplication;
import com.ywl5320.wlivetv.R;
import com.ywl5320.wlivetv.adapter.LiveAdapter;
import com.ywl5320.wlivetv.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * Created by ywl on 10/15/15.
 */
public class LiveFragment extends Fragment{

    private LiveAdapter adapter;
    private List<LiveBean> datas;
    private ListView listView;
    private ImageView ivLoading;

    private SwipeRefreshLayout lySrf;
    private boolean isRefresh = false;

    private String []names = new String[]{
            "CCTV-1","CCTV-2","CCTV-3","CCTV-4","CCTV-5","CCTV-5+","CCTV-6","CCTV-7","CCTV-8","CCTV-9","CCTV-10",
    };

    private String []urls = new String[]{
            "http://106.36.45.36/live.aishang.ctlcdn.com/00000110240001_1/encoder/1/playlist.m3u8",
            "http://106.36.45.36/live.aishang.ctlcdn.com/00000110240002_1/encoder/1/playlist.m3u8",
            "http://106.36.45.36/live.aishang.ctlcdn.com/00000110240245_1/encoder/1/playlist.m3u8",
            "http://106.36.45.36/live.aishang.ctlcdn.com/00000110240005_1/encoder/1/playlist.m3u8",
            "http://122.227.101.159:80/livehls1-cnc.wasu.cn/hd_cctv5/z.m3u8",
            "http://106.36.45.36/live.aishang.ctlcdn.com/00000110240128_1/encoder/1/playlist.m3u8",
            "http://106.36.45.36/live.aishang.ctlcdn.com/00000110240247_1/encoder/1/playlist.m3u8",
            "http://106.36.45.36/live.aishang.ctlcdn.com/00000110240009_1/encoder/1/playlist.m3u8",
            "http://106.36.45.36/live.aishang.ctlcdn.com/00000110240249_1/encoder/1/playlist.m3u8",
            "http://106.36.45.36/live.aishang.ctlcdn.com/00000110240014_1/encoder/1/playlist.m3u8",
            "http://106.36.45.36/live.aishang.ctlcdn.com/00000110240015_1/encoder/1/playlist.m3u8"

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_live, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView)getView().findViewById(R.id.listView);
        ivLoading = (ImageView)getView().findViewById(R.id.iv_live_loading);

        lySrf = (SwipeRefreshLayout)getView().findViewById(R.id.id_swipe_ly);
//        lySrf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (!isRefresh) {
//                    isRefresh = true;
//                    queryData();
//                    System.out.println("refresh");
//                }
//            }
//        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LiveBean liveBean = (LiveBean) listView.getItemAtPosition(position);
                if (liveBean != null) {
//                    System.out.println(liveBean.getTvUrl());
                    LiveActivity.activityStart(MyApplication.getNavagationActivity(), liveBean.getTvUrl(), liveBean.getTvName());
                }
            }
        });

        listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
        setadapter();
        initTempData();
    }

    public void initTempData()
    {
       for(int i = 0; i < 10; i++)
       {
           LiveBean liveBean = new LiveBean();
           liveBean.setTvName(names[i]);
           liveBean.setTvUrl(urls[i]);
           datas.add(liveBean);
       }
        ivLoading.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        ImageLoader.getInstance().stop();
    }

    public void setadapter()
    {
        datas = new ArrayList<>();
        adapter = new LiveAdapter(MyApplication.getNavagationActivity(), datas, listView);
        listView.setAdapter(adapter);
    }

}
