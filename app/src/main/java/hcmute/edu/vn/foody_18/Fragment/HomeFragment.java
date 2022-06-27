package hcmute.edu.vn.foody_18.Fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import hcmute.edu.vn.foody_18.R;
import hcmute.edu.vn.foody_18.Home.Adapter.HomeViewPagerAdapter;
import hcmute.edu.vn.foody_18.SearchActivity;

public class HomeFragment extends Fragment {

    private TabLayout homeTabLayout;
    private TextView textSearch;
    private ViewPager2 viewPager2;

    private View view;

    public HomeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mapping();

        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        String[] tabTitles = {"Xem gần đây", "Gần tôi"};
        new TabLayoutMediator(homeTabLayout, viewPager2, true, false, (tab, position) -> {
            tab.setText(tabTitles[position]);
            if(tab != null) {
                TextView tabTextView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.layout_tab_item, homeTabLayout, false);
                tabTextView.setText(tab.getText());
                tabTextView.setTextAppearance(getContext(), position == 0 ? R.style.TextAppearance_Tabs_style_1_Selected : R.style.TextAppearance_Tabs_style_1);
                tab.setCustomView(tabTextView);
            }
        }).attach();

        homeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab selectedTab) {
                int tabCount = homeTabLayout.getTabCount();
                for(int i = 0; i < tabCount; i++) {
                    TabLayout.Tab tab = homeTabLayout.getTabAt(i);
                    View tabView = tab != null ? tab.getCustomView() : null;
                    if(tabView instanceof TextView) {
                        ((TextView) tabView).setTextAppearance(getContext(), selectedTab.equals(tab)
                                ? R.style.TextAppearance_Tabs_style_1_Selected
                                : R.style.TextAppearance_Tabs_style_1
                        );
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
        textSearch.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        return view;
    }

    public void mapping() {
        homeTabLayout   = view.findViewById(R.id.home_tab_layout);
        textSearch      = view.findViewById(R.id.homeFragment_textview_search);
        viewPager2      = view.findViewById(R.id.home_view_pager);
    }
}