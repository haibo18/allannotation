package cn.qm.until;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 通用工具类
 * 
 * @author QM
 * 
 */
public final class Utils {

	public static final class density {

		/**
		 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
		 * 
		 * @param context
		 * @param dpValue
		 * @return
		 */
		public static int dip2px(Context context, float dpValue) {
			final float scale = context.getResources().getDisplayMetrics().density;
			return (int) (dpValue * scale + 0.5f);
		}

		/**
		 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
		 * 
		 * @param context
		 * @param pxValue
		 * @return
		 */
		public static int px2dip(Context context, float pxValue) {
			final float scale = context.getResources().getDisplayMetrics().density;
			return (int) (pxValue / scale + 0.5f);
		}
	}

	public final static class tools {
		/**
		 * 注册所有点击监听器
		 * 
		 * @param obj
		 * @param args
		 * @return
		 */
		public static boolean registerAllOnClickListener(Object obj, int... args) {

			if (obj instanceof Activity) {
				for (int arg : args) {
					((Activity) obj).findViewById(arg).setOnClickListener((OnClickListener) obj);
				}
			} else if (obj instanceof View) {
				for (int arg : args) {
					((View) obj).findViewById(arg).setOnClickListener((OnClickListener) (((View) obj).getTag()));
				}
			}
			return true;
		}

		/**
		 * 判断网络是否联通
		 * 
		 * @param context
		 * @return
		 */
		public static boolean isConnected(Context context) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

			if (connectivityManager == null) {
				return false;
			} else {
				// 获取NetworkInfo对象
				NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

				if (networkInfo != null && networkInfo.length > 0) {
					for (int i = 0; i < networkInfo.length; i++) {
						System.out.println(i + "===状态===" + networkInfo[i].getState());
						System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
						// 判断当前网络状态是否为连接状态
						if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}
					}
				}
			}
			return false;
		}

		/**
		 * @Title: getCurrentMethod @Description:
		 * TODO(返回当前方法的方法对象) @param @return 设定文件 @return Method 返回类型 @throws
		 */
		public static Method getCurrentMethod() {

			StackTraceElement[] yste = Thread.currentThread().getStackTrace();
			if (yste.length < 2) {
				return null;
			}
			/** getMethodName **/
			String str = "";
			for (int i = 0; i < yste.length; i++) {
				if (yste[i].getMethodName().equals("getCurrentMethod")) {
					Class<?> cC = null;
					try {
						cC = Class.forName(yste[i + 1].getClassName());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					Method[] ym = cC.getMethods();
					str = yste[i + 1].toString();
					str = str.substring(0, str.lastIndexOf('('));

					for (int j = 0; j < ym.length; j++) {
						if (str.endsWith(ym[j].getName())) {
							return ym[j];
						}
					}
				}
			}
			return null;
		}
		
		/* 
		 * 
		 * 安装本地APK程序
		 * 
		 *  */
		public static void openFile(File file,Context context) {
			Log.e("OpenFile", file.getName());
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
			context.startActivity(intent);
		}
		
	}

	public final static class verify {
		/**
		 * 验证非空字符串
		 * 
		 * @param s
		 * @return
		 */
		public static boolean isEmptyOrNullString(String s) {
			return (null == s) || (s.trim().length() == 0);
		}
	}

	public final static class preference {

		/**
		 * 写安卓配置文件 sharepreference
		 * 
		 * @param c
		 * @param preferenceName
		 * @param key
		 * @param para
		 */
		@SuppressLint("NewApi")
		public static void write(Context c, String preferenceName, String key, Object para) {
			SharedPreferences preferences = c.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = preferences.edit();
			if (para instanceof Integer) {
				editor.putInt(key, (Integer) para);
			} else if (para instanceof Boolean) {
				editor.putBoolean(key, (Boolean) para);
			} else if (para instanceof Float) {
				editor.putFloat(key, (Float) para);
			} else if (para instanceof Long) {
				editor.putLong(key, (Long) para);
			} else if (para instanceof String) {
				editor.putString(key, (String) para);
			} else if (para instanceof Set) {
				editor.putStringSet(key, (Set<String>) para);
			}
			editor.commit();
		}

		/**
		 * 读安卓配置文件 sharepreference
		 * 
		 * @param c
		 * @param preferenceName
		 * @param key
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static <E> E read(Context c, String preferenceName, String key) {
			SharedPreferences preferences = c.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
			Map<String, ?> map = preferences.getAll();
			return (E) map.get(key);
		}

	}

	public final static class structure {

		/**
		 * 用泛型的方式新建一个arraylist
		 * 
		 * @return
		 */
		public static <E> ArrayList<E> newArrayList() {
			return new ArrayList<E>();
		}
	}

	public final static class Dateutil {
		/**
		 * 取得当前日期是多少周
		 * 
		 * @param date
		 * @return
		 */
		public static int getWeekOfYear(Date date) {
			Calendar c = new GregorianCalendar();
			c.setFirstDayOfWeek(Calendar.MONDAY);
			c.setMinimalDaysInFirstWeek(7);
			c.setTime(date);
			return c.get(Calendar.WEEK_OF_YEAR);
		}

		/**
		 * 得到某一年周的总数
		 * 
		 * @param year
		 * @return
		 */
		public static int getMaxWeekNumOfYear(int year) {
			Calendar c = new GregorianCalendar();
			c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
			return getWeekOfYear(c.getTime());
		}

		/**
		 * 得到某年某周的第一天
		 * 
		 * @param year
		 * @param week
		 * @return
		 */
		public static Date getFirstDayOfWeek(int year, int week) {
			Calendar c = new GregorianCalendar();
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, Calendar.JANUARY);
			c.set(Calendar.DATE, 1);
			Calendar cal = (GregorianCalendar) c.clone();
			cal.add(Calendar.DATE, week * 7);
			return getFirstDayOfWeek(cal.getTime());
		}

		/**
		 * 得到某年某周的最后一天
		 * 
		 * @param year
		 * @param week
		 * @return
		 */
		public static Date getLastDayOfWeek(int year, int week) {
			Calendar c = new GregorianCalendar();
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, Calendar.JANUARY);
			c.set(Calendar.DATE, 1);
			Calendar cal = (GregorianCalendar) c.clone();
			cal.add(Calendar.DATE, week * 7);
			return getLastDayOfWeek(cal.getTime());
		}

		/**
		 * 取得指定日期所在周的第一天
		 * 
		 * @param date
		 * @return
		 */
		public static Date getFirstDayOfWeek(Date date) {
			Calendar c = new GregorianCalendar();
			c.setFirstDayOfWeek(Calendar.MONDAY);
			c.setTime(date);
			c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
			return c.getTime();
		}

		/**
		 * 取得指定日期所在周的最后一天
		 * 
		 * @param date
		 * @return
		 */
		public static Date getLastDayOfWeek(Date date) {
			Calendar c = new GregorianCalendar();
			c.setFirstDayOfWeek(Calendar.MONDAY);
			c.setTime(date);
			c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
			return c.getTime();
		}

		/**
		 * 取得当前日期所在周的第一天
		 * 
		 * @param date
		 * @return
		 */
		public static Date getFirstDayOfWeek() {
			Calendar c = new GregorianCalendar();
			c.setFirstDayOfWeek(Calendar.MONDAY);
			c.setTime(new Date());
			c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
			return c.getTime();
		}

		/**
		 * 取得当前日期所在周的最后一天
		 * 
		 * @param date
		 * @return
		 */
		public static Date getLastDayOfWeek() {
			Calendar c = new GregorianCalendar();
			c.setFirstDayOfWeek(Calendar.MONDAY);
			c.setTime(new Date());
			c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
			return c.getTime();
		}
	}

}
