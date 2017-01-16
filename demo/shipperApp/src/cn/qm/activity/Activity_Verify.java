/**
 * @author 郭海波
 * @ClassName Activity_Verify
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月7日 下午1:29:22
 * @Modify 修改时间
 */
package cn.qm.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.qm.annotation.OnClick;
import cn.qm.annotation.ViewId;
import cn.qm.shipperapp.R;
import cn.qm.template.BaseFragmentActivity;
import cn.qm.until.PhotoUtils;
import cn.qm.widget.BottomMenuDialog;

@ViewId(R.layout.activity_verify)
public class Activity_Verify extends BaseFragmentActivity {

	@ViewId(R.id.next)
	private Button next;

	@ViewId(R.id.done)
	private Button done;

	@ViewId(R.id.back)
	private TextView back;

	@ViewId(R.id.id_photo)
	private ImageView id_photo;

	@ViewId(R.id.head)
	private ImageView head;

	@ViewId(R.id.information)
	private LinearLayout information;

	@ViewId(R.id.enterprise)
	private LinearLayout enterprise;

	private BottomMenuDialog dialog;
	private PhotoUtils photoUtils;
	static private final int REQUEST_CODE_ASK_PERMISSIONS = 101;

	@OnClick(R.id.next)
	private void next(View v) {
		information.setVisibility(View.GONE);
		enterprise.setVisibility(View.VISIBLE);
	}

	@OnClick(R.id.done)
	private void done(View v) {

	}

	@OnClick(R.id.back)
	private void back(View v) {
		finish();
	}

	@OnClick(R.id.id_photo)
	private void photo(View v) {
		showPhotoDialog();
	}

	@OnClick(R.id.head)
	private void head(View v) {
		showPhotoDialog();
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setPortraitChangeListener();
	}

	private void setPortraitChangeListener() {
		photoUtils = new PhotoUtils(new PhotoUtils.OnPhotoResultListener() {
			@Override
			public void onPhotoResult(Uri uri) {
				if (uri != null && !TextUtils.isEmpty(uri.getPath())) {
					Log.i("debug", uri.getPath());
				}
			}

			@Override
			public void onPhotoCancel() {
				Log.i("debug", "onPhotoCancel");
			}
		});
	}

	private void showPhotoDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}

		dialog = new BottomMenuDialog(Activity_Verify.this);
		dialog.setConfirmListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
				if (Build.VERSION.SDK_INT >= 23) {
					int checkPermission = ContextCompat.checkSelfPermission(Activity_Verify.this, Manifest.permission.CAMERA);
					if (checkPermission != PackageManager.PERMISSION_GRANTED) {
						if (ActivityCompat.shouldShowRequestPermissionRationale(Activity_Verify.this, Manifest.permission.CAMERA)) {
							ActivityCompat.requestPermissions(null, new String[] { Manifest.permission.CAMERA }, REQUEST_CODE_ASK_PERMISSIONS);
						} else {
							new AlertDialog.Builder(Activity_Verify.this).setMessage("您需要在设置里打开相机权限。").setPositiveButton("确认", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									ActivityCompat.requestPermissions(Activity_Verify.this, new String[] { Manifest.permission.CAMERA }, REQUEST_CODE_ASK_PERMISSIONS);
								}
							}).setNegativeButton("取消", null).create().show();
						}
						return;
					}
				}
				photoUtils.takePicture(Activity_Verify.this);
			}
		});
		dialog.setMiddleListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
				photoUtils.selectPicture(Activity_Verify.this);
			}
		});
		dialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PhotoUtils.INTENT_CROP:
		case PhotoUtils.INTENT_TAKE:
		case PhotoUtils.INTENT_SELECT:
			photoUtils.onActivityResult(Activity_Verify.this, requestCode, resultCode, data);
			break;
		}
	}

}
