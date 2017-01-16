/**
 * @author 郭海波
 * @ClassName Activity_AboutUs
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月9日 下午2:11:00
 * @Modify 修改时间
 */
package cn.qm.activity;

import android.view.View;
import android.widget.TextView;
import cn.qm.annotation.OnClick;
import cn.qm.annotation.ViewId;
import cn.qm.shipperapp.R;
import cn.qm.template.BaseFragmentActivity;

@ViewId(R.layout.activity_aboutus)
public class Activity_AboutUs extends BaseFragmentActivity {

	@ViewId(R.id.back)
	private TextView back;
	
	@OnClick(R.id.back)
	private void back(View v){
		finish();
	}

}
