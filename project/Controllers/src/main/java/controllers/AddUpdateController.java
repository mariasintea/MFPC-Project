package controllers;

import domain.Hike;
import services.IService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AddUpdateController  extends UnicastRemoteObject implements Serializable {
    IService service;
    String operation;
    Hike selectedHike;

    public AddUpdateController() throws RemoteException{
    }

    public void setUp(String operation, IService service){
        this.service = service;
        this.operation = operation;
    }

    public void setSelectedHike(Hike hike) {
        this.selectedHike = hike;
    }

    public Hike getSelectedHike() {
        return selectedHike;
    }

    public void handleSendResponse(String name, String price, String availableSpots, String location, String guide){
        if(operation.equals("update")) {
            service.updateHike(selectedHike.getId(), name, Double.valueOf(price), Integer.parseInt(availableSpots),
                    guide, location);
        }
        else{
            service.addHike(name, Double.valueOf(price), Integer.parseInt(availableSpots), guide, location);
        }
    }
}
