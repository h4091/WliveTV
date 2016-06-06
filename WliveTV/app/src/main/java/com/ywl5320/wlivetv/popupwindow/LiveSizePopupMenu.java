package com.ywl5320.wlivetv.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ywl5320.wlivetv.R;
import com.ywl5320.wlivetv.util.CommonUtil;

/**
 * Created by ywl on 15-10-21.
 */
public class LiveSizePopupMenu extends PopupWindow implements View.OnClickListener {

    private Activity activity;
    private View popView;

    private TextView tvSize_4_3;
    private TextView tvSize_16_9;
    private TextView tvSize_default;

    private OnItemClickListener onItemClickListener;



    public LiveSizePopupMenu(Activity activity)
    {
        this.activity = activity;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popView = inflater.inflate(R.layout.popupmenu, null);
        this.setContentView(popView);
        this.setWidth(CommonUtil.dip2px(activity, 80));
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);// 获取焦点
        this.setTouchable(true); // 设置PopupWindow可触摸
        this.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);

        tvSize_4_3 = (TextView)popView.findViewById(R.id.tv_4_3);
        tvSize_16_9 = (TextView)popView.findViewById(R.id.tv_16_9);
        tvSize_default = (TextView)popView.findViewById(R.id.tv_default);

        tvSize_4_3.setOnClickListener(this);
        tvSize_16_9.setOnClickListener(this);
        tvSize_default.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(onItemClickListener != null)
        {
            onItemClickListener.onClick(v.getId());
        }
        dismiss();
    }

    public void showLocation(int resourId) {
        showAsDropDown(activity.findViewById(resourId), CommonUtil.dip2px(activity, 0),
                CommonUtil.dip2px(activity, 0));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener
    {
        void onClick(int id);
    }
}
