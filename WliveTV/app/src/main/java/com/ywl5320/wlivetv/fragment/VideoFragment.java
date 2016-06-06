package com.ywl5320.wlivetv.fragment;

import android.os.Bundle;
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
import com.ywl5320.wlivetv.Entity.VideoBean;
import com.ywl5320.wlivetv.MyApplication;
import com.ywl5320.wlivetv.R;
import com.ywl5320.wlivetv.adapter.VideoAdapter;
import com.ywl5320.wlivetv.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by ywl on 10/15/15.
 */
public class VideoFragment extends Fragment{

    private VideoAdapter adapter;
    private List<VideoBean> datas;
    private ListView listView;
    private ImageView ivLoading;
    private boolean isLoadData;

    private SwipeRefreshLayout lySrf;
    private boolean isRefresh = false;

    private String []names = new String[]{
            "香港电影","综艺频道","高清音乐","动作电影","电影","周星驰","成龙","喜剧","儿歌","LIVE生活"
    };

    private String []urls = new String[]{
            "http://live.gslb.letv.com/gslb?stream_id=lb_hkmovie_1300&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=letv&expect=1",
            "http://live.gslb.letv.com/gslb?stream_id=lb_ent_1300&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=letv&expect=1",
            "http://live.gslb.letv.com/gslb?stream_id=lb_music_1300&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=letv&expect=1",
            "http://live.gslb.letv.com/gslb?tag=live&stream_id=lb_dzdy_720p&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=C1S&expect=1",
            "http://live.gslb.letv.com/gslb?tag=live&stream_id=lb_movie_720p&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=C1S&expect=1",
            "http://live.gslb.letv.com/gslb?tag=live&stream_id=lb_zxc_720p&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=C1S&expect=1",
            "http://live.gslb.letv.com/gslb?tag=live&stream_id=lb_cl_720p&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=C1S&expect=1",
            "http://live.gslb.letv.com/gslb?tag=live&stream_id=lb_comedy_720p&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=C1S&expect=1",
            "http://live.gslb.letv.com/gslb?tag=live&stream_id=lb_erge_720p&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=C1S&expect=1",
            "http://live.gslb.letv.com/gslb?tag=live&stream_id=lb_livemusic_720p&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=C1S&expect=1"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView)getView().findViewById(R.id.listView);
        ivLoading = (ImageView)getView().findViewById(R.id.iv_video_loading);

        lySrf = (SwipeRefreshLayout)getView().findViewById(R.id.id_swipe_ly);
//        lySrf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if(!isRefresh)
//                {
//                    isLoadData = false;
//                    isRefresh = true;
//                    System.out.println("refresh");
//                }
//            }
//        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoBean videoBean = (VideoBean) listView.getItemAtPosition(position);
                if (videoBean != null) {
//                    System.out.println(liveBean.getTvUrl());
                    LiveActivity.activityStart(MyApplication.getNavagationActivity(), videoBean.getTvUrl(), videoBean.getTvName());
                }
            }
        });
        listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
        setadapter();
        initTemData();
    }

    public void initTemData()
    {
        for(int i = 0; i < 10; i++)
        {
            VideoBean videoBean = new VideoBean();
            videoBean.setTvName(names[i]);
            videoBean.setTvUrl(urls[i]);
            datas.add(videoBean);
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
        adapter = new VideoAdapter(MyApplication.getNavagationActivity(), datas, listView);
        listView.setAdapter(adapter);
    }

    /**
     * 网络获取连接
     */
//    public void queryData() {
//        if(CommonUtil.checkTimeOut(MyApplication.getNavagationActivity()))
//        {
//            ImageLoader.getInstance().clearMemoryCache();
//            ImageLoader.getInstance().clearDiskCache();
//        }
//        if (!isLoadData) {
//            BmobQuery<VideoBean> bmobQuery = new BmobQuery<VideoBean>();
//            bmobQuery.findObjects(MyApplication.getNavagationActivity(), new FindListener<VideoBean>() {
//                @Override
//                public void onSuccess(List<VideoBean> list) {
//                    for(int i = 0;  i < 10; i++)
//                    {
//                        System.out.println(list.get(i).getTvName());
//                        System.out.println(list.get(i).getTvUrl());
//                        System.out.println("-------------------------");
//                    }
//                    if (list != null) {
//                        ivLoading.setVisibility(View.GONE);
//                        adapter.updateDatas(list);
//                        isLoadData = true;
//                    }
//                    isRefresh = false;
//                    lySrf.setRefreshing(false);
//                }
//
//                @Override
//                public void onError(int i, String s) {
//                    isRefresh = false;
//                    lySrf.setRefreshing(false);
//                }
//            });
//        }
//    }
}
