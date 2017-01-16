/**
 * @author 郭海波
 * @ClassName Bus
 * @Version 版本
 * @ModifiedBy 修改人
 * @Copyright 启明电子信息
 * @Create 2017年1月9日 下午2:11:00
 * @Modify 修改时间
 */
package cn.qm.bus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cn.qm.annotation.Subscribe;
import cn.qm.exception.CanNotRegisterMoreThanOncetException;
import cn.qm.exception.EventNotRegisterException;

public class Bus {

	private static Bus bus;

	/** 线程安全的单例 */
	public static Bus getDefault() {
		if (null == bus) {
			synchronized (Bus.class) {
				if (null == bus) {
					return bus = new Bus();
				}
			}
		}
		return bus;
	}

	private HashMap<Class<?>[], ArrayList<HashMap<Object, Method>>> map = new HashMap<Class<?>[], ArrayList<HashMap<Object, Method>>>();

	public void register(Object obj) {

		if (isRegistered(obj)) {
			throw new CanNotRegisterMoreThanOncetException("Can Not Register More Than Once Exception");
		}

		if (map.isEmpty()) {
			map.putAll(findSubscribethod(obj));
			return;
		}
		HashMap<Class<?>[], ArrayList<HashMap<Object, Method>>> map_temp = findSubscribethod(obj);
		for (Entry<Class<?>[], ArrayList<HashMap<Object, Method>>> entry1 : map_temp.entrySet()) {
			Class<?>[] key_temp = entry1.getKey();
			ArrayList<HashMap<Object, Method>> value_temp = entry1.getValue();
			for (Entry<Class<?>[], ArrayList<HashMap<Object, Method>>> entry2 : map.entrySet()) {
				Class<?>[] key = entry2.getKey();
				ArrayList<HashMap<Object, Method>> value = entry2.getValue();
				if (isEqual(key_temp, key)) {
					for (int i = 0; i < value_temp.size(); i++) {
						value.add(value_temp.get(i));
					}
				}
			}
		}
	}

	public void unregister(Object obj) {

		if (!isRegistered(obj)) {
			throw new EventNotRegisterException("Event Not Register Exception");
		}

		for (Entry<Class<?>[], ArrayList<HashMap<Object, Method>>> entry2 : map.entrySet()) {
			Class<?>[] key = entry2.getKey();
			ArrayList<HashMap<Object, Method>> value = entry2.getValue();
			Iterator<HashMap<Object, Method>> iter = value.iterator();
			while (iter.hasNext()) {
				HashMap<Object, Method> hashMap = iter.next();
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

	private HashMap<Class<?>[], ArrayList<HashMap<Object, Method>>> findSubscribethod(Object obj) {
		HashMap<Class<?>[], ArrayList<HashMap<Object, Method>>> res = new HashMap<Class<?>[], ArrayList<HashMap<Object, Method>>>();
		Class<?> clz = obj.getClass();
		Method[] methods = clz.getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(Subscribe.class)) {
				ArrayList<HashMap<Object, Method>> m = res.get(method.getParameterTypes());
				if (null == m) {
					m = new ArrayList<HashMap<Object, Method>>();
				}
				HashMap<Object, Method> hashMap = new HashMap<Object, Method>();
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

	public boolean isRegistered(Object obj) {
		for (Entry<Class<?>[], ArrayList<HashMap<Object, Method>>> entry2 : map.entrySet()) {
			Class<?>[] key = entry2.getKey();
			ArrayList<HashMap<Object, Method>> value = entry2.getValue();
			for (int i = 0; i < value.size(); i++) {
				HashMap<Object, Method> hashMap = value.get(i);
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
		for (Entry<Class<?>[], ArrayList<HashMap<Object, Method>>> entry1 : map.entrySet()) {
			Class<?>[] para1 = entry1.getKey();
			if (isEqual(para, para1)) {
				ArrayList<HashMap<Object, Method>> m = map.get(para1);
				for (int i = 0; i < m.size(); i++) {
					HashMap<Object, Method> map = m.get(i);
					for (Map.Entry<Object, Method> entry : map.entrySet()) {
						try {
							System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
							Object o = entry.getKey();
							Method met = entry.getValue();
							met.setAccessible(true);
							met.invoke(o, obj);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
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
