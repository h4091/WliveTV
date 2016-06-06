package com.ywl5320.wlivetv.Dialog;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ywl5320.wlivetv.R;

public class UpdateAppDialog extends Dialog {

	private Context context;
	private Button btn;
	private TextView tvUpdateMsg;
	private String url;

	public UpdateAppDialog(Context context) {
		super(context, R.style.ShareDialog);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_update);
		btn = (Button) findViewById(R.id.btn_download);
		tvUpdateMsg = (TextView) findViewById(R.id.tv_update_msg);
		btn.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!TextUtils.isEmpty(url)) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					context.startActivity(intent);
				}
				dismiss();
			}
		});
	}
	
	public void setUpdateMsg(String msg, String url) {
		this.url = url;
		tvUpdateMsg.setText(msg);
	}
}
