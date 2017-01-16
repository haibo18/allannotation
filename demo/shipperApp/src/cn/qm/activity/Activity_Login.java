/**
 * @author 郭海波
 * @ClassName Activity_Login
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Date 2017年1月6日 下午6:06:09
 */
package cn.qm.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import cn.qm.annotation.OnClick;
import cn.qm.annotation.ViewId;
import cn.qm.shipperapp.R;
import cn.qm.template.BaseFragmentActivity;

@ViewId(R.layout.activity_login)
public class Activity_Login extends BaseFragmentActivity {

	@ViewId(R.id.register)
	private TextView register;

	@ViewId(R.id.find_password)
	private TextView find_password;

	@ViewId(R.id.login)
	private Button login;

	@OnClick(R.id.register)
	private void reg(View v) {
		Intent register_intent = new Intent();
		register_intent.setClass(Activity_Login.this, Activity_Register.class);
		startActivity(register_intent);
	}

	@OnClick(R.id.find_password)
	private void find(View v) {
		Intent findpassword_intent = new Intent();
		findpassword_intent.setClass(Activity_Login.this, Activity_FindPassword.class);
		startActivity(findpassword_intent);
	}

	@OnClick(R.id.login)
	private void log(View v) {
		Intent login_intent = new Intent();
		login_intent.setClass(Activity_Login.this, Activity_Main.class);
		startActivity(login_intent);
		finish();
	}

}
