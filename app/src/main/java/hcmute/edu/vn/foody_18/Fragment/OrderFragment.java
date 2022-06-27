package hcmute.edu.vn.foody_18.Fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import hcmute.edu.vn.foody_18.Order.Adapter.OrderViewPagerAdapter;
import hcmute.edu.vn.foody_18.R;

public class OrderFragment extends Fragment {

    private TabLayout orderLayout;
    private ViewPager2 viewPager2;

    private View view;

    public OrderFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        mapping();

        OrderViewPagerAdapter adapter = new OrderViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        String[] tabTitles = {"Đang đến", "Lịch sử", "Đơn nháp"};
        new TabLayoutMediator(orderLayout, viewPager2, true, false, ((orderTabLayout, position) ->
                orderTabLayout.setText(tabTitles[position]))).attach();

        return view;
    }

    public void mapping() {
        orderLayout = view.findViewById(R.id.orderFragment_tabLayout);
        viewPager2  = view.findViewById(R.id.orderFragment_viewPager2);
    }
}