/**
 * @author 郭海波
 * @ClassName Activity_OrderDetail
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月9日 下午12:27:31
 * @Modify 修改时间
 */
package cn.qm.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import cn.qm.annotation.OnClick;
import cn.qm.annotation.ViewId;
import cn.qm.bean.Order;
import cn.qm.fragment.Sub_Fragment_Order_Billing;
import cn.qm.fragment.Sub_Fragment_Order_Carriage;
import cn.qm.fragment.Sub_Fragment_Order_Evaluate;
import cn.qm.fragment.Sub_Fragment_Order_Fahuo;
import cn.qm.fragment.Sub_Fragment_Order_Get;
import cn.qm.fragment.Sub_Fragment_Order_Transport;
import cn.qm.shipperapp.R;
import cn.qm.template.BaseFragmentActivity;
import cn.qm.widget.MyViewPager;

@ViewId(R.layout.activity_orderdetail)
public class Activity_OrderDetail extends BaseFragmentActivity {

	@ViewId(R.id.back)
	private TextView back;

	@ViewId(R.id.onfahuo)
	private RadioButton onfahuo;
	
	@ViewId(R.id.onCarriage)
	private RadioButton onCarriage;

	@ViewId(R.id.onTransport)
	private RadioButton onTransport;

	@ViewId(R.id.onGet)
	private RadioButton onGet;

	@ViewId(R.id.onBilling)
	private RadioButton onBilling;

	@ViewId(R.id.onEvaluate)
	private RadioButton onEvaluate;

	@ViewId(R.id.viewpager)
	private MyViewPager viewpager;
	
	private List<Fragment> mList;
	
	private FragmentAdapter_OrderDetail adatper;
	
	private Order currOrder;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		currOrder = (Order) getIntent().getSerializableExtra(Order.class.getName());
		initViewPage();
	}

	/** 初始化viewpager*/
	private void initViewPage() {
		mList = new ArrayList<Fragment>();
		mList.add(new Sub_Fragment_Order_Fahuo(currOrder));
		mList.add(new Sub_Fragment_Order_Carriage(currOrder));
		mList.add(new Sub_Fragment_Order_Transport(currOrder));
		mList.add(new Sub_Fragment_Order_Get(currOrder));
		mList.add(new Sub_Fragment_Order_Billing(currOrder));
		mList.add(new Sub_Fragment_Order_Evaluate(currOrder));
		FragmentManager fragmentManager = getSupportFragmentManager();
		adatper = new FragmentAdapter_OrderDetail(fragmentManager, mList);
		viewpager.setAdapter(adatper);
	}
	
	@OnClick(R.id.back)
	private void back(View v){
		finish();
	}
	
	@OnClick(R.id.onfahuo)
	private void fahuo(View v){
		viewpager.setCurrentItem(0, false);
		adatper.notifyDataSetChanged();
	}
	
	@OnClick(R.id.onCarriage)
	private void carr(View v){
		viewpager.setCurrentItem(1, false);
		adatper.notifyDataSetChanged();
	}
	
	@OnClick(R.id.onTransport)
	private void trans(View v){
		viewpager.setCurrentItem(2, false);
		adatper.notifyDataSetChanged();
	}
	
	@OnClick(R.id.onGet)
	private void get(View v){
		viewpager.setCurrentItem(3, false);
		adatper.notifyDataSetChanged();
	}
	
	@OnClick(R.id.onBilling)
	private void bill(View v){
		viewpager.setCurrentItem(4, false);
		adatper.notifyDataSetChanged();
	}
	
	@OnClick(R.id.onEvaluate)
	private void eva(View v){
		viewpager.setCurrentItem(5, false);
		adatper.notifyDataSetChanged();
	}
	
	public class FragmentAdapter_OrderDetail extends FragmentPagerAdapter {
		
		private List<Fragment> mList;

		public FragmentAdapter_OrderDetail(FragmentManager fragmentManager, List<Fragment> list) {
			super(fragmentManager);
			mList = list;
		}

		@Override
		public Fragment getItem(int id) {
			return mList.get(id);
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@SuppressWarnings("deprecation")
		@Override
		public void destroyItem(View container, int position, Object object) {
			super.destroyItem(container, position, object);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			super.destroyItem(container, position, object);
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	}
	
}
