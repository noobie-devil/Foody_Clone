package hcmute.edu.vn.foody_18.Search.Adapter;

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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {

    Context context;
    FragmentActivity activity;
    ArrayList<Restaurant> restaurantArrayList;

    public SearchAdapter(Context context, FragmentActivity activity, ArrayList<Restaurant> restaurantArrayList) {
        this.context = context;
        this.activity = activity;
        this.restaurantArrayList = restaurantArrayList;
    }

    public void setData(ArrayList<Restaurant> restaurantArrayList) {
        this.restaurantArrayList = restaurantArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_restaurant, parent, false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        Restaurant restaurant = restaurantArrayList.get(position);
        Restaurant clickItem = restaurant;
        if (restaurant == null) {
            return;
        }

        holder.textName.setText(restaurant.getName());
        holder.textAddress.setText(restaurant.getAddress());
        Picasso.get().load(restaurant.getThumbImage()).into(holder.imageThumbnail);

        holder.setItemClickListener((view, position1, isLongClick) -> {
            Toast.makeText(context, "" + clickItem.getName(), Toast.LENGTH_LONG).show();
            Intent details = new Intent(activity, RestaurantDetailActivity.class);
            details.putExtra("restaurantId", restaurantArrayList.get(position1).getId());
            Log.d("SearchAdapter", restaurantArrayList.get(position1).toString());
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

    public class SearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textName, textAddress;
        public ShapeableImageView imageThumbnail;
        private ItemClickListener itemClickListener;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);

            textName        = itemView.findViewById(R.id.restaurantRow_textview_name);
            textAddress     = itemView.findViewById(R.id.restaurantRow_textview_address);
            imageThumbnail  = itemView.findViewById(R.id.restaurantRow_image_thumbnail);
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