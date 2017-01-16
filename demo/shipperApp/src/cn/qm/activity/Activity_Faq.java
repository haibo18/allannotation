/**
 * @author 郭海波
 * @ClassName Activity_Faq
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月9日 下午2:10:46
 * @Modify 修改时间
 */
package cn.qm.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.qm.annotation.OnClick;
import cn.qm.annotation.ViewId;
import cn.qm.shipperapp.R;
import cn.qm.template.BaseFragmentActivity;

@ViewId(R.layout.activity_faq)
public class Activity_Faq extends BaseFragmentActivity {

	@ViewId(R.id.back)
	private TextView back;

	@ViewId(R.id.exListView)
	private ExpandableListView exlistView;

	@OnClick(R.id.back)
	private void back(View v) {
		finish();
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		exlistView.setAdapter(new MyAdapter());
	}
	
	class MyAdapter extends BaseExpandableListAdapter {

		// 设置组视图的显示文字
		private String[] generalsTypes = new String[] { "常见问题1", "常见问题2", "常见问题3" };
		// 子视图显示文字
		private String[][] generals = new String[][] { { "答案1", "答案2", "答案3", "答案4", "答案5", "答案6" }, { "答案1", "答案2", "答案3", "答案4", "答案5", "答案6" }, { "答案1", "答案2", "答案3", "答案4", "答案5", "答案6" }

		};

		// 自己定义一个获得textview的方法
		@SuppressWarnings("deprecation")
		TextView getTextView() {
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 100);
			TextView textView = new TextView(Activity_Faq.this);
			textView.setLayoutParams(lp);
			textView.setGravity(Gravity.CENTER_VERTICAL);
			textView.setPadding(36, 0, 0, 0);
			textView.setTextSize(20);
			textView.setTextColor(Color.BLACK);
			return textView;
		}

		@Override
		public int getGroupCount() {
			return generalsTypes.length;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return generals[groupPosition].length;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return generalsTypes[groupPosition];
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return generals[groupPosition][childPosition];
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			LinearLayout ll = new LinearLayout(Activity_Faq.this);
			ll.setOrientation(0);
			TextView textView = getTextView();
			textView.setTextColor(Color.BLACK);
			textView.setText(getGroup(groupPosition).toString());
			ll.addView(textView);
			ll.setPadding(20, 10, 10, 10);
			return ll;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			LinearLayout ll = new LinearLayout(Activity_Faq.this);
			ll.setOrientation(0);
			TextView textView = getTextView();
			textView.setText(getChild(groupPosition, childPosition).toString());
			ll.addView(textView);
			return ll;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}

}
