package com.on2sol.logbook.Adapters;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.on2sol.logbook.ModelClass.Contact;
import com.on2sol.logbook.ModelClass.ContactList;

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
    public static void bindImage(ImageView view, String  r) {
        view.setImageBitmap(getImageBitmap(r));
    }


    @BindingAdapter("bind:items")
    public static void bindList(ListView view, ContactList list) {
        ListAdapter adapter = new ListAdapter();
        adapter.update(list.get(null));
        view.setAdapter(adapter);
    }
    private static Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Error getting bitmap", e);
        }
        return bm;
    }
}
