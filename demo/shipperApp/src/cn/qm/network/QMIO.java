package cn.qm.network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import cn.qm.application.IApplication;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public final class QMIO {

	private static QMIO io;

	public static QMIO getInstance() {
		if (null == io) {
			return io = new QMIO();
		} else {
			return io;
		}
	}

	private final Handler mHandler = new Handler();
	private final Thread mUIThread = Thread.currentThread();
	private static Bitmap bitmap;

	/**
	 * 异步HTTP GET 方法
	 * 
	 * @param url
	 * @param mOnSuccessListener
	 */
	public void get(final String url, final OnResultListener onResultListener) {

		IApplication.getInstance().executorService.submit(new Runnable() {

			Response response = new Response();

			@Override
			public void run() {

				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpget = new HttpGet(url);
					HttpResponse httpResponse = httpClient.execute(httpget);
					response.code = httpResponse.getStatusLine().getStatusCode();
					if (response.code == HttpStatus.SC_OK) {
						response.result = EntityUtils.toString(httpResponse.getEntity());
					} else {
						response.errorMessage = "error Code :" + httpResponse.getStatusLine().getStatusCode();
					}
				} catch (Exception e) {
					response.errorMessage = e.getMessage();
				} finally {
					response.isSuccess = (null != response.result && null == response.errorMessage && response.code == 200) ? true : false;
					if (response.isSuccess) {
						if (Thread.currentThread() != mUIThread) {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									onResultListener.onResult(response);
								}
							});
						} else {
							onResultListener.onResult(response);
						}
					} else {
						onResultListener.onFailure();
					}

				}
			}
		});

	}

	/**
	 * 异步HTTP POST 方法 字段方式
	 * 
	 * @param url
	 * @param pairs
	 * @param mOnSuccessListener
	 */
	public void post(final String url, final List<BasicNameValuePair> pairs, final OnResultListener onResultListener) {

		IApplication.getInstance().executorService.submit(new Runnable() {

			Response response = new Response();

			@Override
			public void run() {
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
					HttpResponse httpResponse = httpClient.execute(httpPost);
					response.code = httpResponse.getStatusLine().getStatusCode();
					if (response.code == HttpStatus.SC_OK) {
						response.result = EntityUtils.toString(httpResponse.getEntity());
					} else {
						response.errorMessage = "error Code :" + httpResponse.getStatusLine().getStatusCode();
					}
				} catch (Exception e) {
					response.errorMessage = e.getMessage();
				} finally {
					response.isSuccess = (null != response.result && null == response.errorMessage && response.code == 200) ? true : false;
					if (response.isSuccess) {
						if (Thread.currentThread() != mUIThread) {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									onResultListener.onResult(response);
								}
							});
						} else {
							onResultListener.onResult(response);
						}
					} else {
						onResultListener.onFailure();
					}

				}
			}
		});
	}

	/**
	 * okHTTP POST 字符串方式 函数重载
	 * 
	 * @param url
	 * @param jsonObject
	 * @param onSuccessListener
	 */
	public void ok_post(final String url, final JSONObject jsonObject, final OnResultListener onSuccessListener) {

		IApplication.getInstance().executorService.submit(new Runnable() {

			Response response2 = new Response();

			@Override
			public void run() {
				OkHttpClient okHttpClient = new OkHttpClient();
				RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
				final okhttp3.Request request = new okhttp3.Request.Builder().url(url).post(requestBody).build();
				okhttp3.Response a;
				try {
					a = okHttpClient.newCall(request).execute();
					response2.result = a.body().string();
				} catch (IOException e) {
					e.printStackTrace();
					response2.errorMessage = e.getMessage();
				} finally {
					response2.isSuccess = (null != response2.result && null == response2.errorMessage) ? true : false;
					if (Thread.currentThread() != mUIThread) {
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								onSuccessListener.onResult(response2);
							}
						});
					} else {
						onSuccessListener.onResult(response2);
					}
				}
			}
		});
	}

	/**
	 * 异步HTTP POST JSON字符串方式 函数重载
	 * 
	 * @param url
	 * @param jsonObject
	 * @param onSuccessListener
	 */
	public void post(final String url, final String str, final OnResultListener onResultListener) {

		IApplication.getInstance().executorService.submit(new Runnable() {

			Response response = new Response();

			@Override
			public void run() {
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(url);
					StringEntity stringEntity = new StringEntity(str, "UTF_8");
					stringEntity.setContentType("application/json");
					stringEntity.setContentEncoding("UTF-8");
					httpPost.setEntity(stringEntity);
					HttpResponse httpResponse = httpClient.execute(httpPost);
					response.code = httpResponse.getStatusLine().getStatusCode();
					if (response.code == HttpStatus.SC_OK) {
						response.result = EntityUtils.toString(httpResponse.getEntity());
					} else {
						response.errorMessage = "error Code :" + httpResponse.getStatusLine().getStatusCode();
					}
				} catch (Exception e) {
					response.errorMessage = e.getMessage();
				} finally {
					response.isSuccess = (null != response.result && null == response.errorMessage && response.code == 200) ? true : false;
					if (response.isSuccess) {
						if (Thread.currentThread() != mUIThread) {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									onResultListener.onResult(response);
								}
							});
						} else {
							onResultListener.onResult(response);
						}
					} else {
						onResultListener.onFailure();
					}
				}
			}
		});
	}

	/**
	 * 异步HTTP POST 字符串方式 函数重载
	 * 
	 * @param url
	 * @param jsonObject
	 * @param onSuccessListener
	 */
	public void post(final String url, final JSONObject jsonObject, final OnResultListener onSuccessListener) {

		IApplication.getInstance().executorService.submit(new Runnable() {

			Response response = new Response();

			@Override
			public void run() {
				try {
					Log.e("post data", jsonObject.toString());
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(url);
					StringEntity stringEntity = new StringEntity(jsonObject.toString(), "UTF_8");
					// StringEntity stringEntity = new
					// StringEntity(jsonObject.toString());
					stringEntity.setContentType("application/json");
					stringEntity.setContentEncoding("UTF-8");
					httpPost.setEntity(stringEntity);
					HttpResponse httpResponse = httpClient.execute(httpPost);
					response.code = httpResponse.getStatusLine().getStatusCode();
					if (response.code == HttpStatus.SC_OK) {
						response.result = EntityUtils.toString(httpResponse.getEntity());
					} else {
						response.errorMessage = "error Code :" + httpResponse.getStatusLine().getStatusCode();
					}
				} catch (Exception e) {
					response.errorMessage = e.getMessage();
				} finally {
					response.isSuccess = (null != response.result && null == response.errorMessage && response.code == 200) ? true : false;
					if (response.isSuccess) {
						if (Thread.currentThread() != mUIThread) {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									onSuccessListener.onResult(response);
								}
							});
						} else {
							onSuccessListener.onResult(response);
						}
					} else {
						onSuccessListener.onFailure();
					}
				}
			}
		});
	}

	/**
	 * 同步HTTP POST请求
	 * 
	 * @param url
	 * @param jsonObject
	 * @return
	 */
	@SuppressWarnings("finally")
	public static Response post(final String url, final JSONObject jsonObject) {

		Response response = new Response();

		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			StringEntity stringEntity = new StringEntity(jsonObject.toString(), "UTF_8");
			stringEntity.setContentType("application/json");
			stringEntity.setContentEncoding("UTF-8");
			httpPost.setEntity(stringEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				response.isSuccess = true;
				response.result = EntityUtils.toString(httpResponse.getEntity());
			} else {
				response.errorMessage = "error Code :" + httpResponse.getStatusLine().getStatusCode();
			}
		} catch (Exception e) {
			response.errorMessage = e.getMessage();
		} finally {
			return response;
		}

	}

	/**
	 * 
	 * ImageView获取网络图片
	 * 
	 * @param url
	 * @return
	 */
	public void getNetBitMap(final ImageView iv, final String url) {

		bitmap = null;
		IApplication.getInstance().executorService.execute(new Runnable() {

			@Override
			public void run() {

				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpget = new HttpGet(url);
					HttpResponse httpResponse = httpClient.execute(httpget);
					if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						bitmap = BitmapFactory.decodeStream(httpResponse.getEntity().getContent());
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (Thread.currentThread() != mUIThread) {
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								iv.setImageBitmap(bitmap);
							}
						});
					} else {
						iv.setImageBitmap(bitmap);
					}
				}
			}
		});
	}

	/*
	 * 
	 * okDownLoad 下载apk
	 * 
	 */
	public void okDownLoad(final String url, final onDownloadProgress onDownloadProgress, final onDownloadSuccess downloadSuccess) {
		IApplication.getInstance().executorService.execute(new Runnable() {
			public void run() {
				final String appName = "shipper.apk";
				okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();
				OkHttpClient mOkHttpClient = new OkHttpClient();
				File file = null;
				try {
					okhttp3.Response response = mOkHttpClient.newCall(request).execute();
					InputStream is = null;
					byte[] buf = new byte[2048];
					int len = 0;
					FileOutputStream fos = null;
					final String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
					try {
						is = response.body().byteStream();
						long total = response.body().contentLength();
						file = new File(SDPath, appName);
						fos = new FileOutputStream(file);
						long sum = 0;
						while ((len = is.read(buf)) != -1) {
							fos.write(buf, 0, len);
							sum += len;
							int progress = (int) (sum * 1.0f / total * 100);
							onDownloadProgress.onProgress(progress);
						}
						fos.flush();
						Log.d("h_bl", "文件下载成功");
						Log.d("h_bl", "路径=========getAbsolutePath=======" + SDPath);
						Log.d("h_bl", "路径===========File.separator全=====" + Environment.getExternalStorageDirectory() + File.separator);
						Log.d("h_bl", "路径===========getExternalStorageDirectory=====" + Environment.getExternalStorageDirectory());
						Log.d("h_bl", "路径===========File.separator=====" + File.separator);
						is.close();
						fos.close();
						if (Thread.currentThread() != mUIThread) {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									downloadSuccess.onDownloadSuccess(new File(SDPath, appName));
								}
							});
						} else {
							downloadSuccess.onDownloadSuccess(new File(SDPath, appName));
						}
					} catch (Exception e) {
						Log.d("h_bl", "文件下载失败");
						Log.d("h_bl", e.getMessage());
						Toast.makeText(IApplication.getInstance().activitys.peek().get(), "文件下载失败:" + e.getMessage(), 0).show();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 异步HTTPS POST JSON字符串
	 * 
	 * @param url
	 * @param jsonObject
	 * @param onSuccessListener
	 */
	public void doHttpsPost(final String url, final JSONObject jsonObject, final OnResultListener onSuccessListener) {

		IApplication.getInstance().executorService.submit(new Runnable() {

			Response res = new Response();

			@Override
			public void run() {
				try {
					HttpParams httpParameters = new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
					HttpConnectionParams.setSoTimeout(httpParameters, 3000);
					// 获取HttpClient对象 （认证）
					HttpClient hc = initHttpClient(httpParameters);
					HttpPost post = new HttpPost(url);
					post.addHeader("Content-Type", "application/json;charset=utf-8");
					post.addHeader("Accept", "application/json");
					StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
					post.setEntity(entity);
					post.setParams(httpParameters);
					HttpResponse response = hc.execute(post);
					int sCode = response.getStatusLine().getStatusCode();
					if (sCode == HttpStatus.SC_OK) {
						res.result = EntityUtils.toString(response.getEntity());
					}
				} catch (Exception e) {
					res.errorMessage = e.getMessage();
				} finally {
					res.isSuccess = (null != res.result && null == res.errorMessage && res.code == 200) ? true : false;
					if (res.isSuccess) {
						if (Thread.currentThread() != mUIThread) {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									onSuccessListener.onResult(res);
								}
							});
						} else {
							onSuccessListener.onResult(res);
						}
					} else {
						onSuccessListener.onFailure();
					}

				}
			}
		});
	}

	/**
	 * initHttpClient
	 * 
	 * @param params
	 * @return
	 */
	public static synchronized HttpClient initHttpClient(HttpParams params) {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory sf = new SSLSocketFactoryImp(trustStore);
			// 允许所有主机的验证
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			// 设置http和https支持
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));
			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			e.printStackTrace();
			return new DefaultHttpClient(params);
		}
	}
}
