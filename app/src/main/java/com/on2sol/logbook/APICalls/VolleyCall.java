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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Umair Saeed on 9/23/2017.
 */

public class VolleyCall {
    private static final String TAG = "VolleyCall";

    private static final String GET_URL = "https://news-balloon-app.appspot.com/webservice/uservideoinfo?email=abcd@gmail.com&page=0&access_token=a131dd0bc5e646cc693d901d98aff95e";
    private static final String STORE_URL = "https://news-balloon-app.appspot.com/webservice/savevide?";
    private static final String UDATE_URL = "https://news-balloon-app.appspot.com/webservice/uservideoinfo?email=abcd@gmail.com&page=0&access_token=a131dd0bc5e646cc693d901d98aff95e";
    private static final String DELETE_URL = "https://news-balloon-app.appspot.com/webservice/uservideoinfo?email=abcd@gmail.com&page=0&access_token=a131dd0bc5e646cc693d901d98aff95e";

    private Context context;
    public interface DataInterface{
        public void onDataSuccess(String response);
        public void onDataS(String response);
    }
    private DataInterface dataInterface;
    public VolleyCall(DataInterface callbackClass, Context m){
        this.dataInterface = callbackClass;
        this.context = m;
    }
    public void getDataFromServer() {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.GET, GET_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        dataInterface.onDataSuccess(response);
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
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

    public void storeData(){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, STORE_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d(TAG,"Response : "+ response);
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
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", "abcd@gmail.com");
                params.put("video", "");
                params.put("description", "");
                params.put("thumbsmall", "");
                params.put("thumblarge", "");
                params.put("lat", "0");
                params.put("lng", "0");
                params.put("type", "MILD");
                params.put("duration", "0:00");


                params.put("access_token", "a131dd0bc5e646cc693d901d98aff95e");

                return params;
            }
        };
        queue.add(postRequest);

    }

    public void updateData(){}

    public void deleteData(){}
}
