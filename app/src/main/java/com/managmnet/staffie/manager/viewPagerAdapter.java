package com.managmnet.staffie.manager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class viewPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    public viewPagerAdapter(@NonNull FragmentManager fm)
    {
        super(fm);
    }

    @Override
    @NonNull
    public Fragment getItem(int position)
    {
        return fragmentArrayList.get(position);
    }

    public void addFragment(Fragment fragment, String title)
    {
        fragmentArrayList.add(fragment);
        name.add(title);
    }

    @Override
    public int getCount()
    {
        return fragmentArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return name.get(position);
    }
}
