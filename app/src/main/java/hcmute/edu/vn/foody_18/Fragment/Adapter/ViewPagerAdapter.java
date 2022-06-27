package hcmute.edu.vn.foody_18.Fragment.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hcmute.edu.vn.foody_18.Fragment.AccountFragment;
import hcmute.edu.vn.foody_18.Fragment.HomeFragment;
import hcmute.edu.vn.foody_18.Fragment.NotificationFragment;
import hcmute.edu.vn.foody_18.Fragment.OrderFragment;
import hcmute.edu.vn.foody_18.Fragment.StorageFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position) {
            case 1:
                return new StorageFragment();
            case 2:
                return new OrderFragment();
            case 3:
                return new NotificationFragment();
            case 4:
                return new AccountFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}