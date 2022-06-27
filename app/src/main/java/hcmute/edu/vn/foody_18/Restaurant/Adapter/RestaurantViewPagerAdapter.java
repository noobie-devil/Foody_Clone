package hcmute.edu.vn.foody_18.Restaurant.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import hcmute.edu.vn.foody_18.Restaurant.TabCommentFragment;
import hcmute.edu.vn.foody_18.Restaurant.TabInfoFragment;
import hcmute.edu.vn.foody_18.Restaurant.TabOrderFragment;

public class RestaurantViewPagerAdapter extends FragmentStateAdapter {

    private int restaurantId;

    TabOrderFragment.TabOrderInteractionListener interactionListener;

    public RestaurantViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int restaurantId) {
        super(fragmentActivity);
        this.restaurantId = restaurantId;
    }

    public RestaurantViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int restaurantId, TabOrderFragment.TabOrderInteractionListener interactionListener) {
        super(fragmentActivity);
        this.restaurantId = restaurantId;
        this.interactionListener = interactionListener;
    }

    public RestaurantViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public RestaurantViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new TabCommentFragment();
            case 2:
                return new TabInfoFragment();
            default:
                if (restaurantId != 0) {
                    return TabOrderFragment.newInstance(restaurantId, interactionListener);
                }
                return new TabOrderFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}