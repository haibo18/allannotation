/**
 * @author 郭海波
 * @ClassName Sub_Fragment_Fahuo
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月7日 下午4:59:12
 * @Modify 修改时间
 */
package cn.qm.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import cn.qm.activity.Activity_Spare_parts_delivery;
import cn.qm.activity.Activity_Vehicle_delivery;
import cn.qm.annotation.OnClick;
import cn.qm.annotation.ViewId;
import cn.qm.shipperapp.R;
import cn.qm.template.BaseFragment;

@ViewId(R.layout.sub_fragment_fahuo)
public class Sub_Fragment_Fahuo extends BaseFragment {

	@ViewId(R.id.vehicle_delivery)
	private TextView vehicle_delivery;

	@ViewId(R.id.spare_parts_delivery)
	private TextView spare_parts_delivery;
	
	@OnClick(R.id.vehicle_delivery)
	private void zcfh(View v){
		Intent intent = new Intent();
		intent.setClass(getActivity(), Activity_Vehicle_delivery.class);
		getActivity().startActivity(intent);
	}
	
	@OnClick(R.id.spare_parts_delivery)
	private void ldfh(View v){
		Intent intent = new Intent();
		intent.setClass(getActivity(), Activity_Spare_parts_delivery.class);
		getActivity().startActivity(intent);
		 
	}
	
}
