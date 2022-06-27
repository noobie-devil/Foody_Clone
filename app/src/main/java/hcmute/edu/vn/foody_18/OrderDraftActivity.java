package hcmute.edu.vn.foody_18;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import hcmute.edu.vn.foody_18.Database.DatabaseHandler;
import hcmute.edu.vn.foody_18.Model.Order;
import hcmute.edu.vn.foody_18.Model.OrderDetail;
import hcmute.edu.vn.foody_18.Model.Restaurant;
import hcmute.edu.vn.foody_18.Order.Adapter.FoodOrderDraftAdapter;

public class OrderDraftActivity extends AppCompatActivity {

    Button buttonConfirm, buttonCancel;
    EditText textNameUser, textPhone, textAddressReceive, textNameRestaurant, textAddressSend;
    TextView textBack, textTotalPrice;
    RecyclerView orderRecyclerView;
    RecyclerView.LayoutManager layoutManager;

    private DatabaseHandler databaseHandler;
    FoodOrderDraftAdapter foodOrderDraftAdapter;
    Order currentOrder;

    private static final String ARG_ORDER_ID = "orderId";
    private int orderId = 0;
    int currentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_draft);

        mapping();

        if (getIntent() != null) {
            this.orderId = getIntent().getIntExtra(ARG_ORDER_ID, 0);
            Log.d("OrderDetails", "Order Id: " + orderId);

            if (orderId != 0) {
                databaseHandler = DatabaseHandler.getInstance(getApplicationContext());
                currentOrder = databaseHandler.getOrderById(orderId);
                currentStatus = currentOrder.getPaid();

                SharedPreferences sharedPreferences = getSharedPreferences("currentUser", MODE_PRIVATE);
                Restaurant restaurant = new Restaurant();
                ArrayList<OrderDetail> orderDetailArrayList = new ArrayList<OrderDetail>();
                int price = 0;
                restaurant = databaseHandler.getRestaurant(currentOrder.getRestaurantId());
                orderDetailArrayList = databaseHandler.getOrderDetailByOrderID(currentOrder.getId());

                textNameUser.setText(sharedPreferences.getString("userName", ""));
                textPhone.setText(sharedPreferences.getString("phone", ""));
                textAddressReceive.setText(sharedPreferences.getString("address", ""));
                textNameRestaurant.setText(restaurant.getName());
                textAddressSend.setText(restaurant.getAddress());

                layoutManager = new LinearLayoutManager(this);
                foodOrderDraftAdapter = new FoodOrderDraftAdapter(this.getApplicationContext(), this, orderDetailArrayList);
                orderRecyclerView.setLayoutManager(layoutManager);
                orderRecyclerView.setAdapter(foodOrderDraftAdapter);
                foodOrderDraftAdapter.setData(orderDetailArrayList);

                for (OrderDetail i : orderDetailArrayList) {
                    price += i.getPrice() * i.getQuantity();
                }
                Double priceTotal = Double.parseDouble(String.valueOf(price));
                DecimalFormat formatter = new DecimalFormat("#,###");
                textTotalPrice.setText("Tổng tiền: ".concat(formatter.format(priceTotal).concat("đ")));

                textBack.setOnClickListener(view -> finish());
                buttonConfirm.setOnClickListener(view -> {
                    databaseHandler.setPaymentOrder(1, textNameUser.getText().toString(), textPhone.getText().toString(), textAddressReceive.getText().toString(), orderId);
                    Toast.makeText(OrderDraftActivity.this, "Đã thanh toán đơn hàng", Toast.LENGTH_LONG).show();
                    finish();
                });
                buttonCancel.setOnClickListener(view -> {
                    databaseHandler.deleteOrder(orderId);
                    Toast.makeText(OrderDraftActivity.this, "Đã loại bỏ đơn nháp", Toast.LENGTH_LONG).show();
                    finish();
                });
            }
        }
    }

    public void mapping() {
        buttonConfirm       = findViewById(R.id.orderDraftActivity_button_confirm);
        buttonCancel        = findViewById(R.id.orderDraftActivity_button_cancel);
        textNameUser        = findViewById(R.id.orderDraftActivity_edittext_user);
        textPhone           = findViewById(R.id.orderDraftActivity_edittext_phone);
        textAddressReceive  = findViewById(R.id.orderDraftActivity_edittext_addressReceive);
        textNameRestaurant  = findViewById(R.id.orderDraftActivity_edittext_restaurant);
        textAddressSend     = findViewById(R.id.orderDraftActivity_edittext_addressSend);
        textBack            = findViewById(R.id.orderDraftActivity_textview_back);
        textTotalPrice      = findViewById(R.id.orderDraftActivity_textview_price);
        orderRecyclerView   = findViewById(R.id.orderDraftActivity_recyclerview);

        orderRecyclerView.setHasFixedSize(true);
        databaseHandler = DatabaseHandler.getInstance(this);
    }
}