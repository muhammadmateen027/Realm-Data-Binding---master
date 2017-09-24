package com.on2sol.logbook.Activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.on2sol.logbook.ModelClass.Contact;
import com.on2sol.logbook.ModelClass.ContactList;
import com.on2sol.logbook.R;
import com.on2sol.logbook.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ContactList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        list = new ContactList();

        list.fetchData();

//        Contact contact = new Contact();
//        contact.id = 1;
//        contact.name = "M.Mateen";

//        list.save(null, contact);

        binding.setInfos(list);
    }
}
