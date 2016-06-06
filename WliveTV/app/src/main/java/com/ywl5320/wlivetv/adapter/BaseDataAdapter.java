package com.ywl5320.wlivetv.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.ywl5320.wlivetv.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by ywl on 2014/12/23.
 */
public class BaseDataAdapter extends BaseAdapter {

    protected Context context;
    protected LayoutInflater mlayoutInflate;
    protected List<? extends BmobObject> mDatas = new ArrayList<BmobObject>();
    public DisplayImageOptions options;

    public BaseDataAdapter(Context context, List<? extends BmobObject> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.mipmap.icon_live_item_default)
                .showImageForEmptyUri(R.mipmap.icon_live_item_default)
                .showImageOnFail(R.mipmap.icon_live_item_default)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer())
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
        if (this.context == null) {
            System.out.println("this.context is null");
        } else {
            mlayoutInflate = LayoutInflater.from(this.context);
        }
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
