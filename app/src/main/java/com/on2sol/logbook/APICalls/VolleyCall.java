package com.on2sol.logbook.APICalls;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.on2sol.logbook.ModelClass.Contact;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by Umair Saeed on 9/23/2017.
 */

public class VolleyCall {
    private static final String TAG = "VolleyCall";

    private String urlJsonObj = "https://news-balloon-app.appspot.com/webservice/uservideoinfo?email=abcd@gmail.com&page=0&access_token=a131dd0bc5e646cc693d901d98aff95e";

    public interface DataInterface{
        public void onDataSuccess(String response);
    }
    DataInterface dataInterface;
    public VolleyCall(DataInterface callbackClass){
        this.dataInterface = callbackClass;
    }
    public void makeJsonObjectRequest() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, String.valueOf(response));
                dataInterface.onDataSuccess(String.valueOf(response));

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
