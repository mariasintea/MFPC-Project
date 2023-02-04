package controllers;

import domain.Hike;
import services.IService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrganizeTripController extends UnicastRemoteObject implements Serializable {
    IService service;
    List<Hike> hikesList;
    List<Integer> hikesSpots;

    public OrganizeTripController() throws RemoteException {

    }

    public void setUp(IService service) {
        hikesList = new ArrayList<>();
        hikesSpots = new ArrayList<>();
        this.service = service;
    }

    public List<String> getHikesNames(){
        return service.getAllHikes().stream().map(p -> p.getName()).collect(Collectors.toList());
    }

    public List<Hike> getHikes(){
        return hikesList;
    }

    public IService getService() {
        return service;
    }

    public void addHike(String hikeName, Integer noPersons){
        Hike hike = service.findHike(hikeName);
        hikesList.add(hike);
        hikesSpots.add(noPersons);
    }

    public boolean checkTrip(){
        for (int i = 0; i < hikesList.size(); i++)
            if(!hikesList.get(i).extractFromSpots(hikesSpots.get(i)))
                return false;
            return true;
    }

    public double addTrip(String date) {
        if (!checkTrip())
            return -1;
        int tripId = service.addTrip(date);
        for (int i = 0; i < hikesList.size(); i++)
            service.addHikeToTrip(tripId, hikesList.get(i).getId(), hikesSpots.get(i));
        return service.getTotalPrice(tripId);
    }
}
