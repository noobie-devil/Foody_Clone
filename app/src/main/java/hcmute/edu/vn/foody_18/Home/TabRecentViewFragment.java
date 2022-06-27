package hcmute.edu.vn.foody_18.Home;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hcmute.edu.vn.foody_18.Database.DatabaseHandler;
import hcmute.edu.vn.foody_18.Home.Adapter.TabRecentViewAdapter;
import hcmute.edu.vn.foody_18.Model.Restaurant;
import hcmute.edu.vn.foody_18.R;

public class TabRecentViewFragment extends Fragment {

    RecyclerView tabRecentViewRecyclerView;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Restaurant> restaurantArrayList;
    View view;

    TabRecentViewAdapter tabRecentViewAdapter;

    public TabRecentViewFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_recent_view, container, false);
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance(getActivity());

        restaurantArrayList = new ArrayList<>();
        restaurantArrayList = databaseHandler.getAllRestaurants();

        tabRecentViewRecyclerView = view.findViewById(R.id.rcv_tab_recent_view);
        tabRecentViewAdapter = new TabRecentViewAdapter(getActivity().getApplicationContext(), getActivity(), restaurantArrayList);
        layoutManager = new GridLayoutManager(getActivity(), 2);

        tabRecentViewRecyclerView.setLayoutManager(layoutManager);
        tabRecentViewRecyclerView.setAdapter(tabRecentViewAdapter);
        tabRecentViewAdapter.setData(restaurantArrayList);

        return view;
    }
}