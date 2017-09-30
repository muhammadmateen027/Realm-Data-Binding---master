package com.on2sol.logbook.Adapters;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.on2sol.logbook.Activities.MainActivity;
import com.on2sol.logbook.ModelClass.ContactList;
import com.on2sol.logbook.R;


/**
 * Created by Muhammad Mateen on 9/23/2017.
 */

public class Binder {
    private static final String TAG = "Binder";
    @BindingAdapter("bind:imageRes")
    public static void bindImage(ImageView view, String r) {
        Glide.with(view.getContext()).load(r).placeholder(R.drawable.avatar).into(view);
    }


//    @BindingAdapter("bind:items")
    @BindingAdapter({"bind:items", "bind:context"})
    public static void bindList(ListView view, ContactList list, MainActivity context) {
        ListAdapter adapter = new ListAdapter(context);
        adapter.update(list.get(null));
        view.setAdapter(adapter);
    }
}
