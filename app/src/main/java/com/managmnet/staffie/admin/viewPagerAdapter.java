package com.managmnet.staffie.admin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class viewPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    public viewPagerAdapter (@NonNull FragmentManager fm)
    {
        super(fm);
    }

    public void addFragment(Fragment fragment,String title)
    {
        fragmentArrayList.add(fragment);
        name.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
    @NonNull
    @Override
    public CharSequence getPageTitle(int position)
    {
        return name.get(position);
    }
}
