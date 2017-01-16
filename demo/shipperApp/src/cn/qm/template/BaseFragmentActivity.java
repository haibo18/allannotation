package cn.qm.template;

import java.lang.annotation.Annotation;
/**
 * @author 郭海波
 * @ClassName BaseFragmentActivity
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月9日 下午12:27:31
 * @Modify 修改时间
 */
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import cn.qm.annotation.OnClick;
import cn.qm.annotation.ViewId;
import cn.qm.application.IApplication;
import cn.qm.bus.Bus2;
import cn.qm.exception.AnnotationDoNotExistException;

/**
 * 基础FragmentActivity模版
 * 
 * @author QM
 * 
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements Base {

	private Base base = this;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		IApplication.getInstance().activitys.add(new WeakReference(this));
		Log.e("DEBUG", this.toString());
		if (isContentViewIdAnnotationPresent()) {
			setContentView(getContentViewId());
			viewIdAllInject();
			methodAllInject();
			eventbus();
		} else {
			throw new AnnotationDoNotExistException("注解未定义");
		}
	}

	@SuppressLint("UseSparseArrays")
	@Override
	public void methodAllInject() {
		Class<?> clazz = this.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				int value;
				try {
					Class<?> annoType = annotation.annotationType();
					if (annoType.isAnnotationPresent(cn.qm.annotation.Event.class)) {
						Method valueMethod = annoType.getDeclaredMethod("value");
						value = (Integer) valueMethod.invoke(annotation);
						Class<?> listenerType = null;
						String listenerSetter = null;
						String methodName = null;
						cn.qm.annotation.Event event = annoType.getAnnotation(cn.qm.annotation.Event.class);
						listenerType = event.listenerType();
						listenerSetter = event.listenerSetter();
						methodName = event.methodName();
						View spec = this.findViewById(value);
						Method setter = spec.getClass().getMethod(listenerSetter, listenerType);
						InvocationHandler handler = new DynamicProxy(this, method, methodName);
						Object obj1 = Proxy.newProxyInstance(handler.getClass().getClassLoader(), new Class[] { listenerType }, handler);
						setter.invoke(spec, obj1);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	@Override
	public boolean isContentViewIdAnnotationPresent() {
		Class<?> clazz = this.getClass();
		return clazz.isAnnotationPresent(ViewId.class);
	}

	@Override
	public int getContentViewId() {
		Class<?> clazz = this.getClass();
		ViewId activityInject = clazz.getAnnotation(ViewId.class);
		return activityInject.value();
	}

	@Override
	public void viewIdAllInject() {
		try {
			HashMap<Integer, Method> hashmap = new HashMap<Integer, Method>();
			Class<?> clazz = this.getClass();
			Field[] fields = clazz.getDeclaredFields();// 获得Activity中声明的字段
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(OnClick.class)) {
					OnClick onClick = method.getAnnotation(OnClick.class);
					int methodAnnotationId = onClick.value();
					hashmap.put(methodAnnotationId, method);
				}
			}
			for (Field field : fields) {
				// 查看这个字段是否有我们自定义的注解类标志的
				if (View.class.isAssignableFrom(field.getType()) && field.isAnnotationPresent(ViewId.class)) {
					ViewId inject = field.getAnnotation(ViewId.class);
					final int id = inject.value();
					if (id <= 0) {
						return;
					}
					field.setAccessible(true);
					Class<?> fieldClass = field.getType();
					Object obj = fieldClass.cast(this.findViewById(id));
					field.set(this, obj);// 给我们要找的字段设置值
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	private class DynamicProxy implements InvocationHandler {

		private Object base;
		private Method met;
		private String methodNanme;

		public DynamicProxy(Base base, Method met, String methodName) {
			this.base = base;
			this.met = met;
			this.methodNanme = methodName;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

			try {
				if (met != null && method.getName().equals(methodNanme)) {
					met.setAccessible(true);
					return met.invoke(base, args);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}

	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		Log.e("DEBUG", this.getClass().getName() + "--------------->finalize");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void eventbus() {
		if (isEventBusAnnotationPresent() && !Bus2.getDefault().isRegistered(this)) {
			Bus2.getDefault().register(this);
		}
	}

	@Override
	public void uneventbus() {
		if (isEventBusAnnotationPresent() && Bus2.getDefault().isRegistered(this)) {
			Bus2.getDefault().unregister(this);
		}
	}

	@Override
	public boolean isEventBusAnnotationPresent() {
		Class<?> clazz = this.getClass();
		return clazz.isAnnotationPresent(cn.qm.annotation.Bus.class);
	}

}
