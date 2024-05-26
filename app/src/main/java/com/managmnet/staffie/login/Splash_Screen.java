package com.managmnet.staffie.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.managmnet.staffie.R;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Thread thread = new Thread(){
            public void run(){
                try{

                    sleep(4000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{
                    Intent i = new Intent(Splash_Screen.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };thread.start();



    }
}