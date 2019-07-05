package m.derakhshan.travelinkapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class Pager extends FragmentStatePagerAdapter {


    private int tabCount;


    Pager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 2:
                return new Tab_FAQs();
            case 0:
                return new Tab_NewTickets();
            case 1:
                return new Tab_Tickets();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}