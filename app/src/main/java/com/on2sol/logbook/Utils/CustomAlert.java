package com.on2sol.logbook.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import com.on2sol.logbook.R;

/**
 * Created by Umair Saeed on 9/24/2017.
 */

public class CustomAlert {
    private Context mContext;
    public CustomAlert(Context context){
        this.mContext = context;
    }
    public void showSettingsAlert(final Activity activity, String title, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        //Setting Dialog Title
        alertDialog.setTitle(title);

        //Setting Dialog Message
        alertDialog.setMessage(msg);

        //On Pressing Setting button
        alertDialog.setPositiveButton(R.string.allow, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        //On pressing cancel button
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
                activity.finish();
            }
        });

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialogInterface.cancel();
                activity.finish();
            }
        });
        alertDialog.show();
    }
}
