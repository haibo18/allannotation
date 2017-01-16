/**
 * @author 郭海波
 * @ClassName Activity_Main
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Date 2017年1月6日 下午6:55:30
 */
package cn.qm.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import cn.qm.annotation.OnClick;
import cn.qm.annotation.ViewId;
import cn.qm.application.IApplication;
import cn.qm.fragment.Main_Fragment_Action;
import cn.qm.fragment.Main_Fragment_Center;
import cn.qm.shipperapp.R;
import cn.qm.template.BaseFragmentActivity;
import cn.qm.widget.ConfirmLogoutDialog;
import cn.qm.widget.ConfirmLogoutDialog.OnConfirmCheckedListener;
import cn.qm.widget.MyViewPager;

@ViewId(R.layout.activity_main)
public class Activity_Main extends BaseFragmentActivity {

	@ViewId(R.id.viewpager_main)
	private MyViewPager viewPager;
	
	@ViewId(R.id.main_tab_action)
	private RadioButton main_tab_action;

	@ViewId(R.id.main_tab_center)
	private RadioButton main_tab_center;

	FragmentAdapter_Main fragmentAdapter_Main;

	private List<Fragment> mList;

	@OnClick(R.id.main_tab_action)
	private void act(View v){
		viewPager.setCurrentItem(0, false);
		fragmentAdapter_Main.notifyDataSetChanged();
	}
	
	@OnClick(R.id.main_tab_center)
	private void cen(View v){
		viewPager.setCurrentItem(1, false);
		fragmentAdapter_Main.notifyDataSetChanged();
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initViewPager();
		keepAlive();
	}

	/**
	 * 初始化viewpager
	 */
	private void initViewPager() {
		mList = new ArrayList<Fragment>();
		mList.add(new Main_Fragment_Action());
		mList.add(new Main_Fragment_Center());
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentAdapter_Main = new FragmentAdapter_Main(fragmentManager, mList);
		viewPager.setAdapter(fragmentAdapter_Main);
	}

	/**
	 * 保证住页面不被杀死
	 */
	private void keepAlive() {
		IApplication.getInstance().executorService.submit(new Runnable() {

			@Override
			public void run() {
				while (true) {
					SystemClock.sleep(1000);
				}
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ConfirmLogoutDialog confirm = new ConfirmLogoutDialog(this);
			confirm.setConfirmCheckedListener(new OnConfirmCheckedListener() {
				@Override
				public void onConfirmChecked() {
					finish();
				}
			});
			confirm.show();
		}
		return true;
	}

	public class FragmentAdapter_Main extends FragmentPagerAdapter {
		
		private List<Fragment> mList;

		public FragmentAdapter_Main(FragmentManager fragmentManager, List<Fragment> list) {
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
