package cn.qm.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import cn.qm.shipperapp.R;

public class ConfirmLogoutDialog extends AlertDialog implements android.view.View.OnClickListener {

	/**
	 * 确认删除事件
	 */
	private OnConfirmCheckedListener confirmCheckedListener;

	/**
	 * 确认按钮
	 */
	private TextView sure;

	/**
	 * 取消按钮
	 */
	private TextView cancel;

	/**
	 * 设置确认事件
	 * 
	 * @param confirmCheckedListener
	 */
	public void setConfirmCheckedListener(OnConfirmCheckedListener confirmCheckedListener) {
		this.confirmCheckedListener = confirmCheckedListener;
	}

	/**
	 * 事件接口
	 * 
	 * @author QM
	 *
	 */
	public interface OnConfirmCheckedListener {
		public void onConfirmChecked();
	}

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	public ConfirmLogoutDialog(Context context) {
		super(context);
	}

	/**
	 * 构造方法
	 * 
	 * @param context
	 * @param confirmCheckedListener
	 */
	public ConfirmLogoutDialog(Context context, OnConfirmCheckedListener confirmCheckedListener) {
		super(context);
		this.confirmCheckedListener = confirmCheckedListener;
	}

	/**
	 * Dialog的OnCreate事件
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_logout_confirm);
		sure = (TextView) findViewById(R.id.sure);
		cancel = (TextView) findViewById(R.id.cancel);
		setCanceledOnTouchOutside(false);
		sure.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	/**
	 * 点击事件分发
	 * 
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sure:
			confirmCheckedListener.onConfirmChecked();
			dismiss();
			break;
		case R.id.cancel:
			dismiss();
			break;
		default:
			break;
		}
	}

}
