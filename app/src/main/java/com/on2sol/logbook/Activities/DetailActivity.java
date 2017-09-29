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

import com.bumptech.glide.Glide;
import com.on2sol.logbook.ModelClass.Contact;
import com.on2sol.logbook.ModelClass.ContactList;
import com.on2sol.logbook.R;
import com.on2sol.logbook.databinding.ActivityDetailBinding;
import de.hdodenhof.circleimageview.CircleImageView;


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
    private Contact mItem = null;
    private String profile = "";
    private CircleImageView profile_image;

    private void init(){
        name_et = (EditText) findViewById(R.id.name_et);
        email_et = (EditText) findViewById(R.id.email_et);
        address_et = (EditText) findViewById(R.id.address_et);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mContext = DetailActivity.this;
        init();
        if( getIntent().getExtras() != null) {
            //do here
            name = getIntent().getStringExtra("name");
            email = getIntent().getStringExtra("email");
            address = getIntent().getStringExtra("address");
            profile = getIntent().getStringExtra("profile");

            name_et.setText(name);
            email_et.setText(email);
            address_et.setText(address);
            Glide.with(this).load(profile).into(profile_image);
        }


        list = new ContactList(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        binding.setBack(this);
        binding.setDelete(this);
        binding.setSave(this);
    }

    public View.OnClickListener getButtonClickListener() {
        return mButtonClickListener;
    }
    private final View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch (v.getId()){
                case R.id.back:
                    push(RESULT_CANCELED);
                    break;
                case R.id.delete:
                    list.deleteData(email_et.getText().toString());
                    break;
                case R.id.save:
                    name = name_et.getText().toString();
                    email = email_et.getText().toString();
                    address = address_et.getText().toString();
                    list.save(null, new Contact(name, email, address, profile));
                    break;
            }
        }
    };

    private void push(int result){
        Intent intent = new Intent();
        setResult(result, intent);
        finish();
    }

    @Override
    public void onProcessSuccess() {
        Log.d(TAG, "onProcessSuccess");
//        list.get(null);
        push(RESULT_OK);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        push(RESULT_CANCELED);
    }
}
