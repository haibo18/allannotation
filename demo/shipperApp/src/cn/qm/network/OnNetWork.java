package cn.qm.network;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 封装请求回调接口
 * 
 * @author QM
 *
 */
public interface OnNetWork {
	
	public JSONObject setPara() throws JSONException;

	public void getResult(JSONObject json) throws JSONException;
	
}