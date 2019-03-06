package com.example.sapienzaLib;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = getView().findViewById(R.id.viewPager);
        // Create an adapter that knows which fragment should be shown on each page
        BookingsAdapter adapterTab = new BookingsAdapter(getChildFragmentManager());
        // Set the adapter onto the view pager
        viewPager.setAdapter(adapterTab);
        //viewPager.setOffscreenPageLimit(2);
        // Give the TabLayout the ViewPager
        TabLayout tabLayout = getView().findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    static class BookingsAdapter extends FragmentPagerAdapter {

        private Context mContext;

        public BookingsAdapter(FragmentManager fm) {
            super(fm);
        }

        // This determines the fragment for each tab
        @Override
        public Fragment getItem(int position) {
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

}
