package com.askvaibhav.qrcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.app.Activity;




import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;


public class SettingsActivity extends AppCompatActivity {


    static int love=3;
    static int flag=0;
    private Switch switch3;






    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switch3=(Switch)findViewById(R.id.switch3);
        if (AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            switch3.setChecked(true);
        }
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_YES);
                    flag=1;

                    ///restartApp();
                }
                else {
                    AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_NO);
                    flag=0;
                    //restartApp();
                }
            }
        });





        Button playgoogle = findViewById(R.id.playgoogle);
        playgoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(SettingsActivity.this);

                // Set the message show for the Alert time
                builder.setMessage("Our App is not on Play Store yet.");

                // Set Alert Title
                builder.setTitle("WAIT!");

                // Set Cancelable false
                // for when the user clicks on the outside
                // the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name
                // OnClickListener method is use of
                // DialogInterface interface.

                builder
                        .setPositiveButton(
                                "OK",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {

                                        // When the user click yes button
                                        // then app will close
                                        dialog.cancel();
                                    }
                                });

                // Set the Negative button with No name
                // OnClickListener method is use
                // of DialogInterface interface.

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();

                // Show the Alert Dialog box
                alertDialog.show();
            }
        });


        Switch switch1 = findViewById(R.id.switch1);
        Switch switch2 = findViewById(R.id.switch2);
        final SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        switch1.setChecked(sharedPreferences.getBoolean("your_key", true));
        switch2.setChecked(sharedPreferences.getBoolean("your_key1", true));



        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean ischecked) {
                editor.putBoolean("your_key", ischecked);
                editor.commit();
                if (ischecked) {

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 18);
                    calendar.set(Calendar.MINUTE, 20);
                    Intent intent = new Intent(getApplicationContext(), NotificationReciever.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY, pendingIntent);
                }
            }
        });
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean ischecked) {
                editor.putBoolean("your_key1", ischecked);
                editor.commit();


                if( ischecked) {
                      love = 1;
                }else
                    love=0;

            }
        });


    }
        public void mailme (View view){
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            String aEmailList[] = {"askvaibhavsharma@gmail.com"};
            emailIntent.setType("plain/text");


            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
            startActivity(Intent.createChooser(emailIntent, "Send your email in:"));
        }

        public void restartApp(){
        Intent i = new Intent(getApplicationContext(),SettingsActivity.class);
        startActivity(i);
        finish();

        }


    }