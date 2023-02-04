package controllers;

import domain.Hike;
import services.IService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class MainPageAdministratorController extends UnicastRemoteObject implements Serializable {
    Hike selectedHike;
    IService service;

    public MainPageAdministratorController() throws RemoteException {
    }

    public void setUp(IService service){
        this.service = service;
    }

    public IService getService(){
        return service;
    }

    public void setSelectedHike(Hike hike) {
        this.selectedHike = hike;
    }

    public List<Hike> getHikes() {
        List<Hike> hikesList = service.getAllHikes();
        return hikesList;
    }

    public void handleDelete() {
        service.deleteHike(selectedHike.getId(), selectedHike.getName(), selectedHike.getPrice(),
                selectedHike.getAvailableSpots(), selectedHike.getGuide(), selectedHike.getLocation());
    }
}
