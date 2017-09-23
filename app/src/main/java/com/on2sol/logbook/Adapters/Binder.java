package com.on2sol.logbook.Adapters;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.widget.ImageView;
import android.widget.ListView;

import com.on2sol.logbook.ModelClass.Contact;

/**
 * Created by Umair Saeed on 9/23/2017.
 */

public class Binder {
    @BindingAdapter("bind:imageRes")
    public static void bindImage(ImageView view, int r) {
        view.setImageResource(r);
    }


    @BindingAdapter("bind:items")
    public static void bindList(ListView view, ObservableArrayList<Contact> list) {
        ListAdapter adapter = new ListAdapter();
        adapter.update(list);
        view.setAdapter(adapter);
    }
}
