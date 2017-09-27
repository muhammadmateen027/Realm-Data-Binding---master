package com.on2sol.logbook.ModelClass;

import io.realm.RealmObject;

/**
 * Created by Umair Saeed on 9/23/2017.
 */

public class Contact extends RealmObject {
    public Contact(){}
    public Contact(String name, String email, String address, String profile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.profile = profile;
    }

    public int id;
    public String name;
    public String email;
    public String address;
    public String profile;

}
