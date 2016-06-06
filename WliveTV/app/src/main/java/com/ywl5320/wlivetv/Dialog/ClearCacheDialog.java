package com.ywl5320.wlivetv.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ywl5320.wlivetv.R;

public class ClearCacheDialog extends Dialog {

	private Context context;
	private Button btn;
	private TextView tvUpdateMsg;

	public ClearCacheDialog(Context context) {
		super(context, R.style.ShareDialog);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_clearcache);
		btn = (Button) findViewById(R.id.btn_download);
		tvUpdateMsg = (TextView) findViewById(R.id.tv_update_msg);
		btn.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageLoader.getInstance().clearMemoryCache();
				ImageLoader.getInstance().clearDiskCache();
				dismiss();
			}
		});
	}
	
	public void setUpdateMsg(String msg) {
		tvUpdateMsg.setText(msg);
	}
}
