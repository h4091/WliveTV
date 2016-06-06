package com.ywl5320.wlivetv.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ywl5320.wlivetv.BaseActivity;
import com.ywl5320.wlivetv.Entity.FeedBackBean;
import com.ywl5320.wlivetv.R;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by ywl on 15-10-20.
 */
public class FeedBackActivity extends BaseActivity{

    private TextView tvFeedBack;
    private EditText etFeedBack;
    private ImageView ivBack;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initSystembar();
        tvFeedBack = (TextView)findViewById(R.id.tv_feedback);
        etFeedBack = (EditText)findViewById(R.id.et_feedback);
        ivBack = (ImageView)findViewById(R.id.iv_back);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvTitle.setText("意见反馈");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedBackActivity.this.finish();
            }
        });
//        tvFeedBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String content = etFeedBack.getText().toString();
//                if(TextUtils.isEmpty(content) || content.length() < 10)
//                {
//                    Toast.makeText(FeedBackActivity.this, "请输入至少10个字符", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    FeedBackBean feedBackBean = new FeedBackBean();
//                    feedBackBean.setContent(content);
//                    feedBackBean.save(FeedBackActivity.this, new SaveListener() {
//                        @Override
//                        public void onSuccess() {
//                            Toast.makeText(FeedBackActivity.this, "提交成功，感谢您的建议", Toast.LENGTH_SHORT).show();
//                            etFeedBack.setText("");
//                        }
//
//                        @Override
//                        public void onFailure(int i, String s) {
//
//                        }
//                    });
//                }
//            }
//        });
    }

    public static void activityStart(Context context)
    {
        Intent intent = new Intent(context, FeedBackActivity.class);
        context.startActivity(intent);
    }
}
