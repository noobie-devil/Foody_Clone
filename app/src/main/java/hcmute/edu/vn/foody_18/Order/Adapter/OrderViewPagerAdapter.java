package hcmute.edu.vn.foody_18.Order.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hcmute.edu.vn.foody_18.Order.TabComingFragment;
import hcmute.edu.vn.foody_18.Order.TabDraftFragment;
import hcmute.edu.vn.foody_18.Order.TabHistoryFragment;

public class OrderViewPagerAdapter extends FragmentStateAdapter {

    public OrderViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public OrderViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position) {
            case 1:
                return new TabHistoryFragment();
            case 2:
                return new TabDraftFragment();
            default:
                return new TabComingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}