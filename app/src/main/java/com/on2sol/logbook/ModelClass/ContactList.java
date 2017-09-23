package com.on2sol.logbook.ModelClass;

import android.databinding.ObservableArrayList;
import android.util.Log;
import android.view.View;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Umair Saeed on 9/23/2017.
 */

public class ContactList {
    private static final String TAG = "ContactList";
    public ObservableArrayList<Contact> list = new ObservableArrayList<>();
    private int mTotalCount;
    private Realm realm;

    public ContactList() {
    }

    public void save(View view, final Contact contact){
        realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Contact realmContact = bgRealm.createObject(Contact.class);
                realmContact = contact;
                realmContact.id = contact.id;
                realmContact.name = contact.name;
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


    public void get(View view){
        realm = Realm.getDefaultInstance();
        RealmResults results = realm.where(Contact.class).findAll();
        Log.d(TAG, String.valueOf(results));
    }
}
