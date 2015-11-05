package kz.growit.altynorda.models;

/**
 * Created by jean on 9/4/2015.
 */
public class ListringRow {
    private String ImageUrl, price, rooms, floor, bathrooms, area, address;

    public ListringRow(String imageUrl, String price, String rooms, String floor, String bathrooms, String area, String address) {

        ImageUrl = imageUrl;
        this.price = price;
        this.rooms = rooms;
        this.floor = floor;
        this.bathrooms = bathrooms;
        this.area = area;
        this.address = address;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getPrice() {
        return price;
    }

    public String getRooms() {
        return rooms;
    }

    public String getFloor() {
        return floor;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public String getArea() {
        return area;
    }

    public String getAddress() {
        return address;
    }
}
