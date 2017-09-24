package com.on2sol.logbook.Adapters;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.on2sol.logbook.ModelClass.ContactList;
import com.on2sol.logbook.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Umair Saeed on 9/23/2017.
 */

public class Binder {
    private static final String TAG = "Binder";
    @BindingAdapter("bind:imageRes")
    public static void bindImage(ImageView view, String r) {
        Picasso.with(view.getContext()).load(r).placeholder(R.drawable.avatar).into(view);
    }


    @BindingAdapter("bind:items")
    public static void bindList(ListView view, ContactList list) {
        ListAdapter adapter = new ListAdapter();
        adapter.update(list.get(null));
        view.setAdapter(adapter);
    }
}
