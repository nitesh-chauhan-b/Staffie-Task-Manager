package com.managmnet.staffie.manager;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.managmnet.staffie.R;
import com.managmnet.staffie.admin.fragments.AdminDetailTaskView;
import com.managmnet.staffie.employee.EmployeeScreen;
import com.managmnet.staffie.login.MainActivity;
import com.managmnet.staffie.manager.fragments.ManagerAdminTasks;
import com.managmnet.staffie.manager.fragments.ManagerCompletedTaskView;
import com.managmnet.staffie.manager.fragments.ManagerCreateTaskView;
import com.managmnet.staffie.manager.fragments.ManagerTaskView;
import com.managmnet.staffie.manager.fragments.Emp_Mng_Completed_Task_View;
import com.managmnet.staffie.manager.navigationfragments.Manager_Attendace;
import com.managmnet.staffie.manager.navigationfragments.Manager_Salary_Check;
import com.managmnet.staffie.manager.navigationfragments.Manager_Take_Attendance;

public class ManagerScreen extends AppCompatActivity {
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
    boolean is_nav_selected=false;
    LayoutInflater layoutInflater;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_screen);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        assert user != null;

        drawerLayout = findViewById(R.id.mng_drawer);
        nv = findViewById(R.id.mng_navigation_view);

        toolbar = findViewById(R.id.mng_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setVisibility(View.VISIBLE);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mng_screen_navigation_menu,new ManagerTaskView());
        ft.commit();

        layoutInflater = (LayoutInflater) ManagerScreen.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.rv_for_mnager_assigned_task_member,null);


        create_button = findViewById(R.id.create_task_menu);
        bottomAppBar = findViewById(R.id.bottom_menu);

        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.mng_screen_navigation_menu,new ManagerCreateTaskView());
                ft1.addToBackStack(null);
                ft1.commit();
            }
        });
        bv = (BottomNavigationView) findViewById(R.id.bottom_nv);
        bv.setBackground(null);

        bv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.mng_assigned_task)
                {
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.mng_screen_navigation_menu,new ManagerTaskView());
                    ft1.addToBackStack(null);
                    ft1.commit();
                }
                else if(item.getItemId()==R.id.mng_take_attendance_menu)
                {
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.mng_screen_navigation_menu,new Manager_Take_Attendance());
                    ft1.addToBackStack(null);
                    ft1.commit();
                }
                else if(item.getItemId()==R.id.mng_got_task_menu)
                {
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.mng_screen_navigation_menu,new ManagerAdminTasks());
                    ft1.addToBackStack(null);
                    ft1.commit();
                }
                else if(item.getItemId()==R.id.mng_completed_task_menu)
                {
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.mng_screen_navigation_menu,new ManagerCompletedTaskView());
                    ft1.addToBackStack(null);
                    ft1.commit();
                }
                return true;
            }
        });
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {

                if(item.getItemId()==R.id.mng_home)
                {
                    ManagerTaskView f = new ManagerTaskView();
                    FragmentManager ft = getSupportFragmentManager();
                    ft.beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.mng_screen_navigation_menu,f)
                            .commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                else if(item.getItemId()==R.id.mng_menu_attendance)
                {
                    Manager_Attendace f = new Manager_Attendace();
                    FragmentManager ft = getSupportFragmentManager();
                    ft.beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.mng_screen_navigation_menu,f)
                            .commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(item.getItemId()==R.id.mng_menu_salary_check)
                {

                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.mng_screen_navigation_menu,new Manager_Salary_Check());
                    ft1.addToBackStack(null);
                    ft1.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(item.getItemId()==R.id.mng_menu_log_out)
                {
                    auth.signOut();
                    Toast.makeText(ManagerScreen.this, "You have logged out!", Toast.LENGTH_SHORT).show();

                    SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = pref.edit();
                    ed.putBoolean("IsLogin",false);
                    ed.putString("user_type",null);
                    ed.apply();


                    drawerLayout.closeDrawer(GravityCompat.START);
                    Intent i = new Intent(ManagerScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
//                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }

        });

    }


    public final LayoutInflater getLayoutInflater(){

        if(layoutInflater == null)
        {
            return performGetLayoutInflater();
        }

        return layoutInflater;
    }

    public LayoutInflater performGetLayoutInflater() {
        LayoutInflater inflater = (LayoutInflater) ManagerScreen.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.rv_for_mnager_assigned_task_member,null);
        return inflater;
    }


    public void is_nav_selected(boolean t){
        is_nav_selected=t;
    }

    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawerLayout.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
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
}