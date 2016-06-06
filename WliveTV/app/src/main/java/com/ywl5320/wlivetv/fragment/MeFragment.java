package com.ywl5320.wlivetv.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ywl5320.wlivetv.Activity.AboutActivity;
import com.ywl5320.wlivetv.Activity.FeedBackActivity;
import com.ywl5320.wlivetv.Dialog.ClearCacheDialog;
import com.ywl5320.wlivetv.Dialog.UpdateAppDialog;
import com.ywl5320.wlivetv.Entity.LiveBean;
import com.ywl5320.wlivetv.Entity.UpdateBean;
import com.ywl5320.wlivetv.MyApplication;
import com.ywl5320.wlivetv.R;
import com.ywl5320.wlivetv.util.CommonUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by ywl on 10/15/15.
 */
public class MeFragment extends Fragment{

    private TextView tvFeed;
    private TextView tvUpdate;
    private TextView tvAbout;
    private TextView tvClearCache;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_me, container, false);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvFeed = (TextView)getView().findViewById(R.id.tv_feedback);
        tvUpdate = (TextView)getView().findViewById(R.id.tv_update);
        tvAbout = (TextView)getView().findViewById(R.id.tv_about);
        tvClearCache = (TextView)getView().findViewById(R.id.tv_chearcache);

        tvFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedBackActivity.activityStart(MyApplication.getNavagationActivity());
            }
        });

//        tvUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MyApplication.getNavagationActivity(), "正在检查更新", Toast.LENGTH_SHORT).show();
//                BmobQuery<UpdateBean> bmobQuery = new BmobQuery<UpdateBean>();
//                bmobQuery.findObjects(MyApplication.getNavagationActivity(), new FindListener<UpdateBean>() {
//                    @Override
//                    public void onSuccess(List<UpdateBean> list) {
//                        if (list != null && list.size() > 0) {
//                            if (CommonUtil.getAppVersion(MyApplication.getNavagationActivity()) == list.get(0).getVersionCode()) {
//                                Toast.makeText(MyApplication.getNavagationActivity(), "已经是新版本", Toast.LENGTH_SHORT).show();
//                            } else if (CommonUtil.getAppVersion(MyApplication.getNavagationActivity()) < list.get(0).getVersionCode()) {
//                                UpdateAppDialog updateAppDialog = new UpdateAppDialog(MyApplication.getNavagationActivity());
//                                updateAppDialog.show();
//                                updateAppDialog.setUpdateMsg(list.get(0).getUpdateMsg(), list.get(0).getUrl());
//                            }
//                        } else {
//                            Toast.makeText(MyApplication.getNavagationActivity(), "已经是新版本", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(int i, String s) {
//                        Toast.makeText(MyApplication.getNavagationActivity(), "已经是新版本", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

        tvAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.activityStart(MyApplication.getNavagationActivity());
            }
        });

        tvClearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearCacheDialog clearCacheDialog = new ClearCacheDialog(MyApplication.getNavagationActivity());
                clearCacheDialog.show();
                clearCacheDialog.setUpdateMsg("是否清除缓存的图片数据？");
            }
        });

    }
}
