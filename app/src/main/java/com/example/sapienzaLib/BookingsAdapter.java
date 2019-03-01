package com.example.sapienzaLib;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class BookingsAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public BookingsAdapter(FragmentManager fm) {
        super(fm);
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position)  {
        if (position == 0)
            return new PopBookingsFragment();
        else if(position == 1)
            return new ExpBookingsFragment();
        else
            return new YourBookingsFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                //return mContext.getString(R.string.expiring);
                return "Most Pop";
            case 1:
                //return mContext.getString(R.string.your);
                return "Expiring";
            case 2:
                //return mContext.getString(R.string.your);
                return "Freshly Booked";
            default:
                return null;
        }
    }



}
