package com.on2sol.logbook.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.on2sol.logbook.ModelClass.ContactList;
import com.on2sol.logbook.R;
import com.on2sol.logbook.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ContactList.DataProcess{
    private ContactList list;
    private FloatingActionButton fab;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        list = new ContactList(this);

        list.fetchData();
        list.get(null);
        binding.setNext(this);
        binding.setInfos(list);
//        Contact contact = new Contact();
//        contact.id = 1;
//        contact.name = "M.Mateen";

//        list.save(null, contact);
    }
    public View.OnClickListener getButtonClickListener() {
        return mButtonClickListener;
    }
    private final View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onProcessSuccess() {
        binding.setInfos(list);
    }
}
