package com.on2sol.logbook.ModelClass;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.util.Log;
import android.view.View;

import com.on2sol.logbook.APICalls.VolleyCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Muhammad Mateen on 9/23/2017.
 */

public class ContactList implements VolleyCall.DataInterface{
    private static final String TAG = "ContactList";
    public ObservableArrayList<Contact> list = new ObservableArrayList<>();
    private Realm realm;
    private VolleyCall volleyCall;
    private Context context;
    private boolean isStored = false;

    public interface DataProcess{
        public void onProcessSuccess();
    }
    private DataProcess dataProcess;
    public ContactList(DataProcess callbackClass, Context context) {
        this.context = context;
        this.dataProcess = callbackClass;
        realm = Realm.getDefaultInstance();
        volleyCall = new VolleyCall(ContactList.this, context);
    }

    public void fetchData() {
        volleyCall.getDataFromServer();
    }

    public void store(final String name, final String email, final String address, final String image){

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                if (checkIfExists(bgRealm, email)) isStored = true;
                else isStored = false;
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                if (isStored)
                    volleyCall.updateData(name, email, address, image);
                else
                    volleyCall.storeData(name, email, address, image);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.d(TAG,"onError(Throwable error)");
            }
        });
    }
    public void deleteDat(String email) {
        volleyCall.deleteData(email);
    }


    private void save(View view, final Contact contact){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                if (checkIfExists(bgRealm, contact.email)){
                    Contact cObj = bgRealm.where(Contact.class).equalTo("email", contact.email).findFirst();
                    cObj.name = contact.name;
                    cObj.address = contact.address;
                    cObj.profile = contact.profile;
                }
                else{
                    Contact realmContact = bgRealm.createObject(Contact.class);
                    realmContact.name = contact.name;
                    realmContact.email = contact.email;
                    realmContact.profile = contact.profile;
                    realmContact.address = contact.address;
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


    public List<Contact> get(View view){
        RealmResults<Contact> results = realm.where(Contact.class).findAll();
        Log.d(TAG, String.valueOf(results));

        List<Contact> retVal = new ArrayList<Contact>();
        for (int i=0; i<results.size(); i++) {
            Contact realmObj = results.get(i);
            retVal.add(realmObj);
        }
        return retVal;
    }

    private void deleteData(final String email){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                if (checkIfExists(bgRealm, email)){
                    RealmResults<Contact> result = bgRealm.where(Contact.class).equalTo("email", email).findAll();
                    result.deleteAllFromRealm();
                    Log.d(TAG, "deleteData2");
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

    @Override
    public void onDataRetrived(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equalsIgnoreCase("1")){
                JSONArray jsonArray = jsonObject.getJSONArray("value");
                for (int i=0; i<jsonArray.length()-1; i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Contact c = new Contact();
                    c.email = obj.getString("email");
                    c.name = obj.getString("name");
                    c.profile = obj.getString("image");
                    c.address = obj.getString("address");
                    this.save(null, c);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataStore(Contact response) {
        this.save(null, response);
    }

    @Override
    public void onDeleteData(String response) {
        deleteData(response);
    }

    private boolean checkIfExists(Realm realmM, String email){
        RealmQuery<Contact> query = realmM.where(Contact.class).equalTo("email", email);
        return query.count() != 0;
    }
}
