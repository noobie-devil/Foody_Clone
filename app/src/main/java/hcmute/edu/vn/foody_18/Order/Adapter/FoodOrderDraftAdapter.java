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

import hcmute.edu.vn.foody_18.Database.DatabaseHandler;
import hcmute.edu.vn.foody_18.Model.Food;
import hcmute.edu.vn.foody_18.Model.OrderDetail;
import hcmute.edu.vn.foody_18.R;

public class FoodOrderDraftAdapter extends RecyclerView.Adapter<FoodOrderDraftAdapter.FoodOrderDraftHolder> {

    Context context;
    FragmentActivity activity;
    ArrayList<OrderDetail> orderDetailArrayList;

    DatabaseHandler databaseHandler;
    Food food = new Food();

    double price;

    public FoodOrderDraftAdapter(Context context, FragmentActivity activity, ArrayList<OrderDetail> orderDetailArrayList) {
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
    public FoodOrderDraftHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_food, parent, false);
        databaseHandler = DatabaseHandler.getInstance(context);
        return new FoodOrderDraftHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodOrderDraftHolder holder, int position) {
        OrderDetail orderDetail = orderDetailArrayList.get(position);
        food = orderDetail.getFood();

        Picasso.get().load(food.getThumbImage()).into(holder.imageFood);
        holder.textName.setText(food.getName());
        holder.textDescription.setText(food.getDescription());
        price = Double.parseDouble(food.getPrice()) * orderDetail.getQuantity();
        DecimalFormat formatter = new DecimalFormat("#,###");
        holder.textPrice.setText(formatter.format(price).concat("đ"));
        holder.textQuantity.setText(String.valueOf(orderDetail.getQuantity()));

        holder.buttonDecrease.setVisibility(View.GONE);
        holder.buttonIncrease.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        if (orderDetailArrayList != null) {
            return orderDetailArrayList.size();
        }
        return 0;
    }

    public class FoodOrderDraftHolder extends RecyclerView.ViewHolder {
        private ImageView imageFood;
        private MaterialButton buttonIncrease, buttonDecrease;
        private TextView textName, textDescription, textPrice, textQuantity;

        public FoodOrderDraftHolder(@NonNull View itemView) {
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