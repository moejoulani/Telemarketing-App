package com.example.telemarketingapp;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    Button btn;
    public static final int PER_CODEE=1;
    private static int WELCOME_TIMEOUT =2500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent welcome  = new Intent(MainActivity.this,signin.class);
                startActivity(welcome);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        },WELCOME_TIMEOUT);


        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(MainActivity.this,"You have Already Granted",Toast.LENGTH_SHORT).show();

        }else{
            requestStoragePermession();
        }




    }
    public void requestStoragePermession()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE))
        {
                new AlertDialog.Builder(this)
                        .setTitle("Permission Needed")
                        .setMessage("This Permission is needed beacause of this and that")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},PER_CODEE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                            }
                        }).create().show();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},PER_CODEE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==PER_CODEE)
        {
            if(grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED)
            {
                    Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }




    }










}
