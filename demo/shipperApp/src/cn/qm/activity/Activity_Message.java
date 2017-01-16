/**
 * @author 郭海波
 * @ClassName Activity_Message
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月16日 下午12:04:57
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

@ViewId(R.layout.sub_fragment_message)
public class Activity_Message extends BaseFragmentActivity {

	@ViewId(R.id.exListView)
	private ExpandableListView exlistView;

	@ViewId(R.id.back)
	private TextView back;

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
		private String[] generalsTypes = new String[] { "消息1", "消息2", "消息3" };
		// 子视图显示文字
		private String[][] generals = new String[][] { { "消息体1", "消息体2", "消息体3", "消息体4", "消息体5", "消息体6" }, { "消息体1", "消息体2", "消息体3", "消息体4", "消息体5", "消息体6" }, { "消息体1", "消息体2", "消息体3", "消息体4", "消息体5", "消息体6" }

		};

		// 自己定义一个获得textview的方法
		@SuppressWarnings("deprecation")
		TextView getTextView() {
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 100);
			TextView textView = new TextView(Activity_Message.this);
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
			LinearLayout ll = new LinearLayout(Activity_Message.this);
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
			LinearLayout ll = new LinearLayout(Activity_Message.this);
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
