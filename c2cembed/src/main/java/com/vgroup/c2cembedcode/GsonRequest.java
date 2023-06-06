/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vgroup.c2cembedcode;

//import com.android.volley.AuthFailureError;
//import com.android.volley.NetworkResponse;
//import com.android.volley.ParseError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.Response.ErrorListener;
//import com.android.volley.Response.Listener;
//import com.android.volley.toolbox.HttpHeaderParser;
//import com.google.gson.Gson;
//import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * A canned request for retrieving the response body at a given URL as a String.
 * @param <T>
 */
public class GsonRequest<T> {}
//        extends Request<T> {
//    private final Listener<T> mListener;
//    private Map<String, String> headers ;
//    private final Gson gson = new Gson();
//    private final Class<T> clazz ;
//    private byte[] body;
//
//    /**
//     * Creates a new request with the given method.
//     *
//     * @param method the request {@link Method} to use
//     * @param url URL to fetch the string at
//     * @param listener Listener to receive the String response
//     * @param errorListener Error listener, or null to ignore errors
//     */
//    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers, byte[] body, Listener<T> listener,
//                       ErrorListener errorListener) {
//        super(method, url, errorListener);
//        this.headers = headers;
//        this.clazz = clazz;
//        this.body = body;
//        this.mListener = listener;
//    }
//
//    public GsonRequest(String url, Class<T> clazz, Map<String, String> headers, byte[] body, Listener<T> listener,
//                       ErrorListener errorListener) {
//        this(Method.GET,url,clazz,headers,body,listener,errorListener);
//    }
//
//    public GsonRequest(int method, String url, Class<T> clazz, byte[] body, Listener<T> listener, ErrorListener errorListener) {
//        this(method,url,clazz,null,body,listener,errorListener);
//    }
//
//    public GsonRequest(String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
//        this(Method.GET,url,clazz,null,listener,errorListener);
//    }
//
//
//    protected void addHeaders(Map<String, String> headers ){
//        if(this.headers!=null ){
//            this.headers.putAll(headers);
//        }else{
//            this.headers=  headers;
//        }
//    }
//
//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//        return headers != null ? headers : super.getHeaders();
//    }
//
//    @Override
//    public byte[] getBody() throws AuthFailureError {
//        return body != null ? body : super.getBody();
//    }
//
//    @Override
//    protected Response<T> parseNetworkResponse(NetworkResponse response) {
//        try {
//
//            String jsonString = new String(response.data,HttpHeaderParser.parseCharset(response.headers));
//            if(clazz != null) {
//                return Response.success(gson.fromJson(jsonString, clazz), HttpHeaderParser.parseCacheHeaders(response));
//            }
//            else
//            {
//                return Response.success((T) jsonString, HttpHeaderParser.parseCacheHeaders(response));
//            }
////            return Response.success(gson.fromJson(jsonString, clazz), HttpHeaderParser.parseCacheHeaders(response));
//
//        } catch (UnsupportedEncodingException e) {
//            return Response.error(new ParseError(e));
//        } catch (JsonSyntaxException e) {
//            e.printStackTrace();
//            return Response.error(new ParseError(e));
//        }
//
//    }
//    @Override
//    public String getBodyContentType() {
//        return "application/json";
//    }
//
//    @Override
//    public String getPostBodyContentType() {
//        return "application/json";
//    }
//
//    @Override
//	protected void deliverResponse(T response) {
//		mListener.onResponse(response);
//	}
//}
