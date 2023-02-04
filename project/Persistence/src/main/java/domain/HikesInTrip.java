package domain;

import java.io.Serializable;

public class HikesInTrip implements Serializable {
    int id;
    int tripId;
    int hikeId;
    int noPersons;

    public HikesInTrip() {
    }

    public HikesInTrip(int id, int tripId, int hikeId, int noPersons) {
        this.id = id;
        this.tripId = tripId;
        this.hikeId = hikeId;
        this.noPersons = noPersons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getHikeId() {
        return hikeId;
    }

    public void setHikeId(int hikeId) {
        this.hikeId = hikeId;
    }

    public int getNoPersons() {
        return noPersons;
    }

    public void setNoPersons(int noPersons) {
        this.noPersons = noPersons;
    }
}
