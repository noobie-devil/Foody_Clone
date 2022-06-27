package hcmute.edu.vn.foody_18;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import hcmute.edu.vn.foody_18.Order.Adapter.FoodOrderAdapter;

public class OrderActivity extends AppCompatActivity {

    Button buttonCheck, buttonCancel;
    EditText textNameUser, textPhone, textAddressReceive, textNameRestaurant, textAddressSend;
    TextView textBack, textTotalPrice, textStatus;
    RecyclerView orderRecyclerView;
    RecyclerView.LayoutManager layoutManager;

    private DatabaseHandler databaseHandler;
    FoodOrderAdapter foodOrderAdapter;
    Order currentOrder;

    private static final String ARG_ORDER_ID = "orderId";
    private int orderId = 0;
    private int currentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Bundle bundle = getIntent().getExtras();
        mapping();

        if (bundle != null) {
            this.orderId = bundle.getInt(ARG_ORDER_ID, 0);
            Log.d("OrderDetails", "Order Id: " + orderId);

            if (orderId != 0) {
                databaseHandler = DatabaseHandler.getInstance(getApplicationContext());
                currentOrder = databaseHandler.getOrderById(orderId);
                currentStatus = currentOrder.getPaid();

                Restaurant restaurant = new Restaurant();
                ArrayList<OrderDetail> orderDetailArrayList = new ArrayList<OrderDetail>();
                int price = 0;
                restaurant = databaseHandler.getRestaurant(currentOrder.getRestaurantId());
                orderDetailArrayList = databaseHandler.getOrderDetailByOrderID(currentOrder.getId());

                textNameUser.setText(currentOrder.getUserName());
                textPhone.setText(currentOrder.getPhone());
                textAddressReceive.setText(currentOrder.getAddress());
                textNameRestaurant.setText(restaurant.getName());
                textAddressSend.setText(restaurant.getAddress());

                layoutManager = new LinearLayoutManager(this);
                foodOrderAdapter = new FoodOrderAdapter(this.getApplicationContext(), this, orderDetailArrayList);
                orderRecyclerView.setLayoutManager(layoutManager);
                orderRecyclerView.setAdapter(foodOrderAdapter);
                foodOrderAdapter.setData(orderDetailArrayList);

                for (OrderDetail i : orderDetailArrayList) {
                    price += i.getPrice() * i.getQuantity();
                }
                Double priceTotal = Double.parseDouble(String.valueOf(price));
                DecimalFormat formatter = new DecimalFormat("#,###");
                textTotalPrice.setText("Tổng tiền: ".concat(formatter.format(priceTotal).concat("đ")));
                int status = currentOrder.getPaid();
                switch (status) {
                    case 1:
                        textStatus.setTextColor(Color.parseColor("#FFD6B400"));
                        textStatus.setText("Đang giao");
                        buttonCheck.setVisibility(View.VISIBLE);
                        buttonCancel.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        textStatus.setTextColor(Color.parseColor("#FF4DC832"));
                        textStatus.setText("Hoàn thành");
                        break;
                    case 3:
                        textStatus.setTextColor(Color.parseColor("#FFFF0000"));
                        textStatus.setText("Bị hủy");
                        break;
                }

                textBack.setOnClickListener(view -> {
                    Intent intent = new Intent();
                    intent.putExtra("currentStatus", currentStatus);
                    setResult(RESULT_OK, intent);
                    finish();
                });
                buttonCheck.setOnClickListener(view -> {
                    databaseHandler.changeOrderStatus(2, orderId);
                    currentStatus = 2;
                    Toast.makeText(OrderActivity.this, "Đã xác nhận đơn hàng", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra("currentStatus", currentStatus);
                    setResult(RESULT_OK, intent);
                    finish();
                });
                buttonCancel.setOnClickListener(view -> {
                    databaseHandler.changeOrderStatus(3, orderId);
                    currentStatus = 3;
                    Toast.makeText(OrderActivity.this, "Đã hủy đơn hàng", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra("currentStatus", currentStatus);
                    setResult(RESULT_OK, intent);
                    finish();
                });
            }
        }
    }

    public void mapping() {
        buttonCheck         = findViewById(R.id.orderActivity_button_check);
        buttonCancel        = findViewById(R.id.orderActivity_button_cancel);
        textNameUser        = findViewById(R.id.orderActivity_edittext_user);
        textPhone           = findViewById(R.id.orderActivity_edittext_phone);
        textAddressReceive  = findViewById(R.id.orderActivity_edittext_addressReceive);
        textNameRestaurant  = findViewById(R.id.orderActivity_edittext_restaurant);
        textAddressSend     = findViewById(R.id.orderActivity_edittext_addressSend);
        textBack            = findViewById(R.id.orderActivity_textview_back);
        textTotalPrice      = findViewById(R.id.orderActivity_textview_price);
        textStatus          = findViewById(R.id.orderActivity_textview_status);
        orderRecyclerView   = findViewById(R.id.orderActivity_recyclerview);

        orderRecyclerView.setHasFixedSize(true);
        databaseHandler = DatabaseHandler.getInstance(this);
    }
}