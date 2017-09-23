package com.on2sol.logbook.ModelClass;

import io.realm.RealmObject;

/**
 * Created by Umair Saeed on 9/23/2017.
 */

public class Contact extends RealmObject {
    public int id;
    public String name;
    public String email;
    public String address;

}
