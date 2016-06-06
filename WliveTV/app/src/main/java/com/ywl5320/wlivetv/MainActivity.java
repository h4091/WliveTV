package com.ywl5320.wlivetv;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ywl5320.wlivetv.Activity.LiveActivity;
import com.ywl5320.wlivetv.Entity.LiveBean;
import com.ywl5320.wlivetv.adapter.LiveAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends BaseActivity {

    private LiveAdapter adapter;
    private List<LiveBean> datas;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "2b77bf16ea2de19f99511df6e59fcad8");
        setContentView(R.layout.activity_main);
        initSystembar();
//        listView = (ListView)findViewById(R.id.listview);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                LiveBean liveBean = (LiveBean)listView.getItemAtPosition(position);
//                if(liveBean != null)
//                {
//                    System.out.println(liveBean.getTvUrl());
//                    LiveActivity.activityStart(MainActivity.this, liveBean.getTvUrl());
//                }
//            }
//        });
//
//        setadapter();
//        queryData();

    }


    public void queryData()
    {
        BmobQuery<LiveBean> bmobQuery = new BmobQuery<LiveBean>();
        bmobQuery.findObjects(this, new FindListener<LiveBean>() {
            @Override
            public void onSuccess(List<LiveBean> list) {
                System.out.println(list.size());
                adapter.updateDatas(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

}
