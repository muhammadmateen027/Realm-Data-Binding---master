package com.on2sol.logbook.Adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.on2sol.logbook.ModelClass.Contact;
import com.on2sol.logbook.R;
import com.on2sol.logbook.databinding.ListItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad Mateen on 9/23/2017.
 */

public class ListAdapter extends BaseAdapter {
    private static final String TAG = "ListAdapter";
    private List<Contact> list;
    private LayoutInflater inflater;


    public interface OnItemClickListener {
        void onItemClick(Contact item);
    }

    public OnItemClickListener listener;
    public ListAdapter(OnItemClickListener listener){
        this.listener = listener;
    }
    public ListAdapter(){}
    public void update(List<Contact> l) {
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
        binding.setListener(listener);
        return binding.getRoot();
    }
}
