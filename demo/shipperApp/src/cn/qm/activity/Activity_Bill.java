/**
 * @author 郭海波
 * @ClassName Activity_Bill
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月9日 下午2:10:29
 * @Modify 修改时间
 */
package cn.qm.activity;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.qm.annotation.OnClick;
import cn.qm.annotation.ViewId;
import cn.qm.bean.Score;
import cn.qm.shipperapp.R;
import cn.qm.template.BaseFragmentActivity;
import cn.qm.until.Utils;

@ViewId(R.layout.activity_bill)
public class Activity_Bill extends BaseFragmentActivity {

	@ViewId(R.id.back)
	private TextView back;
	
	
	@OnClick(R.id.back)
	private void back(View v){
		finish();
	}
	
	@ViewId(R.id.lv)
	private ListView lv;

	/** 订单数据集 */
	private List<Score> scores = Utils.structure.newArrayList();

	/** 适配器 */
	private BaseAdapter_Bill adapter;

	@Override
	protected void onStart() {
		super.onStart();
		adapter = new BaseAdapter_Bill();
		lv.setAdapter(adapter);
	}

	private class BaseAdapter_Bill extends BaseAdapter {

		@Override
		public int getCount() {
			return scores.size();
		}

		@Override
		public Object getItem(int position) {
			return scores.get(position);
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

}
