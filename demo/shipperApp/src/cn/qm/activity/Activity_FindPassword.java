/**
 * @author 郭海波
 * @ClassName Activity_FindPassword
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Date 2017年1月6日 下午6:52:08
 */
package cn.qm.activity;

import android.view.View;
import android.widget.TextView;
import cn.qm.annotation.OnClick;
import cn.qm.annotation.ViewId;
import cn.qm.shipperapp.R;
import cn.qm.template.BaseFragmentActivity;

@ViewId(R.layout.activity_findpassword)
public class Activity_FindPassword extends BaseFragmentActivity {

	@ViewId(R.id.back)
	private TextView back;

	@OnClick(R.id.back)
	private void back(View v){
		finish();
	}

}
