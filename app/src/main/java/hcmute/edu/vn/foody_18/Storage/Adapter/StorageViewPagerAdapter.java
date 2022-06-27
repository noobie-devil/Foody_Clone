package hcmute.edu.vn.foody_18.Storage.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hcmute.edu.vn.foody_18.Storage.TabAllFragment;
import hcmute.edu.vn.foody_18.Storage.TabImageFragment;
import hcmute.edu.vn.foody_18.Storage.TabLocationFragment;
import hcmute.edu.vn.foody_18.Storage.TabPostFragment;

public class StorageViewPagerAdapter extends FragmentStateAdapter {

    public StorageViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public StorageViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position) {
            case 1:
                return new TabLocationFragment();
            case 2:
                return new TabImageFragment();
            case 3:
                return new TabPostFragment();
            default:
                return new TabAllFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}