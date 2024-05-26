package com.managmnet.staffie.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.managmnet.staffie.R;
import com.managmnet.staffie.admin.fragments.AdminLogin;
import com.managmnet.staffie.employee.EmployeeLogin;
import com.managmnet.staffie.manager.fragments.ManagerLogin;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv1,iv2,iv3;
    FirebaseUser user;
    FirebaseAuth auth;
    String a,b,uname,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        iv1 = findViewById(R.id.admin);
        iv2 = findViewById(R.id.manager);
        iv3 = findViewById(R.id.employee);


        SharedPreferences pref = getSharedPreferences("mypref",Context.MODE_PRIVATE);

        boolean is = pref.getBoolean("IsLogin",false);
        String user_type = pref.getString("user_type",null);
        if(is)
        {
            if(user_type.equals("admin"))
            {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
                finish();
            }

            else if(user_type.equals("manager"))
            {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
                finish();
            }
            else if(user_type.equals("employee"))
            {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
                finish();
            }

        }

        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==iv1)
        {
            Intent i = new Intent(MainActivity.this, Login.class);
            i.putExtra("view","admin");
            startActivity(i);


        }
        else if (v==iv2)
        {
            Intent i = new Intent(MainActivity.this,Login.class);
            i.putExtra("view","manager");
            startActivity(i);


        }
        else if(v==iv3)
        {
            Intent i = new Intent(MainActivity.this,Login.class);
            i.putExtra("view","employee");
            startActivity(i);

        }
    }
}