/**
 * @author 郭海波
 * @ClassName Main_Fragment_Center
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月7日 下午3:31:27
 * @Modify 修改时间
 */
package cn.qm.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.qm.activity.Activity_AboutUs;
import cn.qm.activity.Activity_Bill;
import cn.qm.activity.Activity_CheckNew;
import cn.qm.activity.Activity_Faq;
import cn.qm.activity.Activity_Message;
import cn.qm.activity.Activity_Score;
import cn.qm.activity.Activity_Verify;
import cn.qm.annotation.Bus;
import cn.qm.annotation.OnClick;
import cn.qm.annotation.Subscribe;
import cn.qm.annotation.ViewId;
import cn.qm.bus.Bus2;
import cn.qm.shipperapp.R;
import cn.qm.template.BaseFragment;

@Bus
@ViewId(R.layout.main_fragment_center)
public class Main_Fragment_Center extends BaseFragment {

	@ViewId(R.id.verify_status)
	private TextView verify_status;

	@ViewId(R.id.score)
	private LinearLayout score;

	@ViewId(R.id.bill)
	private LinearLayout bill;

	@ViewId(R.id.faq)
	private LinearLayout faq;

	@ViewId(R.id.about_us)
	private LinearLayout about_us;

	@ViewId(R.id.check_new)
	private LinearLayout check_new;

	@OnClick(R.id.verify_status)
	private void verify(View v) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), Activity_Verify.class);
		startActivity(intent);
	}

	@OnClick(R.id.message)
	private void message(View v) {
		Intent intent_message = new Intent();
		intent_message.setClass(getActivity(), Activity_Message.class);
		startActivity(intent_message);
	}
	
	@OnClick(R.id.score)
	private void score(View v) {
		Intent intent_Score = new Intent();
		intent_Score.setClass(getActivity(), Activity_Score.class);
		startActivity(intent_Score);
	}

	@OnClick(R.id.bill)
	private void bill(View v) {
		Intent intent_Bill = new Intent();
		intent_Bill.setClass(getActivity(), Activity_Bill.class);
		startActivity(intent_Bill);
	}

	@OnClick(R.id.faq)
	private void faq(View v) {
		Intent intent_Faq = new Intent();
		intent_Faq.setClass(getActivity(), Activity_Faq.class);
		startActivity(intent_Faq);
	}

	@OnClick(R.id.about_us)
	private void about(View v) {
		Intent intent_AboutUs = new Intent();
		intent_AboutUs.setClass(getActivity(), Activity_AboutUs.class);
		startActivity(intent_AboutUs);
	}

	@OnClick(R.id.check_new)
	private void check(View v) {
		Intent intent_CheckNew = new Intent();
		intent_CheckNew.setClass(getActivity(), Activity_CheckNew.class);
		startActivity(intent_CheckNew);
	}

	@Subscribe(Bus2.UI_THREAD)
	public void a(String name) {
		// verify_status.setText(name);
		Log.i("debug", "===========" + Thread.currentThread().getName());
	}

}
