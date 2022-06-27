package hcmute.edu.vn.foody_18.Order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import hcmute.edu.vn.foody_18.Order.Adapter.OrderAdapter;
import hcmute.edu.vn.foody_18.OrderActivity;
import hcmute.edu.vn.foody_18.R;

public class TabHistoryFragment extends Fragment implements OrderAdapter.OrderInteractionListener {

    RecyclerView tabHistoryRecyclerView;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Order> orderArrayList;
    View view;

    OrderAdapter orderAdapter;

    public TabHistoryFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_history, container, false);
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.tabHistoryFragment_swipeRefresh);
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance(getActivity());

        orderArrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        orderArrayList = databaseHandler.getAllPaidOrders(sharedPreferences.getInt("userId", 0));

        tabHistoryRecyclerView = view.findViewById(R.id.tabHistoryFragment_recyclerview);
        orderAdapter = new OrderAdapter(getActivity().getApplicationContext(), this, orderArrayList);
        layoutManager = new GridLayoutManager(getActivity(), 1);

        tabHistoryRecyclerView.setLayoutManager(layoutManager);
        tabHistoryRecyclerView.setAdapter(orderAdapter);
        orderAdapter.setData(orderArrayList);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            orderArrayList = databaseHandler.getAllPaidOrders(sharedPreferences.getInt("userId", 0));
            orderAdapter.setData(orderArrayList);
            swipeRefreshLayout.setRefreshing(false);
        });

        return view;
    }

    @Override
    public void clickListener(Order order) {
        Intent details = new Intent(getActivity(), OrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("orderId", order.getId());
        details.putExtras(bundle);
        launcher.launch(details);
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        int currentStatus = intent.getIntExtra("currentStatus", 1);
                    }
                }
            }
    );
}