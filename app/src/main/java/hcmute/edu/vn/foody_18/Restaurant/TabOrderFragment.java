package hcmute.edu.vn.foody_18.Restaurant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hcmute.edu.vn.foody_18.Database.DatabaseHandler;
import hcmute.edu.vn.foody_18.Model.Menu;
import hcmute.edu.vn.foody_18.Model.Order;
import hcmute.edu.vn.foody_18.Model.OrderDetail;
import hcmute.edu.vn.foody_18.R;
import hcmute.edu.vn.foody_18.Restaurant.Adapter.FoodAdapter;
import hcmute.edu.vn.foody_18.Restaurant.Adapter.MenuAdapter;
import hcmute.edu.vn.foody_18.SignInActivity;

public class TabOrderFragment extends Fragment implements FoodAdapter.FoodInteractionListener {

    RecyclerView menuRecyclerView;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Menu> menuArrayList;
    private HashMap<Integer, OrderDetail> orderDetailHashMap;
    SharedPreferences sharedPreferences;

    DatabaseHandler databaseHandler;
    MenuAdapter menuAdapter;
    private Order currentOrder;
    View view;

    private static final String ARG_RESTAURANT_ID = "restaurantId";
    private int restaurantId;
    private int currentUserId;

    TabOrderInteractionListener interactionListener;

    public interface TabOrderInteractionListener {
        public void handleOrderButton(int quantity);
        public void showOrderButton();
        public void hideOrderButton();
    }

    public TabOrderFragment() { }

    public TabOrderFragment(TabOrderInteractionListener interactionListener) {
        this.interactionListener = interactionListener;
    }

    public static TabOrderFragment newInstance(int restaurantId) {
        TabOrderFragment fragment = new TabOrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RESTAURANT_ID, restaurantId);
        fragment.setArguments(args);
        return fragment;
    }

    public static TabOrderFragment newInstance(int restaurantId, TabOrderInteractionListener interactionListener) {
        TabOrderFragment fragment = new TabOrderFragment(interactionListener);
        Bundle args = new Bundle();
        args.putInt(ARG_RESTAURANT_ID, restaurantId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = requireContext().getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        Log.d("userName", String.valueOf(sharedPreferences.getString("userName", "null")));
        Log.d("email", String.valueOf(sharedPreferences.getString("email", "null")));
        databaseHandler = DatabaseHandler.getInstance(getActivity());

        currentOrder = null;
        orderDetailHashMap = new HashMap<>();
        currentUserId = sharedPreferences.getInt("userId", 0);
        Log.d("currentUserId", String.valueOf(currentUserId));

        if (getArguments() != null) {
            restaurantId = getArguments().getInt(ARG_RESTAURANT_ID);
        }
        Log.d("currentUserId", String.valueOf(currentUserId));
        Log.d("restaurantId", String.valueOf(restaurantId));
        if (currentUserId != 0 && restaurantId != 0) {
            currentOrder = databaseHandler.checkExistNotPaidOrder(currentUserId, restaurantId);
            if (currentOrder != null) {
                Log.d("currentOrder", currentOrder.toString());
                orderDetailHashMap = databaseHandler.getAllOrderDetailsInOrder(currentOrder.getId());
                if (orderDetailHashMap.size() < 0) {
                    interactionListener.hideOrderButton();
                    Log.d("orderDetails size:", String.valueOf(orderDetailHashMap.size()));
                } else {
                    interactionListener.showOrderButton();
                    for (Map.Entry<Integer, OrderDetail> set: orderDetailHashMap.entrySet()) {
                        Log.d("orderdetail: ", set.getValue().toString());
                    }
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_order, container, false);

        if (restaurantId != 0) {
            menuArrayList = new ArrayList<>();
            menuArrayList = databaseHandler.getAllMenuFoodsByIdRestaurant(restaurantId);
            menuRecyclerView = view.findViewById(R.id.rcv_foods_menu);

            menuAdapter = new MenuAdapter(getActivity().getApplicationContext(), restaurantId, this);
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

            menuRecyclerView.setLayoutManager(layoutManager);
            menuRecyclerView.setAdapter(menuAdapter);
            menuAdapter.setData(menuArrayList, orderDetailHashMap);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("TabOrderFragment", "Restaurant Id = " + restaurantId);
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        boolean logged = intent.getBooleanExtra("logged", false);
                    }
                }
            }
    );

    @Override
    public void increaseBtn(int currentQuantity, OrderDetail orderDetail) {
        if (sharedPreferences.getString("userName", null) == null) {
            Intent intent = new Intent(getActivity(), SignInActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("backToPrevious", true);
            intent.putExtras(bundle);
            launcher.launch(intent);
            Toast.makeText(getActivity(), "Bạn phải đăng nhập mới sử dụng được chức năng này", Toast.LENGTH_LONG).show();
        } else {
            if (orderDetail.getId() != 0) {
                Log.d("updateOrderDetails", String.valueOf(orderDetail.getFood().getFoodId()));
                databaseHandler.updateOrderDetail(orderDetail);

                if (orderDetailHashMap == null) {
                    orderDetailHashMap = new HashMap<>();
                    orderDetailHashMap.put(orderDetail.getFood().getFoodId(), orderDetail);
                } else {
                    orderDetailHashMap.put(orderDetail.getFood().getFoodId(), orderDetail);
                }
                menuAdapter.setOrderDetailHashMap(orderDetailHashMap);
            } else {
                if (currentOrder == null) {
                    currentOrder = new Order();
                    currentOrder = databaseHandler.createNewOrder(currentUserId, restaurantId);
                }
                orderDetail.setOrderId(currentOrder.getId());
                orderDetail = databaseHandler.addOrderDetail(orderDetail);

                if (orderDetail != null) {
                    Toast.makeText(getActivity(), "Đã thêm món vào giỏ", Toast.LENGTH_LONG).show();
                    orderDetailHashMap.put(orderDetail.getFood().getFoodId(), orderDetail);
                    menuAdapter.setOrderDetailHashMap(orderDetailHashMap);
                }
            }

            interactionListener.handleOrderButton(currentQuantity);
        }
    }

    @Override
    public void decreaseBtn(int currentQuantity, OrderDetail orderDetail) {
        if (currentQuantity != 0) {
            databaseHandler.updateOrderDetail(orderDetail);
            orderDetailHashMap.put(orderDetail.getFood().getFoodId(), orderDetail);
            menuAdapter.setOrderDetailHashMap(orderDetailHashMap);
        } else {
            databaseHandler.deleteOrderDetail(orderDetail);
            orderDetailHashMap.remove(orderDetail.getFood().getFoodId());
            menuAdapter.setOrderDetailHashMap(orderDetailHashMap);
        }

        interactionListener.handleOrderButton(currentQuantity);
    }
}