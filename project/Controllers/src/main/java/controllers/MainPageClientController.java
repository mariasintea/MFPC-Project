package controllers;

import domain.Hike;
import services.IService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class MainPageClientController extends UnicastRemoteObject implements Serializable {
    IService service;

    public MainPageClientController() throws RemoteException {
    }

    public void setUp(IService service){
        this.service = service;
    }

    public IService getService(){
        return service;
    }

    public List<Hike> getHikes() {
        List<Hike> hikesList = service.getAllHikes();
        return hikesList;
    }
}