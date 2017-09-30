package com.on2sol.logbook.Activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.on2sol.logbook.Adapters.ListAdapter;
import com.on2sol.logbook.ModelClass.Contact;
import com.on2sol.logbook.ModelClass.ContactList;
import com.on2sol.logbook.R;
import com.on2sol.logbook.databinding.ActivityMainBinding;

import java.io.Serializable;

import static android.R.attr.filter;

public class MainActivity extends AppCompatActivity implements ContactList.DataProcess, ListAdapter.OnItemClickListener{
    private static final String TAG = "MainActivity";
    private static final int NEXT_ACTIVITY = 222;
    private ContactList list;
    private ActivityMainBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = MainActivity.this;
        list = new ContactList(this, this);

        list.fetchData();
        list.get(null);
        binding.setNext(this);
        binding.setSelf(this);
        binding.setInfos(list);
        binding.setData(this);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.w(TAG, "onTextChanged " + s);
    }

    public View.OnClickListener getButtonClickListener() {
        return mButtonClickListener;
    }
    private final View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            startActivityForResult(intent, NEXT_ACTIVITY);
        }
    };

    @Override
    public void onProcessSuccess() {
        binding.setInfos(list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == NEXT_ACTIVITY){
                binding.setInfos(list);
            }
        }
    }

    @Override
    public void onItemClick(Contact item) {
        Log.d(TAG, "New Value "+item.name + " "+ item.profile);
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("name", item.name);
        intent.putExtra("email",  item.email);
        intent.putExtra("address", item.address);
        intent.putExtra("profile", item.profile);
        startActivityForResult(intent, NEXT_ACTIVITY);
    }
}
