package com.on2sol.logbook.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.on2sol.logbook.APICalls.VolleyCall;
import com.on2sol.logbook.ModelClass.Contact;
import com.on2sol.logbook.ModelClass.ContactList;
import com.on2sol.logbook.R;
import com.on2sol.logbook.databinding.ActivityDetailBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;


public class DetailActivity extends AppCompatActivity implements ContactList.DataProcess, VolleyCall.DataInterface{
    private static final String TAG = "DetailActivity";
    private ActivityDetailBinding binding;
    private ContactList list;
    private Context mContext;
    private static final int PICK_IMAGE = 222;

    private EditText name_et;
    private EditText email_et;
    private EditText address_et;
    private String name = "";
    private String email = "";
    private String address = "";
    private String profile = "";
    private CircleImageView profile_image;
    private VolleyCall volleyCall;

    private void init(){
        name_et = (EditText) findViewById(R.id.name_et);
        email_et = (EditText) findViewById(R.id.email_et);
        address_et = (EditText) findViewById(R.id.address_et);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mContext = DetailActivity.this;
        init();
        volleyCall = new VolleyCall(this, mContext);
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


        list = new ContactList(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        binding.setBack(this);
        binding.setDelete(this);
        binding.setSave(this);
        binding.setProfile(this);
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
                    Log.d(TAG, "Save called....");
                    name = name_et.getText().toString();
                    email = email_et.getText().toString();
                    address = address_et.getText().toString();

                    list.store(name, email, address, profile);
//
                    break;
                case R.id.profile_image:
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            //TODO: action

            Uri uri = data.getData();
            profile_image.setImageURI(uri);
            String realPath = getRealPathFromURI(uri);

            profile = realPath;
        }
    }
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    private void push(int result){
        Intent intent = new Intent();
        setResult(result, intent);
        finish();
    }

    @Override
    public void onProcessSuccess() {
        Log.d(TAG, "onProcessSuccess");
        push(RESULT_OK);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        push(RESULT_CANCELED);
    }

    @Override
    public void onDataRetrived(String response) {

    }

    @Override
    public void onDataStore(Contact response) {
        list.save(null, response);
    }
}
