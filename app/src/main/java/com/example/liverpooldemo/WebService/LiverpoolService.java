package com.example.liverpooldemo.WebService;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;

import com.example.liverpooldemo.utils.MyUtilsJava;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;

import java.util.Map;

import static com.android.volley.Request.Method.GET;


public class LiverpoolService {

    /**
     * Notificador
     */
    WebResponseDelegate mDelegate;
    Context mContext;

    public LiverpoolService(Context context) {
        mContext = context;
    }


    private static final String URLBASE = "https://shoppapp.liverpool.com.mx/";


    public static final int NO_INTERNET = 10;
    public static final int FORBIDDEN = 403;
    public static final int UNAUTHORIZED = 401;


    public enum Endpoint {

        SearchProducto("appclienteservices/services/v3/plp/?", GET, null);


        public String value;
        public int method;
        public HashMap<String, Object> parametros;

        Endpoint(String text, int method, HashMap<String, Object> parametros) {
            this.value = text;
            this.method = method;
            this.parametros = parametros;
        }

    }

    /**
     * Hace un request
     *
     * @param endpoint       la url
     * @param jsonObject
     * @param responseObject el tipo de objeto que se espera ebtener
     */
    public void performRequest(final Endpoint endpoint, final JSONObject jsonObject, final Class responseObject) {
        String url = URLBASE + endpoint.value;

        if (endpoint.parametros != null) {
            //Entonces llenar la url con los parametros
        }


        if (endpoint.parametros != null) {

            Iterator it = endpoint.parametros.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
                url += pair.getKey();
                url += "=";
                url += pair.getValue();
                url += "&";
            }
        }

        Log.d(MyUtilsJava.APP_NAME, "METHOD:" + url);
        Log.d(MyUtilsJava.APP_NAME, "REQUEST:" + jsonObject);

        Log.d(MyUtilsJava.APP_NAME, "------------------------");

        StringRequest stringRequest = new StringRequest(endpoint.method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HttpClient", "success! response: " + response.toString());
                        try {
                            final String res = String.valueOf(response);
                            try {
                                response = new String(response.getBytes(), "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            JSONObject json = new JSONObject(response);
                            JSONObject plpResults = json.getJSONObject("plpResults");
                            JSONArray results = plpResults.getJSONArray("records");
                            mDelegate.didFInish(results, endpoint);

                        } catch (JSONException e) {
                            mDelegate.didFinishWithError(endpoint, null, NO_INTERNET);
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Bundle params = new Bundle();
                        //Es un error de falta de internet
                        if (error instanceof NoConnectionError) {
                            params.putString("Error", "No internet");
                            mDelegate.didFinishWithError(endpoint, null, NO_INTERNET);
                            return;
                        }
                        if (error.networkResponse == null) {
                            mDelegate.didFinishWithError(endpoint, null, NO_INTERNET);
                            return;
                        }
                        try {
                            String body;
                            final String statusCode = String.valueOf(error.networkResponse.statusCode);
                            body = new String(error.networkResponse.data, "UTF-8");
                            Log.d(MyUtilsJava.APP_NAME, "------------------------");
                            Log.d(MyUtilsJava.APP_NAME, "CODIGO : " + error.networkResponse.statusCode);
                            Log.d(MyUtilsJava.APP_NAME, "RESPONSE :" + body);
                            Log.d(MyUtilsJava.APP_NAME, "------------------------");

                            int code = error.networkResponse.statusCode;
                            params.putString("code", code + "");
                            params.putString("body", body);
                            if (code == FORBIDDEN) {
                                Log.d(MyUtilsJava.APP_NAME, "----------FORBIDDEN--------");

                                mDelegate.didFinishWithError(endpoint, "error", FORBIDDEN);

                                return;
                            } else if (code == UNAUTHORIZED) {

                                mDelegate.didFinishWithError(endpoint, "", UNAUTHORIZED);
                            } else {
                                mDelegate.didFinishWithError(endpoint, "", 500);
                            }

                            mDelegate.didFinishWithError(endpoint, null, code);

                        } catch (Exception e) {
                        }

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }


    public WebResponseDelegate getDelegate() {
        return mDelegate;
    }

    public void setDelegate(WebResponseDelegate delegate) {
        mDelegate = delegate;
    }

}
