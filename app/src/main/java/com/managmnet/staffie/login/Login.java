package com.managmnet.staffie.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.managmnet.staffie.R;
import com.managmnet.staffie.admin.fragments.AdminLogin;
import com.managmnet.staffie.employee.EmployeeLogin;
import com.managmnet.staffie.manager.fragments.ManagerLogin;

public class Login extends AppCompatActivity {

    String user_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        Intent i = getIntent();
        user_type= i.getStringExtra("view");

        SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);

        boolean is = pref.getBoolean("IsLogin",false);
        String user_type1 = pref.getString("user_type",null);
        if(is)
        {
            if(user_type1.equals("admin"))
            {
                AdminLogin f = new AdminLogin();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.adm_login_layout,f);
                ft.commit();
            }

            else if(user_type1.equals("manager"))
            {
                ManagerLogin f = new ManagerLogin();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.adm_login_layout,f);
                ft.commit();
            }
            else if(user_type1.equals("employee"))
            {
                EmployeeLogin f = new EmployeeLogin();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.adm_login_layout,f);
                ft.commit();
            }


        }
        else {
//            Toast.makeText(this, "not login", Toast.LENGTH_SHORT).show();
            go_to_login();
        }

    }

    public void go_to_login(){

        if(user_type.equals("admin"))
        {

            AdminLogin f = new AdminLogin();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.adm_login_layout,f);
            ft.commit();
        }

        else if(user_type.equals("manager"))
        {

            ManagerLogin f = new ManagerLogin();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.adm_login_layout,f);
            ft.commit();
        }

        else if(user_type.equals("employee"))
        {

            EmployeeLogin f = new EmployeeLogin();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.adm_login_layout,f);
            ft.commit();
        }

    }


}