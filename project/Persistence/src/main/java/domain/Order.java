package domain;

import java.io.Serializable;

public class Order implements Serializable {
    int id;
    int address;

    public Order() {
    }

    public Order(int id, int address) {
        this.id = id;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

}
