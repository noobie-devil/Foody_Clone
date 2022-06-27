package hcmute.edu.vn.foody_18;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import hcmute.edu.vn.foody_18.Database.DatabaseHandler;
import hcmute.edu.vn.foody_18.Model.Restaurant;
import hcmute.edu.vn.foody_18.Search.Adapter.SearchAdapter;

public class SearchActivity extends AppCompatActivity {

    EditText editSearch;
    TextView textBack;
    RecyclerView searchRecyclerView;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Restaurant> restaurantArrayList;
    DatabaseHandler databaseHandler;
    SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mapping();
        restaurantArrayList = new ArrayList<>();
        restaurantArrayList = databaseHandler.getAllRestaurants();

        layoutManager = new LinearLayoutManager(this);
        searchAdapter = new SearchAdapter(this.getApplicationContext(), this, restaurantArrayList);

        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerView.setAdapter(searchAdapter);

        textBack.setOnClickListener(view -> finish());
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                getList(editable.toString());
            }
        });
    }

    public void mapping() {
        editSearch          = findViewById(R.id.searchActivity_edittext_search);
        textBack            = findViewById(R.id.searchActivity_textview_back);
        searchRecyclerView  = findViewById(R.id.searchActivity_recyclerview);

        searchRecyclerView.setHasFixedSize(true);
        databaseHandler = DatabaseHandler.getInstance(this);
    }

    public void getList(String search) {
        restaurantArrayList = new ArrayList<>();

        if (search.equals("")) {
            restaurantArrayList = databaseHandler.getAllRestaurants();
        } else {
            restaurantArrayList = databaseHandler.getAllRestaurantsByName(search);
        }
        searchAdapter.setData(restaurantArrayList);
    }
}