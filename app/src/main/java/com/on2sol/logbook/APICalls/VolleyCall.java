package com.on2sol.logbook.APICalls;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.on2sol.logbook.ModelClass.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Muhammad Mateen on 9/23/2017.
 */

public class VolleyCall {
    private static final String TAG = "VolleyCall";

    private static final String GET_URL = "https://ihilbi.com/logbook/crud/read.php";
    private static final String STORE_URL = "https://ihilbi.com/logbook/crud/create.php?";
    private static final String UPDATE_URL = "https://ihilbi.com/logbook/crud/update.php";
    private static final String DELETE_URL = "https://ihilbi.com/logbook/crud/delete.php";

    private Context context;
    public interface DataInterface{
        public void onDataRetrived(String response);
        public void onDataStore(Contact response);
        public void onDeleteData(String response);
    }
    private DataInterface dataInterface;
    public VolleyCall(DataInterface callbackClass, Context m){
        this.dataInterface = callbackClass;
        this.context = m;
    }

    public void getDataFromServer() {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, GET_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d(TAG, "Response: "+ response);
                        dataInterface.onDataRetrived(response);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d(TAG, "Error.Response:"+ String.valueOf(error));
                    }
                }
        );
//        {
//            @Override
//            protected Map<String, String> getParams()
//            {
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("email", "abcd@gmail.com");
//                params.put("page", "0");
//                params.put("access_token", "a131dd0bc5e646cc693d901d98aff95e");
//
//                return params;
//            }
//        };
        queue.add(postRequest);

    }

    public void storeData(final String name, final String email, final String address, final String image){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, STORE_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d(TAG,"Response : "+ response);
                        JSONObject jsonObject = null;
                        JSONArray value = null;
                        try {
                            jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equalsIgnoreCase("1")){
                                value = jsonObject.getJSONArray("value");
                                if (value.length() != 0) {
                                    Contact c = null;
                                    for (int i = 0; i < value.length(); i++) {
                                        JSONObject valueObj = value.getJSONObject(i);
                                        c = new Contact(valueObj.getString("name"),
                                                valueObj.getString("email"), valueObj.getString("address"),
                                                valueObj.getString("image"));
                                    }
                                    if (c != null)
                                        dataInterface.onDataStore(c);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d(TAG, "Error.Response : "+ String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", email);
                params.put("name", name);
                params.put("address", address);
                params.put("image", image);
                return params;
            }
        };
        queue.add(postRequest);

    }

    public void updateData(final String name, final String email, final String address, final String image){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, UPDATE_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d(TAG,"Response : "+ response);
                        JSONObject jsonObject = null;
                        JSONArray value = null;
                        try {
                            jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equalsIgnoreCase("1")){
                                value = jsonObject.getJSONArray("value");
                                if (value.length() != 0) {
                                    Contact c = null;
                                    for (int i = 0; i < value.length(); i++) {
                                        JSONObject valueObj = value.getJSONObject(i);
                                        c = new Contact(valueObj.getString("name"),
                                                valueObj.getString("email"), valueObj.getString("address"),
                                                valueObj.getString("image"));
                                    }
                                    if (c != null)
                                        dataInterface.onDataStore(c);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d(TAG, "Error.Response : "+ String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", email);
                params.put("name", name);
                params.put("address", address);
                params.put("image", image);
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void deleteData(final String email){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, DELETE_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d(TAG,"Response : "+ response);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equalsIgnoreCase("1")){
                                dataInterface.onDeleteData(jsonObject.getString("value"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d(TAG, "Error.Response : "+ String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", email);
                return params;
            }
        };
        queue.add(postRequest);

    }
}
