package com.on2sol.logbook;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.on2sol.logbook.Activities.MainActivity;
import com.on2sol.logbook.Utils.CustomAlert;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private Activity activity;
    private Context context;
    private CustomAlert customAlert;

    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.
    String[] permissions = new String[] {
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = SplashActivity.this;
        context = SplashActivity.this;

        customAlert = new CustomAlert(context);

        if (checkPermissions()){
            // permissions granted.
            Log.d("checkPermissions", "if");

        } else {
            // show dialog informing them that we lack certain permissions
            Log.d("checkPermissions", "else");
            waitToStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNetworkConnected())
            waitToStart();
        else
            customAlert.showSettingsAlert(this, getResources()
                            .getString(R.string.WiFiAlertDialogTitle), getResources()
                    .getString(R.string.WiFiAlertDialogMessage));
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(context,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // permissions granted.

                    waitToStart();
                } else {
                    // no permissions granted.
                }
                return;
            }
        }
    }

    private void waitToStart(){
        new Timer().schedule(new TimerTask(){
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
            }
        }, 2000);
    }
}
