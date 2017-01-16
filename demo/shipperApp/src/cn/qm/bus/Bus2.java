/**
 * @author 郭海波
 * @ClassName Bus2
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月9日 下午2:11:00
 * @Modify 修改时间
 */
package cn.qm.bus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

import android.os.Handler;
import android.util.Log;
import cn.qm.annotation.Subscribe;
import cn.qm.application.IApplication;
import cn.qm.exception.CanNotRegisterMoreThanOncetException;
import cn.qm.exception.EventNotRegisterException;

public class Bus2 {

	private static Bus2 bus;
	public static final int UI_THREAD = 0;
	public static final int WORKER_THREAD = 1;
	public static final int POSTING_THREAD = 2;
	private Handler handler = new Handler();
	/** 线程安全的单例 */
	public static Bus2 getDefault() {
		if (null == bus) {
			synchronized (Bus2.class) {
				if (null == bus) {
					return bus = new Bus2();
				}
			}
		}
		return bus;
	}

	private ConcurrentHashMap<Class<?>[], CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>>> map = new ConcurrentHashMap<Class<?>[], CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>>>();

	public synchronized void register(Object obj) {

		if (isRegistered(obj)) {
			throw new CanNotRegisterMoreThanOncetException("Can Not Register More Than Once Exception");
		}

		if (map.isEmpty()) {
			map.putAll(findSubscribethod(obj));
			return;
		}
		ConcurrentHashMap<Class<?>[], CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>>> map_temp = findSubscribethod(obj);
		for (Entry<Class<?>[], CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>>> entry1 : map_temp.entrySet()) {
			Class<?>[] key_temp = entry1.getKey();
			CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>> value_temp = entry1.getValue();
			for (Entry<Class<?>[], CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>>> entry2 : map.entrySet()) {
				Class<?>[] key = entry2.getKey();
				CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>> value = entry2.getValue();
				if (isEqual(key_temp, key)) {
					for (int i = 0; i < value_temp.size(); i++) {
						value.add(value_temp.get(i));
					}
				}
			}
		}
	}

	public synchronized void unregister(Object obj) {

		if (!isRegistered(obj)) {
			throw new EventNotRegisterException("Event Not Register Exception");
		}

		for (Entry<Class<?>[], CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>>> entry2 : map.entrySet()) {
			Class<?>[] key = entry2.getKey();
			CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>> value = entry2.getValue();
			Iterator<ConcurrentHashMap<Object, Method>> iter = value.iterator();
			while (iter.hasNext()) {
				ConcurrentHashMap<Object, Method> hashMap = iter.next();
				for (Entry<Object, Method> entry : hashMap.entrySet()) {
					if (entry.getKey().equals(obj)) {
						iter.remove();
					}
				}
			}
			if (value.size() == 0) {
				map.remove(key);
			}
		}
	}

	private ConcurrentHashMap<Class<?>[], CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>>> findSubscribethod(Object obj) {
		ConcurrentHashMap<Class<?>[], CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>>> res = new ConcurrentHashMap<Class<?>[], CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>>>();
		Class<?> clz = obj.getClass();
		Method[] methods = clz.getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(Subscribe.class)) {
				CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>> m = res.get(method.getParameterTypes());
				if (null == m) {
					m = new CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>>();
				}
				ConcurrentHashMap<Object, Method> hashMap = new ConcurrentHashMap<Object, Method>();
				hashMap.put(obj, method);
				m.add(hashMap);
				res.put(method.getParameterTypes(), m);
			}
		}
		return res;
	}

	private boolean isEqual(Class<?>[] para, Class<?>[] para1) {
		if (para.length == para1.length) {
			for (int i = 0; i < para.length; i++) {
				if (para[i] != para1[i]) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public synchronized boolean isRegistered(Object obj) {
		for (Entry<Class<?>[], CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>>> entry2 : map.entrySet()) {
			Class<?>[] key = entry2.getKey();
			CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>> value = entry2.getValue();
			for (int i = 0; i < value.size(); i++) {
				ConcurrentHashMap<Object, Method> hashMap = value.get(i);
				for (Entry<Object, Method> entry : hashMap.entrySet()) {
					if (entry.getKey().equals(obj)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void post(Object... obj) {
		Class<?>[] para = getParameterTypes(obj);
		for (Entry<Class<?>[], CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>>> entry1 : map.entrySet()) {
			Class<?>[] para1 = entry1.getKey();
			if (isEqual(para, para1)) {
				CopyOnWriteArrayList<ConcurrentHashMap<Object, Method>> m = map.get(para1);
				for (int i = 0; i < m.size(); i++) {
					ConcurrentHashMap<Object, Method> map = m.get(i);
					for (Map.Entry<Object, Method> entry : map.entrySet()) {
						try {
							Object o = entry.getKey();
							Method met = entry.getValue();
							int value = met.getAnnotation(Subscribe.class).value();
							if (value == WORKER_THREAD) {
								InvocationHandler handler = new DynamicProxy(met, o, obj);
								Object obj1 = Proxy.newProxyInstance(handler.getClass().getClassLoader(), new Class[] { Runnable.class }, handler);
								Executor executor = IApplication.getInstance().executorService;
								Method met1 = executor.getClass().getMethod("submit", Runnable.class);
								met1.setAccessible(true);
								met1.invoke(executor, obj1);
							} else if (value == POSTING_THREAD) {
								met.setAccessible(true);
								met.invoke(o, obj);
							} else {
								InvocationHandler handl = new DynamicProxy2(met, o, obj);
								Object obj1 = Proxy.newProxyInstance(handl.getClass().getClassLoader(), new Class[] { Runnable.class }, handl);
								Executor executor = IApplication.getInstance().executorService;
								Method met2 = handler.getClass().getMethod("post", Runnable.class);
								met2.setAccessible(true);
								met2.invoke(handler, obj1);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

	}

	private class DynamicProxy implements InvocationHandler {

		private Object obj;
		private Method met;
		private Object[] para;

		/**
		 * @param met
		 */
		public DynamicProxy(Method met, Object obj, Object[] para) {
			super();
			this.met = met;
			this.obj = obj;
			this.para = para;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Log.i("debug", "==================" + method.getName());
			if (method.getName().equals("run")) {
				met.setAccessible(true);
				return met.invoke(obj, para);
			}
			return null;
		}

	}
	
	private class DynamicProxy2 implements InvocationHandler {
		
		private Object obj;
		private Method met;
		private Object[] para;
		
		/**
		 * @param met
		 */
		public DynamicProxy2(Method met, Object obj, Object[] para) {
			super();
			this.met = met;
			this.obj = obj;
			this.para = para;
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (method.getName().equals("run")) {
				met.setAccessible(true);
				return met.invoke(obj, para);
			}
			return null;
		}
		
	}

	private Class[] getParameterTypes(Object... objs) {
		ArrayList<Class<?>> clz = new ArrayList<Class<?>>();
		for (Object obj : objs) {
			clz.add(obj.getClass());
		}
		Class[] res = new Class[clz.size()];
		for (int i = 0; i < res.length; i++) {
			res[i] = clz.get(i);
		}
		return res;
	}

}
