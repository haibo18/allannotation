/**
 * @author 郭海波
 * @ClassName Main_Fragment_Action
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月7日 下午3:30:47
 * @Modify 修改时间
 */
package cn.qm.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import cn.qm.annotation.OnClick;
import cn.qm.annotation.ViewId;
import cn.qm.shipperapp.R;
import cn.qm.template.BaseFragment;
import cn.qm.widget.MyViewPager;

@ViewId(R.layout.main_fragment_action)
public class Main_Fragment_Action extends BaseFragment {

	@ViewId(R.id.fahuo)
	private RadioButton fahuo;

	@ViewId(R.id.order)
	private RadioButton order;

	@ViewId(R.id.viewpager_manage)
	private MyViewPager viewpager;

	private List<Fragment> mlList;

	FragmentAdapter_Sub_Action fragmentAdapter_Sub_Manage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		initViewPager();
		return view;
	}

	/**
	 * 初始化viewpager
	 */
	private void initViewPager() {
		mlList = new ArrayList<Fragment>();
		mlList.add(new Sub_Fragment_Fahuo());
		mlList.add(new Sub_Fragment_Order());
		fragmentAdapter_Sub_Manage = new FragmentAdapter_Sub_Action(getChildFragmentManager(), mlList);
		viewpager.setAdapter(fragmentAdapter_Sub_Manage);
	}

	@OnClick(R.id.fahuo)
	private void fahuo(View v) {
		viewpager.setCurrentItem(0, false);
		fragmentAdapter_Sub_Manage.notifyDataSetChanged();
	}

	@OnClick(R.id.order)
	private void order(View v) {
		viewpager.setCurrentItem(1, false);
		fragmentAdapter_Sub_Manage.notifyDataSetChanged();
	}

	public class FragmentAdapter_Sub_Action extends FragmentPagerAdapter {
		private List<Fragment> mlList;

		public FragmentAdapter_Sub_Action(FragmentManager fm, List<Fragment> mlList) {
			super(fm);
			this.mlList = mlList;
		}

		@Override
		public Fragment getItem(int arg0) {

			return mlList.get(arg0);
		}

		@Override
		public int getCount() {
			return mlList.size();
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	}
	
}
