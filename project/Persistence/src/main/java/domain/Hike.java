package domain;

import java.io.Serializable;

public class Hike implements Serializable {
    int id;
    String name;
    double price;
    int availableSpots;
    String guide;
    String location;

    public Hike(int id, String name, double price, int availableSpots, String guide, String location) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availableSpots = availableSpots;
        this.guide = guide;
        this.location = location;
    }

    public Hike() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean extractFromSpots(double spots){
        if (this.availableSpots >= spots){
            this.availableSpots -= spots;
            return true;
        }
        return false;
    }
}
