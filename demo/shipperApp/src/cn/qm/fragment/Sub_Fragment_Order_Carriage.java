/**
 * @author 郭海波
 * @ClassName Sub_Fragment_Order_Carriage
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月9日 下午1:19:20
 * @Modify 修改时间
 */
package cn.qm.fragment;

import cn.qm.annotation.ViewId;
import cn.qm.bean.Order;
import cn.qm.shipperapp.R;
import cn.qm.template.BaseFragment;

@ViewId(R.layout.sub_fragment_order_carriage)
public class Sub_Fragment_Order_Carriage extends BaseFragment {

	private Order currOrder;
	
	public Sub_Fragment_Order_Carriage(Order currOrder) {
		super();
		this.currOrder = currOrder;
	}


}
