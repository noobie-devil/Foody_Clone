package hcmute.edu.vn.foody_18.Home.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hcmute.edu.vn.foody_18.Event.ItemClickListener;
import hcmute.edu.vn.foody_18.Model.Restaurant;
import hcmute.edu.vn.foody_18.R;
import hcmute.edu.vn.foody_18.RestaurantDetailActivity;

public class TabRecentViewAdapter extends RecyclerView.Adapter<TabRecentViewAdapter.TabRecentViewHolder> {

    Context context;
    FragmentActivity activity;
    ArrayList<Restaurant> restaurantArrayList;

    public TabRecentViewAdapter(Context context, FragmentActivity activity, ArrayList<Restaurant> foodLocationArrayList) {
        this.context = context;
        this.activity = activity;
        this.restaurantArrayList = foodLocationArrayList;
    }

    public void setData(ArrayList<Restaurant> restaurantArrayList) {
        this.restaurantArrayList = restaurantArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TabRecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_restaurant, parent, false);
        return new TabRecentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabRecentViewHolder holder, int position) {
        Restaurant restaurant = restaurantArrayList.get(position);
        Restaurant clickItem = restaurant;
        if (restaurant == null) {
            return;
        }

        holder.txtLocationFoodName.setText(restaurant.getName());
        Picasso.get().load(restaurant.getThumbImage()).into(holder.imageView);

        holder.setItemClickListener((view, position1, isLongClick) -> {
            Toast.makeText(context, "" + clickItem.getName(), Toast.LENGTH_LONG).show();
            Intent details = new Intent(activity, RestaurantDetailActivity.class);
            details.putExtra("restaurantId", restaurantArrayList.get(position1).getId());
            Log.d("TabRecentViewAdapter", restaurantArrayList.get(position1).toString());
            activity.startActivity(details);
        });
    }

    @Override
    public int getItemCount() {
        if (restaurantArrayList != null) {
            return restaurantArrayList.size();
        }
        return 0;
    }

    public class TabRecentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtLocationFoodName;
        public ShapeableImageView imageView;
        private ItemClickListener itemClickListener;

        public TabRecentViewHolder(@NonNull View itemView) {
            super(itemView);

            txtLocationFoodName = itemView.findViewById(R.id.txv_name_food_location);
            imageView           = itemView.findViewById(R.id.thumb_img_food_location);
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