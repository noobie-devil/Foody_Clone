package hcmute.edu.vn.foody_18.Order.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import hcmute.edu.vn.foody_18.Model.Food;
import hcmute.edu.vn.foody_18.Model.OrderDetail;
import hcmute.edu.vn.foody_18.R;

public class FoodOrderAdapter extends RecyclerView.Adapter<FoodOrderAdapter.FoodOrderHolder> {

    Context context;
    FragmentActivity activity;
    ArrayList<OrderDetail> orderDetailArrayList;

    double price;

    public FoodOrderAdapter(Context context, FragmentActivity activity, ArrayList<OrderDetail> orderDetailArrayList) {
        this.context = context;
        this.activity = activity;
        this.orderDetailArrayList = orderDetailArrayList;
    }

    public void setData(ArrayList<OrderDetail> orderDetailArrayList) {
        this.orderDetailArrayList = orderDetailArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_food, parent, false);
        return new FoodOrderAdapter.FoodOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodOrderHolder holder, int position) {
        OrderDetail orderDetail = orderDetailArrayList.get(position);
        Food food = new Food();
        food = orderDetail.getFood();

        Picasso.get().load(food.getThumbImage()).into(holder.imageFood);
        holder.textName.setText(food.getName());
        holder.textDescription.setText(food.getDescription());
        price = Double.parseDouble(food.getPrice()) * orderDetail.getQuantity();
        DecimalFormat formatter = new DecimalFormat("#,###");
        holder.textPrice.setText(formatter.format(price).concat("đ"));
        holder.textQuantity.setText(String.valueOf(orderDetail.getQuantity()));

        holder.buttonIncrease.setVisibility(View.INVISIBLE);
        holder.buttonDecrease.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        if (orderDetailArrayList != null) {
            return orderDetailArrayList.size();
        }
        return 0;
    }

    public class FoodOrderHolder extends RecyclerView.ViewHolder {
        private ImageView imageFood;
        private MaterialButton buttonIncrease, buttonDecrease;
        private TextView textName, textDescription, textPrice, textQuantity;

        public FoodOrderHolder(@NonNull View itemView) {
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
}