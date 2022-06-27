package hcmute.edu.vn.foody_18.Home.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hcmute.edu.vn.foody_18.Home.TabNearMeFragment;
import hcmute.edu.vn.foody_18.Home.TabRecentViewFragment;

public class HomeViewPagerAdapter extends FragmentStateAdapter {

    public HomeViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public HomeViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new TabNearMeFragment();
        }
        return new TabRecentViewFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}