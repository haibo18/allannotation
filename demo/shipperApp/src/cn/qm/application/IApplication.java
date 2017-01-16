/**
 * @author 郭海波
 * @ClassName IApplication
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 公司名称 
 */
package cn.qm.application;

import java.lang.ref.WeakReference;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.Application;

public class IApplication extends Application {

	private static IApplication app;
	public Stack<WeakReference<Activity>> activitys = new Stack<WeakReference<Activity>>();
	public ExecutorService executorService = Executors.newCachedThreadPool();

	public static IApplication getInstance() {
		if (null == app) {
			return app = new IApplication();
		} else {
			return app;
		}
	}

}
