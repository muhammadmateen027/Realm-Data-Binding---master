package com.on2sol.logbook.ModelClass;

import android.databinding.ObservableArrayList;
import android.util.Log;
import android.view.View;

import com.on2sol.logbook.APICalls.VolleyCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmQuery;
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

    public interface DataProcess{
        public void onProcessSuccess();
    }
    private DataProcess dataProcess;
    public ContactList(DataProcess callbackClass) {
        this.dataProcess = callbackClass;
        realm = Realm.getDefaultInstance();
    }

    public void fetchData() {
        volleyCall = new VolleyCall(ContactList.this);
        volleyCall.getDataFromServer();
    }

    public void save(View view, final Contact contact){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                if (checkIfExists(bgRealm, contact.email)){
                    Contact cObj = bgRealm.where(Contact.class).equalTo("email", contact.email).findFirst();
                    cObj.name = contact.name;
                    cObj.address = contact.address;
                }
                else{
                    Contact realmContact = bgRealm.createObject(Contact.class);
                    realmContact.id = contact.id;
                    realmContact.name = contact.name;
                    realmContact.email = contact.email;
                }
            }
    }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                dataProcess.onProcessSuccess();
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
        RealmResults<Contact> results = realm.where(Contact.class).findAll();
        Log.d(TAG, String.valueOf(results));

        ObservableArrayList<Contact> retVal = new ObservableArrayList<Contact>();
        for (int i=0; i<results.size(); i++) {
            Contact realmObj = results.get(i);
            retVal.add(realmObj);
        }
        return retVal;
    }

    public void deleteData(final String email){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                RealmResults<Contact> result = bgRealm.where(Contact.class).equalTo("email", email).findAll();
                result.deleteAllFromRealm();
                Log.d(TAG, "deleteData2");
                dataProcess.onProcessSuccess();
            }
        });
    }

    @Override
    public void onDataSuccess(JSONObject response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = response;
            if (jsonObject.getString("result").equalsIgnoreCase("success") &&
                    jsonObject.getString("success").equalsIgnoreCase("1")){
                JSONArray jsonArray = jsonObject.getJSONArray("value");
                for (int i=0; i<jsonArray.length()-1; i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Contact c = new Contact();
                    c.email = obj.getString("profile_email");
                    c.name = obj.getString("profile_name");
                    c.profile = obj.getString("profile_image");
                    this.save(null, c);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private boolean checkIfExists(Realm realmM, String email){
        RealmQuery<Contact> query = realmM.where(Contact.class).equalTo("email", email);
        return query.count() != 0;
    }
}
