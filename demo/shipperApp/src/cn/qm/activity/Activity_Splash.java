/**
 * @author 郭海波
 * @ClassName Activity_Splash
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Date 2017年1月6日 下午6:06:09
 */
package cn.qm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import cn.qm.annotation.ViewId;
import cn.qm.shipperapp.R;
import cn.qm.template.BaseFragmentActivity;

@ViewId(R.layout.activity_splash)
public class Activity_Splash extends BaseFragmentActivity {

	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent();
				intent.setClass(Activity_Splash.this, Activity_Login.class);
				startActivity(intent);
				finish();
			}
		}, 3000);
	}

}
