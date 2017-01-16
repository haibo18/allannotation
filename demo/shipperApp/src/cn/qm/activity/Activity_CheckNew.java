/**
 * @author 郭海波
 * @ClassName Activity_CheckNew
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月9日 下午2:11:14
 * @Modify 修改时间
 */
package cn.qm.activity;


import android.text.Editable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import cn.qm.annotation.OnClick;
import cn.qm.annotation.ViewId;
import cn.qm.annotation.onTextWatch;
import cn.qm.bus.Bus2;
import cn.qm.shipperapp.R;
import cn.qm.template.BaseFragmentActivity;

@ViewId(R.layout.activity_checknew)
public class Activity_CheckNew extends BaseFragmentActivity {

	@ViewId(R.id.back)
	private TextView back;

	@OnClick(R.id.back)
	private void back(View v) {
		finish();
		Bus2.getDefault().post("123");
	}

	@onTextWatch(R.id.editText1)
	private void onText(Editable s) {
		Toast.makeText(this, s.toString(), 0).show();
	}

}
