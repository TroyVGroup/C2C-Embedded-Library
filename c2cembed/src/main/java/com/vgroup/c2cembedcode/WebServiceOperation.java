package com.vgroup.c2cembedcode;

import android.text.TextUtils;
import android.util.Log;

//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request.Method;
//import com.android.volley.Response;
//import com.android.volley.RetryPolicy;
//import com.android.volley.VolleyError;
//import com.android.volley.VolleyLog;
//import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public abstract class WebServiceOperation<T>{}
//implements Response.Listener<T>,Response.ErrorListener{
//	private static final String TAG = "VolleyAddToRequest";
//	protected GsonRequest mWebRequest;
//
//	public WebServiceOperation(int method, String url,Class<T> clazz ,Map<String, String> headers,String body) {
//
//		    if(headers!=null){
//			   headers.putAll(getHeaders());
//		   }else{
//				headers = new HashMap<String, String>();
//		   }
//		   mWebRequest = new GsonRequest(method, Constants.BASE_URL + url,clazz, headers,body!=null ? body.getBytes():null,this,this);
//	}
//
//	public WebServiceOperation(String url,Class<T> clazz, Map<String, String> headers,String body){
//	       this(Method.GET,url,clazz,headers,body);
//	}
//
//	public WebServiceOperation(int method,String url,String body) {
//         this(method,url,null,null,body);
//
//	}
//
//     public WebServiceOperation(int method,String url,Map<String, String> headers){
//         this(method,url,null,headers,null);
//     }
//
//     public WebServiceOperation(String url,Class<T> clazz) {
//         this(Method.GET,url,clazz,null,null);
//     }
//
//     public WebServiceOperation(String url,Class<T> clazz,Map<String, String> headers){
//         this(Method.GET,url,clazz,headers,null);
//     }
//
//     public Map<String, String> getHeaders(){
//    	 Map<String, String> headers = new HashMap<String, String>();
//    	 headers.put("Content-Type","application/json");
//		 headers.put("Accept", "application/json");
//
// 	     return headers;
//     }
//
//
//	   public void addToRequestQueue(String tag) {
//		       // set the default tag if tag is empty
//			   mWebRequest.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
//
//		       VolleyLog.d("Adding request to queue: %s", mWebRequest.getUrl());
//
//               int socketTimeout = 180000;//30 seconds - change to what you want
//               RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//               mWebRequest.setRetryPolicy(policy);
//
//			   AppControllerC2C.getInstance().getRequestQueue().add(mWebRequest);
//
//	   }
//
//
//	   public void addToRequestQueue() {
//	       // set the default tag if tag is empty
//		  addToRequestQueue(TAG);
//	   }
//
//	   /**
//	    * Cancels all pending requests by the specified TAG, it is important
//	    * to specify a TAG so that the pending/ongoing requests can be cancelled.
//	    *
//	    * @param tag
//	    */
//	   public void cancelPendingRequests(Object tag) {
//	       if (AppControllerC2C.getInstance().getRequestQueue() != null) {
//			   AppControllerC2C.getInstance().getRequestQueue().cancelAll(tag);
//	       }
//	   }
//
//	@Override
//	public void onResponse(T response) {
//		onServerResult(response);
//	}
//
//
//	@Override
//	public void onErrorResponse(VolleyError error) {
//		if(error.networkResponse!=null){
//			String jsonString = null;
//            try {
//
//				jsonString = new String(error.networkResponse.data,HttpHeaderParser.parseCharset(error.networkResponse.headers));
//				Log.d("error",jsonString);
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//
//        }
//
//
//		onErrorResult(error);
//	}
//
//	public abstract void onServerResult(T response);
//	public abstract void onErrorResult(VolleyError error);
//
//
//}
