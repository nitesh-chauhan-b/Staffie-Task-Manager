package com.managmnet.staffie.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.managmnet.staffie.R;
import com.managmnet.staffie.admin.AdminScreen;
import com.managmnet.staffie.employee.navigationfragments.Employee_Attendance;
import com.managmnet.staffie.employee.navigationfragments.Employee_Salary_Check;
import com.managmnet.staffie.employee.navigationfragments.Employee_Screen_Fragment;
import com.managmnet.staffie.employee.viewpager.viewPagerAdapter;
import com.managmnet.staffie.login.MainActivity;
import com.managmnet.staffie.manager.fragments.ManagerAdminTasks;
import com.managmnet.staffie.manager.fragments.ManagerTaskView;
import com.managmnet.staffie.manager.navigationfragments.Manager_Salary_Check;
import com.managmnet.staffie.manager.navigationfragments.Manager_Take_Attendance;


public class EmployeeScreen extends AppCompatActivity {


    TabLayout tabLayout;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nv;
    BottomNavigationView bv;
    public BottomAppBar bottomAppBar;
    public Toolbar toolbar;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_screen);

        bottomAppBar = findViewById(R.id.bottom_menu);

        toolbar = findViewById(R.id.emp_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setVisibility(View.VISIBLE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.emp_screen_navigation_menu,new EmployeeTaskToComplete());
        ft.commit();

        drawerLayout = findViewById(R.id.emp_drawer);
        nv = findViewById(R.id.emp_navigation_view);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        assert user != null;


        bv = (BottomNavigationView) findViewById(R.id.bottom_nv);
        bv.setBackground(null);

        bv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.emp_assigned_task)
                {
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.emp_screen_navigation_menu,new EmployeeTaskToComplete());
                    ft1.addToBackStack(null);
                    ft1.commit();
                }
                else if(item.getItemId()==R.id.emp_completed_task)
                {
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.emp_screen_navigation_menu,new EmployeeCompletedTasks());
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

                if(item.getItemId()==R.id.emp_menu_home)
                {
                    EmployeeTaskToComplete f = new EmployeeTaskToComplete();
                    FragmentManager ft = getSupportFragmentManager();
                    ft.beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.emp_screen_navigation_menu,f)
                            .commit();
                    drawerLayout.closeDrawer(GravityCompat.START);

                }
                else if(item.getItemId()==R.id.emp_menu_attendance)
                {

                    Employee_Attendance f = new Employee_Attendance();
                    FragmentManager ft = getSupportFragmentManager();
                    ft.beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.emp_screen_navigation_menu,f)
                            .commit();
                    drawerLayout.closeDrawer(GravityCompat.START);

                }
                else if(item.getItemId()==R.id.emp_menu_salary_check)
                {
                    Employee_Salary_Check f = new Employee_Salary_Check();
                    FragmentManager ft = getSupportFragmentManager();
                    ft.beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.emp_screen_navigation_menu,f)
                            .commit();

                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(item.getItemId()==R.id.emp_menu_log_out)
                {
                    auth.signOut();
                    Toast.makeText(EmployeeScreen.this, "You have logged out!", Toast.LENGTH_SHORT).show();

                    SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = pref.edit();
                    ed.putBoolean("IsLogin",false);
                    ed.putString("user_type",null);
                    ed.apply();


                    drawerLayout.closeDrawer(GravityCompat.START);
                    Intent i = new Intent(EmployeeScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }


                return true;
            }

        });


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