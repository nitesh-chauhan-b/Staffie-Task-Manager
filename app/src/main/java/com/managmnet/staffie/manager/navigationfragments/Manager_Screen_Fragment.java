package com.managmnet.staffie.manager.navigationfragments;

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
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.managmnet.staffie.R;
import com.managmnet.staffie.employee.viewpager.viewPagerAdapter;
import com.managmnet.staffie.manager.fragments.ManagerAdminTasks;
import com.managmnet.staffie.manager.fragments.ManagerCreateTaskView;
import com.managmnet.staffie.manager.fragments.ManagerTaskView;


public class Manager_Screen_Fragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View v=  inflater.inflate(R.layout.fragment_manager__screen_,null);
        tabLayout = v.findViewById(R.id.manager_tablayout);
        viewPager = v.findViewById(R.id.manager_viewpager);

        drawerLayout = v.findViewById(R.id.mng_drawer);
        nv = v.findViewById(R.id.mng_navigation_view);

        Toolbar toolbar = v.findViewById(R.id.mng_toolbar);
//        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.open,R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


//        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item)
//            {
//
//                if(item.getItemId()==R.id.mng_home)
//                {
//                    onBackPressed();
//                    Manager_Screen_Fragment f = new Manager_Screen_Fragment();
//                    FragmentManager ft = getActivity().getSupportFragmentManager();
//                    ft.beginTransaction()
//                            .addToBackStack(null)
//                            .replace(R.id.mng_screen_navigation_menu,f,null)
//                            .commit();
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//                else if(item.getItemId()==R.id.                                              ^
//                )
//                {
//                    Manager_Take_Attendance f = new Manager_Take_Attendance();
//                    FragmentManager ft = getActivity().getSupportFragmentManager();
//                    ft.beginTransaction()
//                            .addToBackStack(null)
//                            .replace(R.id.mng_screen_navigation_menu,f,null)
//                            .commit();
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//                else if(item.getItemId()==R.id.mng_menu_attendance)
//                {
//
//                    Manager_Attendace f = new Manager_Attendace();
//                    FragmentManager ft = getActivity().getSupportFragmentManager();
//                    ft.beginTransaction()
//                            .addToBackStack(null)
//                            .replace(R.id.mng_screen_navigation_menu,f,null)
//                            .commit();
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//                else if(item.getItemId()==R.id.mng_menu_salary_check)
//                {
//
//                    Manager_Salary_Check f = new Manager_Salary_Check();
//                    FragmentManager ft = getActivity().getSupportFragmentManager();
//                    ft.beginTransaction()
//                            .addToBackStack(null)
//                            .replace(R.id.mng_screen_navigation_menu,f,null)
//                            .commit();
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//                else if(item.getItemId()==R.id.mng_menu_settings)
//                {
//
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//                else if(item.getItemId()==R.id.mng_menu_help)
//                {
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//                return true;
//            }
//
//        });


//        viewPagerAdapter viewadapter = new viewPagerAdapter(getChildFragmentManager());
//
//        viewadapter.addFragment(new ManagerTaskView(),"Given Tasks");
//        viewadapter.addFragment(new ManagerAdminTasks(),"Tasks");
//        viewadapter.addFragment(new ManagerCreateTaskView(),"Create Task");
//
//
//        viewPager.setAdapter(viewadapter);
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.getTabAt(0).setIcon(R.drawable.given_task);
//        tabLayout.getTabAt(1).setIcon(R.drawable.working_task_icon);
//        tabLayout.getTabAt(2).setIcon(R.drawable.create_task_icon);

//        Intent i = getIntent();
//        String task_name = i.getStringExtra("task_name");
//        String task_start_date = i.getStringExtra("task_start_date");
//        String task_due_date = i.getStringExtra("task_due_date");
//        String task_priority = i.getStringExtra("task_priority");
//        String task_desc = i.getStringExtra("task_desc");
//
//
//
//        ManagerAdminTasks f = new ManagerAdminTasks();
//
//        Bundle task_details = new Bundle();
//        task_details.putString("task_name",task_name);
//        task_details.putString("task_start_date",task_start_date);
//        task_details.putString("task_due_date",task_due_date);
//        task_details.putString("task_priority",task_priority);
//        task_details.putString("task_desc",task_desc);
//
//        f.setArguments(task_details);
//
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();


//        ft.commitNowAllowingStateLoss();

//        tabLayout.setOnTabSelectedListener(
//                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
//
//                    @Override
//                    public void onTabSelected(TabLayout.Tab tab) {
//                        super.onTabSelected(tab);
//                        int tabIconColor = ContextCompat.getColor(getActivity(), R.color.tablecolor);
//                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                    }
//
//                    @Override
//                    public void onTabUnselected(TabLayout.Tab tab) {
//                        super.onTabUnselected(tab);
//                        int tabIconColor = ContextCompat.getColor(getActivity(), R.color.defaulttextcolor);
//                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                    }
//
//                    @Override
//                    public void onTabReselected(TabLayout.Tab tab) {
//                        super.onTabReselected(tab);
//                    }
//                }
//        );
        return v;
    }

    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for(int i=0; i<fm.getBackStackEntryCount(); ++i)
        {
            fm.popBackStack();
        }
        super.getActivity().onBackPressed();

    }
}