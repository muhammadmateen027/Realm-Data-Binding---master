package com.on2sol.logbook.ModelClass;

import android.databinding.ObservableArrayList;
import android.util.Log;
import android.view.View;

import com.on2sol.logbook.APICalls.VolleyCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Umair Saeed on 9/23/2017.
 */

public class ContactList implements VolleyCall.DataInterface{
    private static final String TAG = "ContactList";
    public ObservableArrayList<Contact> list = new ObservableArrayList<>();
    private int mTotalCount;
    private Realm realm;
    private VolleyCall volleyCall;
    public ContactList() {

    }

    public void fetchData() {
        volleyCall = new VolleyCall(ContactList.this);
        volleyCall.makeJsonObjectRequest();
//        data (JSONArray) = volly.get();
//
//        for (int i=0; i<data.size(); i++) {
//            JSONObject obj = (JSONObject) data.get(i);
//
//            Contact c = obj
//            this.save(null, c);
//        }
    }

    public void save(View view, final Contact contact){
        realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Contact realmContact = bgRealm.createObject(Contact.class);
                realmContact.id = contact.id;
                realmContact.name = contact.name;
                realmContact.email = contact.email;
            }
    }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.d(TAG,"Realm.Transaction.OnSuccess()");

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.d(TAG,"onError(Throwable error)");
            }
        });
    }


    public ObservableArrayList<Contact> get(View view){
        realm = Realm.getDefaultInstance();
        RealmResults<Contact> results = realm.where(Contact.class).findAll();
        Log.d(TAG, String.valueOf(results));

        ObservableArrayList<Contact> retVal = new ObservableArrayList<Contact>();
        for (int i=0; i<results.size(); i++) {
            Contact realmObj = results.get(i);
//            Contact c = new Contact();
//            c.name = realmObj.name;
//            c.name = "Engr Munib";
            retVal.add(realmObj);
        }

        return retVal;
    }

    @Override
    public void onDataSuccess(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            if (jsonObject.getString("result").equalsIgnoreCase("success") && jsonObject.getString("success").equalsIgnoreCase("1")){
                JSONArray jsonArray = jsonObject.getJSONArray("value");
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Contact c = new Contact();
                    c.name = obj.getString("profile_name");
                    c.email = obj.getString("profile_email");
                    c.profile = obj.getString("profile_image");
                    this.save(null, c);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
