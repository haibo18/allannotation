/**
 * @author 郭海波
 * @ClassName Sub_Fragment_Order
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月7日 下午4:59:38
 * @Modify 修改时间
 */
package cn.qm.fragment;

import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import cn.qm.activity.Activity_OrderDetail;
import cn.qm.annotation.OnClick;
import cn.qm.annotation.ViewId;
import cn.qm.bean.Order;
import cn.qm.shipperapp.R;
import cn.qm.template.BaseFragment;
import cn.qm.until.Utils;

@ViewId(R.layout.sub_fragment_order)
public class Sub_Fragment_Order extends BaseFragment implements OnItemClickListener {
	
	@ViewId(R.id.onAll)
	private RadioButton onAll;
	
	@ViewId(R.id.onDispatch)
	private RadioButton onDispatch;
	
	@ViewId(R.id.onConfirm)
	private RadioButton onConfirm;

	@ViewId(R.id.onTransport)
	private RadioButton onTransport;

	@ViewId(R.id.onGet)
	private RadioButton onGet;

	@ViewId(R.id.onBalance)
	private RadioButton onBalance;
	
	@ViewId(R.id.onEvaluate)
	private RadioButton onEvaluate;

	@ViewId(R.id.order_list)
	private ListView order_list;

	/** 订单数据集 */
	private List<Order> orders = Utils.structure.newArrayList();

	/** 适配器 */
	private BaseAdapter_Order adapter;

	@Override
	public void onStart() {
		super.onStart();
		// TODO 请求网络获取订单数据
		adapter = new BaseAdapter_Order();
		order_list.setAdapter(adapter);
		order_list.setOnItemClickListener(this);
	}

	@OnClick(R.id.onAll)
	private void all(View v) {

	}

	@OnClick(R.id.onDispatch)
	private void dispatch(View v) {

	}

	@OnClick(R.id.onConfirm)
	private void confirm(View v) {

	}

	@OnClick(R.id.onTransport)
	private void transport(View v) {

	}

	@OnClick(R.id.onGet)
	private void get(View v) {

	}

	@OnClick(R.id.onBalance)
	private void balance(View v) {

	}

	@OnClick(R.id.onEvaluate)
	private void evaluate(View v) {

	}

	private class BaseAdapter_Order extends BaseAdapter {

		@Override
		public int getCount() {
			return orders.size();
		}

		@Override
		public Object getItem(int position) {
			return orders.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return null;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), Activity_OrderDetail.class);
		intent.putExtra(Order.class.getName(), orders.get(position));
		getActivity().startActivity(intent);
	}

}
