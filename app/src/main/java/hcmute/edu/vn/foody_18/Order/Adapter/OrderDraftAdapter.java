package hcmute.edu.vn.foody_18.Order.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import hcmute.edu.vn.foody_18.Database.DatabaseHandler;
import hcmute.edu.vn.foody_18.Event.ItemClickListener;
import hcmute.edu.vn.foody_18.Model.Order;
import hcmute.edu.vn.foody_18.Model.OrderDetail;
import hcmute.edu.vn.foody_18.Model.Restaurant;
import hcmute.edu.vn.foody_18.OrderDraftActivity;
import hcmute.edu.vn.foody_18.R;

public class OrderDraftAdapter extends RecyclerView.Adapter<OrderDraftAdapter.OrderDraftHolder> {

    Context context;
    FragmentActivity activity;
    ArrayList<Order> orderArrayList;
    ArrayList<OrderDetail> orderDetailArrayList;

    DatabaseHandler databaseHandler;
    Restaurant restaurant;

    public OrderDraftAdapter(Context context, FragmentActivity activity, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.activity = activity;
        this.orderArrayList = orderArrayList;
    }

    public void setData(ArrayList<Order> orderArrayList) {
        this.orderArrayList = orderArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderDraftHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order, parent, false);
        databaseHandler = DatabaseHandler.getInstance(context);
        return new OrderDraftHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDraftHolder holder, int position) {
        Order order = orderArrayList.get(position);
        Order clickItem = order;
        if (order == null) {
            return;
        }

        restaurant = new Restaurant();
        orderDetailArrayList = new ArrayList<>();
        int quantity = 0, price = 0;
        restaurant = databaseHandler.getRestaurant(order.getRestaurantId());
        orderDetailArrayList = databaseHandler.getOrderDetailByOrderID(order.getId());

        holder.textAddress.setText(restaurant.getAddress());
        holder.textName.setText(restaurant.getName());
        for (OrderDetail i : orderDetailArrayList) {
            quantity += i.getQuantity();
        }
        holder.textQuantity.setText(String.valueOf(quantity).concat(" phần - "));
        for (OrderDetail i : orderDetailArrayList) {
            price += i.getPrice() * i.getQuantity();
        }
        Double priceTotal = Double.parseDouble(String.valueOf(price));
        DecimalFormat formatter = new DecimalFormat("#,###");
        holder.textPrice.setText(formatter.format(priceTotal).concat("đ"));
        holder.textStatus.setTextColor(Color.parseColor("#FFAFAFAF"));
        holder.textStatus.setText("Chờ thanh toán");
        Picasso.get().load(restaurant.getThumbImage()).into(holder.imageThumbnail);

        holder.setItemClickListener((view, position1, isLongClick) -> {
            Intent details = new Intent(activity, OrderDraftActivity.class);
            details.putExtra("orderId", orderArrayList.get(position1).getId());
            Log.d("OrderAdapter", orderArrayList.get(position1).toString());
            activity.startActivity(details);
        });
    }

    @Override
    public int getItemCount() {
        if (orderArrayList != null) {
            return orderArrayList.size();
        }
        return 0;
    }

    public class OrderDraftHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textAddress, textName, textQuantity, textPrice, textStatus;
        public ShapeableImageView imageThumbnail;
        private ItemClickListener itemClickListener;

        public OrderDraftHolder(@NonNull View itemView) {
            super(itemView);

            textAddress     = itemView.findViewById(R.id.orderRow_textview_address);
            textName        = itemView.findViewById(R.id.orderRow_textview_name);
            textQuantity    = itemView.findViewById(R.id.orderRow_textview_quantity);
            textPrice       = itemView.findViewById(R.id.orderRow_textview_price);
            textStatus      = itemView.findViewById(R.id.orderRow_textview_status);
            imageThumbnail  = itemView.findViewById(R.id.orderRow_image_thumbnail);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAbsoluteAdapterPosition(), false);
        }
    }
}