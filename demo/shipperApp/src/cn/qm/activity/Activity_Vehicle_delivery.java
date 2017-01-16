/**
 * @author 郭海波
 * @ClassName Activity_Vehicle_delivery
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月10日 下午3:10:13
 * @Modify 修改时间
 */
package cn.qm.activity;

import android.view.View;
import android.widget.TextView;
import cn.qm.annotation.OnClick;
import cn.qm.annotation.ViewId;
import cn.qm.shipperapp.R;
import cn.qm.template.BaseFragmentActivity;

@ViewId(R.layout.activity_vehicle_delivery)
public class Activity_Vehicle_delivery extends BaseFragmentActivity {

	@ViewId(R.id.back)
	private TextView back;
	
	@OnClick(R.id.back)
	private void back(View v){
		finish();
	}
	
}
