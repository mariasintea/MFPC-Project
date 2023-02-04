package services;

import domain.Hike;
import services.observer.Observable;

import java.util.List;

public interface IService extends Observable {
    String checkUser(String username, String password);

    Hike findHike(String name);

    int addTrip(String date);

    void addHikeToTrip(int tripId, int hikeId, int noPersons);

    double getTotalPrice(int tripId);

    void addHike(String name, double price, int availableSpots, String guide, String location);

    void updateHike(int id, String name, double price, int availableSpots, String guide, String location);

    void deleteHike(int id, String name, double price, int availableSpots, String guide, String location);

    List<Hike> getAllHikes();
}
