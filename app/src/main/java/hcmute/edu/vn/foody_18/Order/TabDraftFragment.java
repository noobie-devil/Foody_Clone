package hcmute.edu.vn.foody_18.Order;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hcmute.edu.vn.foody_18.Database.DatabaseHandler;
import hcmute.edu.vn.foody_18.Model.Order;
import hcmute.edu.vn.foody_18.Order.Adapter.OrderDraftAdapter;
import hcmute.edu.vn.foody_18.R;

public class TabDraftFragment extends Fragment {

    RecyclerView tabDraftRecyclerView;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Order> orderArrayList;
    View view;

    OrderDraftAdapter orderDraftAdapter;

    public TabDraftFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_draft, container, false);
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.tabDraftFragment_swipeRefresh);
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance(getActivity());

        orderArrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        orderArrayList = databaseHandler.getAllDraftOrders(sharedPreferences.getInt("userId", 0));

        tabDraftRecyclerView = view.findViewById(R.id.tabDraftFragment_recyclerview);
        orderDraftAdapter = new OrderDraftAdapter(getActivity().getApplicationContext(), getActivity(), orderArrayList);
        layoutManager = new GridLayoutManager(getActivity(), 1);

        tabDraftRecyclerView.setLayoutManager(layoutManager);
        tabDraftRecyclerView.setAdapter(orderDraftAdapter);
        orderDraftAdapter.setData(orderArrayList);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            orderArrayList = databaseHandler.getAllDraftOrders(sharedPreferences.getInt("userId", 0));
            orderDraftAdapter.setData(orderArrayList);
            swipeRefreshLayout.setRefreshing(false);
        });

        return view;
    }
}