package hcmute.edu.vn.foody_18.Model;

import java.io.Serializable;

public class Food implements Serializable {

    private int foodId;
    private String name;
    private String price;
    private int sold;
    private String description;
    private String thumbImage;
    private String coverImage;

    public Food() { }

    public Food(int foodId, String name, String price, int sold, String description, String thumbImage, String coverImage) {
        this.foodId = foodId;
        this.name = name;
        this.price = price;
        this.sold = sold;
        this.description = description;
        this.thumbImage = thumbImage;
        this.coverImage = coverImage;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodId=" + foodId +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", sold=" + sold +
                ", description='" + description + '\'' +
                ", thumbImage='" + thumbImage + '\'' +
                ", coverImage='" + coverImage + '\'' +
                '}';
    }
}