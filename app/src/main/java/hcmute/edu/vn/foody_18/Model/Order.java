package hcmute.edu.vn.foody_18.Model;

import java.util.ArrayList;

public class Order {

    private int id;
    private int userId;
    private int restaurantId;
    private int paid;
    private String userName;
    private String phone;
    private String address;
    private String createdAt;
    private ArrayList<OrderDetail> orderDetails;

    public Order() { }

    public Order(int id, int userId, int restaurantId, int paid, String userName, String phone, String address, String createdAt, ArrayList<OrderDetail> orderDetails) {
        this.id = id;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.paid = paid;
        this.userName = userName;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
        this.orderDetails = orderDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", paid=" + paid +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}