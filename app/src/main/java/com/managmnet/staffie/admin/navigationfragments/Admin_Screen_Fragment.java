package com.managmnet.staffie.admin.navigationfragments;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.managmnet.staffie.R;


public class Admin_Screen_Fragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v=  inflater.inflate(R.layout.fragment_admin__screen_, container, false);
//        tabLayout = v.findViewById(R.id.admin_tablayout_view);
//        viewPager = v.findViewById(R.id.admin_viewpager);


        drawerLayout = v.findViewById(R.id.adm_drawer);
//        nv = v.findViewById(R.id.adm_navigation_view);

//        Toolbar toolbar = v.findViewById(R.id.adm_toolbar);
//        setSupportActionBar(toolbar);

//        toggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.open,R.string.close);
//        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();


//        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item)
//            {
//                if(item.getItemId()==R.id.adm_menu_attendance)
//                {
//                    Toast.makeText(getActivity(), "Attendance", Toast.LENGTH_SHORT).show();
//                    drawerLayout.closeDrawer(GravityCompat.START);
//
//                }
//                else if(item.getItemId()==R.id.adm_menu_comment)
//                {
//                    Toast.makeText(getActivity(), "Comment", Toast.LENGTH_SHORT).show();
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//                else if(item.getItemId()==R.id.adm_menu_settings)
//                {
//                    Toast.makeText(getActivity(), "Settings", Toast.LENGTH_SHORT).show();
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//                else if(item.getItemId()==R.id.adm_menu_help)
//                {
//                    Toast.makeText(getActivity(), "Help", Toast.LENGTH_SHORT).show();
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//                return true;
//            }
//
//        });


//        viewPagerAdapter adapter = new viewPagerAdapter(getChildFragmentManager());
//
//        adapter.addFragment(new AdminTaskView(),"Tasks");
//        adapter.addFragment(new AdminCreateTaskView(),"Create Task");
//
//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);
//
//        tabLayout.getTabAt(0).setIcon(R.drawable.working_task_icon);
//        tabLayout.getTabAt(1).setIcon(R.drawable.create_task_icon);
//        tabLayout.getTabAt(0).setText("Working Tasks");
//        tabLayout.getTabAt(1).setText("Create Task");
//
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

}