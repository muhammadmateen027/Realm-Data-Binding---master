package com.on2sol.logbook.Activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.on2sol.logbook.ModelClass.Contact;
import com.on2sol.logbook.ModelClass.ContactList;
import com.on2sol.logbook.R;
import com.on2sol.logbook.databinding.ActivityDetailBinding;


public class DetailActivity extends AppCompatActivity implements ContactList.DataProcess{
    private static final String TAG = "DetailActivity";
    private ActivityDetailBinding binding;
    private ContactList list;
    private Context mContext;

    private EditText name_et;
    private EditText email_et;
    private EditText address_et;
    private String name = "";
    private String email = "";
    private String address = "";

    private void init(){
        name_et = (EditText) findViewById(R.id.name_et);
        email_et = (EditText) findViewById(R.id.email_et);
        address_et = (EditText) findViewById(R.id.address_et);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mContext = DetailActivity.this;
        init();
        list = new ContactList(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        binding.setBack(this);
        binding.setDelete(this);
        binding.setSave(this);

                Contact contact = new Contact();
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
            switch (v.getId()){
                case R.id.back:
                    push();
                    break;
                case R.id.delete:
                    list.deleteData(email_et.getText().toString());
                    break;
                case R.id.save:
                    name = name_et.getText().toString();
                    email = email_et.getText().toString();
                    address = address_et.getText().toString();
                    list.save(null, new Contact(name, email, address, ""));
                    break;
            }

        }
    };

    private void push(){
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onProcessSuccess() {
        Log.d(TAG, "onProcessSuccess");
//        list.get(null);
        push();
    }
}
