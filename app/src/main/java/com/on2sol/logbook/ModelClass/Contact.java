package com.on2sol.logbook.ModelClass;

import io.realm.RealmObject;

/**
 * Created by Muhammad Mateen on 9/23/2017.
 */

public class Contact extends RealmObject {
    public Contact(){}
    public Contact(String name, String email, String address, String profile) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String name;
    public String email;
    public String address;
    public String profile;

}
