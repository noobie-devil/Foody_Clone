package hcmute.edu.vn.foody_18.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Menu implements Serializable {

    private int id;
    private String title;
    private ArrayList<Food> foods;
    private int quantity;

    public Menu() { }

    public Menu(int id, String title, ArrayList<Food> foods, int quantity) {
        this.id = id;
        this.title = title;
        this.foods = foods;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public int getQuantity() {
        return foods.size();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "MenuFood{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", foods=" + foods +
                ", quantity=" + quantity +
                '}';
    }
}