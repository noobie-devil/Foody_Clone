package hcmute.edu.vn.foody_18.Model;

public class Restaurant {

    private int id;
    private String name;
    private int vote;
    private String address;
    private String thumbImage;
    private String coverImage;

    public Restaurant() { }

    public Restaurant(int id, String name, int vote, String address, String thumbImage, String coverImage) {
        this.id = id;
        this.name = name;
        this.vote = vote;
        this.address = address;
        this.thumbImage = thumbImage;
        this.coverImage = coverImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", vote=" + vote +
                ", address='" + address + '\'' +
                ", thumbImage='" + thumbImage + '\'' +
                ", coverImage='" + coverImage + '\'' +
                '}';
    }
}