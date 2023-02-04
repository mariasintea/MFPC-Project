package domain;

import java.io.Serializable;

public class Trip implements Serializable {
    int id;
    String date;

    public Trip() {
    }

    public Trip(int id, String date) {
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
