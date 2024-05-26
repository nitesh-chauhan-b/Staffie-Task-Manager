package com.managmnet.staffie.employee.navigationfragments;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.managmnet.staffie.R;
import com.managmnet.staffie.employee.EmployeeCompletedTasks;
import com.managmnet.staffie.employee.EmployeeTaskToComplete;
import com.managmnet.staffie.employee.viewpager.viewPagerAdapter;


public class Employee_Screen_Fragment extends Fragment {
    TabLayout tabLayout;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_employee__screen_,null);

        getActivity().getSupportFragmentManager().popBackStack();
        Toolbar toolbar = v.findViewById(R.id.emp_toolbar);
//        setSupportActionBar(toolbar);

        drawerLayout = v.findViewById(R.id.emp_drawer);
        nv = v.findViewById(R.id.emp_navigation_view);

        toggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.open,R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

//
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {


                    if(item.getItemId()==R.id.emp_menu_home)
                    {
                        Employee_Screen_Fragment f = new Employee_Screen_Fragment();
                        FragmentManager ft = getActivity().getSupportFragmentManager();
                        ft.beginTransaction()
                                .replace(R.id.emp_screen_navigation_menu,f)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);

                    }

                    else if(item.getItemId()==R.id.emp_menu_attendance)
                    {
                        Toast.makeText(getActivity(), "Attendance", Toast.LENGTH_SHORT).show();

                        Employee_Attendance f = new Employee_Attendance();
                        FragmentManager ft = getActivity().getSupportFragmentManager();
                        ft.beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.emp_screen_navigation_menu,f)
                                .commit();

                    }
                    else if(item.getItemId()==R.id.emp_menu_salary_check)
                    {
                        Employee_Salary_Check f = new Employee_Salary_Check();
                        FragmentManager ft = getActivity().getSupportFragmentManager();
                        ft.beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.emp_screen_navigation_menu,f)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
//                    else if(item.getItemId()==R.id.emp_menu_settings)
//                    {
//                        Toast.makeText(getActivity(), "Settings", Toast.LENGTH_SHORT).show();
//
//                        drawerLayout.closeDrawer(GravityCompat.START);
//                    }
//                    else if(item.getItemId()==R.id.emp_menu_help)
//                    {
//                        Toast.makeText(getActivity(), "Help", Toast.LENGTH_SHORT).show();
//
//                        drawerLayout.closeDrawer(GravityCompat.START);
//                    }

                return true;
            }

        });


        tabLayout = v.findViewById(R.id.emp_tablayout);
        final ViewPager viewPager = v.findViewById(R.id.emp_viewpager);
        viewPager.setOffscreenPageLimit(2);

        viewPagerAdapter viewadapter = new viewPagerAdapter(getChildFragmentManager());

        viewadapter.addFragment(new EmployeeTaskToComplete(),"Assigned Tasks");
        viewadapter.addFragment(new EmployeeCompletedTasks(),"Completed Task");

        viewPager.setAdapter(viewadapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.assigned_task);
        tabLayout.getTabAt(1).setIcon(R.drawable.completed_tasks);

        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        int tabIconColor = ContextCompat.getColor(getActivity(), R.color.tablecolor);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(getActivity(), R.color.defaulttextcolor);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );

        return v;
    }

    public void onBackPressed() {
        if(getActivity().getSupportFragmentManager().getBackStackEntryCount()>0)
        {
            getActivity().getSupportFragmentManager().popBackStack();
        }
        else{
            super.getActivity().onBackPressed();
        }

    }
}