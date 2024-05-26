package com.managmnet.staffie.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.managmnet.staffie.R;
import com.managmnet.staffie.admin.fragments.AdminCreateTaskView;
import com.managmnet.staffie.admin.fragments.AdminDetailTaskView;
import com.managmnet.staffie.admin.fragments.AdminLogin;
import com.managmnet.staffie.admin.fragments.AdminTaskView;
import com.managmnet.staffie.admin.fragments.Admin_take_attendace;
import com.managmnet.staffie.admin.navigationfragments.DrawerLocker;
import com.managmnet.staffie.login.MainActivity;

public class AdminScreen extends AppCompatActivity implements DrawerLocker {
    TabLayout tabLayout;
    ViewPager viewPager;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nv;
    AdminDetailTaskView kl;
    BottomNavigationView bv;
    public BottomAppBar bottomAppBar;
    public Toolbar toolbar;
    public FloatingActionButton create_button;

    FirebaseAuth auth;
    FirebaseUser user;
    TextView admin_nv_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

//        tabLayout = findViewById(R.id.admin_tablayout_view);
//        viewPager = findViewById(R.id.admin_viewpager);

        create_button = findViewById(R.id.create_task_menu);
        bottomAppBar = findViewById(R.id.bottom_menu);
        admin_nv_name = findViewById(R.id.admin_nv_name);

        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        assert user != null;

        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.adm_screen_navigation_menu,new AdminCreateTaskView());
                ft1.addToBackStack(null);
                ft1.commit();
            }
        });

        kl = new AdminDetailTaskView();


        bv = (BottomNavigationView) findViewById(R.id.bottom_nv);
        bv.setBackground(null);

        drawerLayout = findViewById(R.id.adm_drawer);

        nv = findViewById(R.id.adm_navigation_view);
        FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
        ft4.replace(R.id.adm_screen_navigation_menu,new AdminTaskView());
        ft4.commit();

        bv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.adm_assigned_task)
                {
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.adm_screen_navigation_menu,new AdminTaskView());
                    ft1.addToBackStack(null);
                    ft1.commit();
                }
                else if(item.getItemId()==R.id.adm_take_attendance_menu)
                {
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.adm_screen_navigation_menu,new Admin_take_attendace());
                    ft1.addToBackStack(null);
                    ft1.commit();
                }
                return true;
            }
        });


//


        toolbar= findViewById(R.id.adm_toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                if(item.getItemId()==R.id.admin_menu_home)
                {
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.adm_screen_navigation_menu,new AdminTaskView());
                    ft1.addToBackStack(null);
                    ft1.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);

                }
                else if(item.getItemId()==R.id.adm_menu_logout)
                {
                    auth.signOut();
                    Toast.makeText(AdminScreen.this, "You have logged out!", Toast.LENGTH_SHORT).show();

                    SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = pref.edit();
                    ed.putBoolean("IsLogin",false);
                    ed.putString("user_type",null);
                    ed.apply();


                    drawerLayout.closeDrawer(GravityCompat.START);
                    Intent i = new Intent(AdminScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

                return true;
            }

        });


    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>0)
        {
            getSupportFragmentManager().popBackStack();
        }
        else{
            super.onBackPressed();
        }

    }
    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawerLayout.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
    }


}