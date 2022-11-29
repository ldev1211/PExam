package ldev.ptithcm.pexam.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import ldev.ptithcm.pexam.ui.TabListDocsFrag;
import ldev.ptithcm.pexam.ui.TabListExFrag;

public class ViewPagerListExAdapter extends FragmentStateAdapter {

    ViewPager2 viewPager2;

    public ViewPagerListExAdapter(@NonNull FragmentActivity fragmentActivity,ViewPager2 viewPager2) {
        super(fragmentActivity);
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new TabListExFrag();
            case 1:
                return new TabListDocsFrag();
            default:
                return new TabListExFrag();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
