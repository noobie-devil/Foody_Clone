package hcmute.edu.vn.foody_18.Restaurant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import hcmute.edu.vn.foody_18.Model.Menu;
import hcmute.edu.vn.foody_18.Model.OrderDetail;
import hcmute.edu.vn.foody_18.R;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    Context context;
    ArrayList<Menu> menuList;
    HashMap<Integer, OrderDetail> orderDetailHashMap;

    FoodAdapter foodAdapter;
    RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    private int restaurantId;

    FoodAdapter.FoodInteractionListener interactionListener;

    public MenuAdapter(ArrayList<Menu> menuList) {
        this.menuList = menuList;
    }

    public MenuAdapter(Context context, int restaurantId, FoodAdapter.FoodInteractionListener interactionListener) {
        this.context = context;
        this.restaurantId = restaurantId;
        this.interactionListener = interactionListener;
    }

    public void setData(ArrayList<Menu> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }

    public void setData(ArrayList<Menu> menuList, HashMap<Integer, OrderDetail> orderDetailHashMap) {
        this.menuList = menuList;
        this.orderDetailHashMap = orderDetailHashMap;
        notifyDataSetChanged();
    }

    public void setOrderDetailHashMap(HashMap<Integer, OrderDetail> orderDetailHashMap) {
        this.orderDetailHashMap = orderDetailHashMap;
        this.foodAdapter.setData(orderDetailHashMap);
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_foods_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu menu = menuList.get(position);
        holder.txtMenuTitle.setText(menu.getTitle().concat(" (" + menu.getQuantity() + ")"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.rcvListFoods.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(menu.getFoods().size());
        foodAdapter = new FoodAdapter(context, interactionListener, restaurantId);

        holder.rcvListFoods.setLayoutManager(layoutManager);
        foodAdapter.setData(menu.getFoods(), orderDetailHashMap);
        holder.rcvListFoods.setAdapter(foodAdapter);
        holder.rcvListFoods.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        if (menuList != null) {
            return menuList.size();
        }
        return 0;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        private TextView txtMenuTitle;
        private RecyclerView rcvListFoods;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMenuTitle = itemView.findViewById(R.id.txv_menu_title);
            rcvListFoods = itemView.findViewById(R.id.rcv_list_foods);
        }
    }
}