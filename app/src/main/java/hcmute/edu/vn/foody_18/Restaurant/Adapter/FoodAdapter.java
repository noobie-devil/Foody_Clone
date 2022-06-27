package hcmute.edu.vn.foody_18.Restaurant.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hcmute.edu.vn.foody_18.Model.Food;
import hcmute.edu.vn.foody_18.Model.OrderDetail;
import hcmute.edu.vn.foody_18.R;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    Context context;
    FoodInteractionListener interactionListener;
    private ArrayList<Food> foodList;
    private HashMap<Integer, OrderDetail> orderDetailHashMap;

    SharedPreferences sharedPreferences;

    private int restaurantId;
    private int quantity;
    private double price, priceTotal;

    public FoodAdapter(Context context, FoodInteractionListener interactionListener, int restaurantId) {
        this.context = context;
        this.interactionListener = interactionListener;
        this.restaurantId = restaurantId;
    }

    public FoodAdapter(Context context, FoodInteractionListener interactionListener, ArrayList<Food> foodList, int restaurantId) {
        this.context = context;
        this.interactionListener = interactionListener;
        this.foodList = foodList;
        this.restaurantId = restaurantId;
    }

    public void setData(ArrayList<Food> foodList, HashMap<Integer, OrderDetail> orderDetailHashMap) {
        this.foodList = foodList;
        this.orderDetailHashMap = orderDetailHashMap;

        if (orderDetailHashMap != null) {
            for(Map.Entry<Integer, OrderDetail> set: orderDetailHashMap.entrySet()) {
                Log.d("FoodAdapter: ", set.getValue().toString());
            }
        }
        notifyDataSetChanged();
    }

    public void setData(HashMap<Integer, OrderDetail> orderDetailHashMap) {
        this.orderDetailHashMap = orderDetailHashMap;

        if (orderDetailHashMap != null) {
            for (Map.Entry<Integer, OrderDetail> set: orderDetailHashMap.entrySet()) {
                Log.d("FoodAdapter update: ", set.getValue().toString());
            }
        }
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sharedPreferences = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);

        Picasso.get().load(food.getThumbImage()).into(holder.imageFood);
        holder.textName.setText(food.getName());
        holder.textDescription.setText(food.getDescription());
        price = Double.parseDouble(food.getPrice());
        DecimalFormat formatter = new DecimalFormat("#,###");
        holder.textPrice.setText(formatter.format(price).concat("đ"));

        holder.buttonDecrease.setVisibility(View.INVISIBLE);
        holder.textQuantity.setVisibility(View.INVISIBLE);
        if (orderDetailHashMap != null) {
            if (orderDetailHashMap.containsKey(food.getFoodId())) {
                Log.d("ContainsKey", orderDetailHashMap.get(food.getFoodId()).toString());
                holder.buttonDecrease.setVisibility(View.VISIBLE);
                holder.textQuantity.setVisibility(View.VISIBLE);

                OrderDetail orderDetail = orderDetailHashMap.get(food.getFoodId());
                quantity = orderDetail.getQuantity();
                holder.textQuantity.setText(String.valueOf(quantity));
                priceTotal = price * quantity;
                holder.textPrice.setText(formatter.format(priceTotal).concat("đ"));
            }
        }

        holder.buttonIncrease.setOnClickListener(view -> {
            if (sharedPreferences.getString("userName", null) != null) {
                quantity = Integer.parseInt(holder.textQuantity.getText().toString());
                quantity++;

                if (quantity == 100) {
                    Toast.makeText(context.getApplicationContext(), "Số lượng đặt tối đa là 99", Toast.LENGTH_LONG).show();
                } else {
                    holder.buttonDecrease.setVisibility(View.VISIBLE);
                    holder.textQuantity.setVisibility(View.VISIBLE);

                    holder.textQuantity.setText(String.valueOf(quantity));
                    price = Double.parseDouble(food.getPrice());
                    priceTotal = price * quantity;
                    holder.textPrice.setText(formatter.format(priceTotal).concat("đ"));

                    OrderDetail orderDetail = new OrderDetail();
                    if (orderDetailHashMap != null) {
                        if (orderDetailHashMap.containsKey(food.getFoodId())) {
                            orderDetail = orderDetailHashMap.get(food.getFoodId());
                            orderDetail.setQuantity(quantity);
                            orderDetail.setFood(food);
                            orderDetailHashMap.put(food.getFoodId(), orderDetail);
                        } else {
                            orderDetail.setId(0);
                            orderDetail.setFood(food);
                            orderDetail.setQuantity(quantity);
                        }
                    } else {
                        orderDetail.setId(0);
                        orderDetail.setFood(food);
                        orderDetail.setQuantity(quantity);
                    }
                    Log.d("food:", food.toString());

                    interactionListener.increaseBtn(quantity, orderDetail);
                }
            } else {
                interactionListener.increaseBtn(0, null);
            }
        });
        holder.buttonDecrease.setOnClickListener(view -> {
            quantity = Integer.parseInt(holder.textQuantity.getText().toString());
            quantity --;

            if (quantity == 0) {
                holder.buttonDecrease.setVisibility(View.INVISIBLE);
                holder.textQuantity.setVisibility(View.INVISIBLE);
                holder.textQuantity.setText(String.valueOf(0));
            } else {
                holder.textQuantity.setText(String.valueOf(quantity));
                price = Double.parseDouble(food.getPrice());
                priceTotal = price * quantity;
                holder.textPrice.setText(formatter.format(priceTotal).concat("đ"));
            }
            OrderDetail orderDetail = new OrderDetail();
            orderDetail = orderDetailHashMap.get(food.getFoodId());
            orderDetail.setQuantity(quantity);
            orderDetail.setFood(food);

            interactionListener.decreaseBtn(quantity, orderDetail);
        });
    }

    @Override
    public int getItemCount() {
        if (foodList != null) {
            return foodList.size();
        }
        return 0;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageFood;
        private MaterialButton buttonIncrease, buttonDecrease;
        private TextView textName, textDescription, textPrice, textQuantity;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            imageFood       = itemView.findViewById(R.id.foodLayout_image_food);
            buttonIncrease  = itemView.findViewById(R.id.foodLayout_button_increase);
            buttonDecrease  = itemView.findViewById(R.id.foodLayout_button_decrease);
            textName        = itemView.findViewById(R.id.foodLayout_textview_name);
            textDescription = itemView.findViewById(R.id.foodLayout_textview_description);
            textPrice       = itemView.findViewById(R.id.foodLayout_textview_totalPrice);
            textQuantity    = itemView.findViewById(R.id.foodLayout_textview_quantity);
        }
    }

    public interface FoodInteractionListener {
        void increaseBtn(int currentQuantity, OrderDetail orderDetail);
        void decreaseBtn(int currentQuantity, OrderDetail orderDetail);
    }
}