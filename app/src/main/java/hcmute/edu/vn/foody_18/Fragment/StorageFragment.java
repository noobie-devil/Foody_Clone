package hcmute.edu.vn.foody_18.Fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import hcmute.edu.vn.foody_18.R;
import hcmute.edu.vn.foody_18.Storage.Adapter.StorageViewPagerAdapter;

public class StorageFragment extends Fragment {

    private TabLayout storageTabLayout;
    private ViewPager2 viewPager2;

    private View view;

    public StorageFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_storage, container, false);
        mapping();

        StorageViewPagerAdapter adapter = new StorageViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        String[] tabTitles = {"Tất cả", "Địa điểm", "Hình ảnh", "Bài viết"};
        new TabLayoutMediator(storageTabLayout, viewPager2, (storageTabLayout, position) ->
                storageTabLayout.setText(tabTitles[position])).attach();

        return view;
    }

    public void mapping() {
        storageTabLayout    = view.findViewById(R.id.storage_tab_layout);
        viewPager2          = view.findViewById(R.id.storage_view_pager);
    }
}