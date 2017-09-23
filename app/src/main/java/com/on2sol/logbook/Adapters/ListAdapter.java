package com.on2sol.logbook.Adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.on2sol.logbook.ModelClass.Contact;
import com.on2sol.logbook.R;
import com.on2sol.logbook.databinding.ListItemBinding;

/**
 * Created by Umair Saeed on 9/23/2017.
 */

public class ListAdapter extends BaseAdapter{
    private ObservableArrayList<Contact> list;
    private LayoutInflater inflater;

    public void update(ObservableArrayList<Contact> l) {
        list = l;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        ListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item, parent, false);
        binding.setContact(list.get(position));

        return binding.getRoot();
    }
}
