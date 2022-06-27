package hcmute.edu.vn.foody_18.Order.Adapter;

import android.content.Context;
import android.graphics.Color;
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
import hcmute.edu.vn.foody_18.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {

    Context context;
    FragmentActivity activity;
    ArrayList<Order> orderArrayList;
    ArrayList<OrderDetail> orderDetailArrayList;

    DatabaseHandler databaseHandler;
    Restaurant restaurant;

    OrderInteractionListener orderInteractionListener;

    public interface OrderInteractionListener {
        public void clickListener(Order order);
    }

    public OrderAdapter(Context context, FragmentActivity activity, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.activity = activity;
        this.orderArrayList = orderArrayList;
    }

    public OrderAdapter(Context context, OrderInteractionListener orderInteractionListener, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.orderInteractionListener = orderInteractionListener;
        this.orderArrayList = orderArrayList;
    }

    public void setData(ArrayList<Order> orderArrayList) {
        this.orderArrayList = orderArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order, parent, false);
        databaseHandler = DatabaseHandler.getInstance(context);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
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
        int status = order.getPaid();
        switch (status) {
            case 1:
                holder.textStatus.setTextColor(Color.parseColor("#FFD6B400"));
                holder.textStatus.setText("Đang giao");
                break;
            case 2:
                holder.textStatus.setTextColor(Color.parseColor("#FF4DC832"));
                holder.textStatus.setText("Hoàn thành");
                break;
            case 3:
                holder.textStatus.setTextColor(Color.parseColor("#FFFF0000"));
                holder.textStatus.setText("Bị hủy");
                break;
        }
        Picasso.get().load(restaurant.getThumbImage()).into(holder.imageThumbnail);

        holder.setItemClickListener((view, position1, isLongClick) -> orderInteractionListener.clickListener(orderArrayList.get(position1)));
    }

    @Override
    public int getItemCount() {
        if (orderArrayList != null) {
            return orderArrayList.size();
        }
        return 0;
    }

    public class OrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textAddress, textName, textQuantity, textPrice, textStatus;
        public ShapeableImageView imageThumbnail;
        private ItemClickListener itemClickListener;

        public OrderHolder(@NonNull View itemView) {
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