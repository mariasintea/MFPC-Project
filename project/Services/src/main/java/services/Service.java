package services;

import domain.*;
import repository.TripsRepository;
import repository.HikesRepository;
import repository.UsersRepository;
import services.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements IService {
    List<Observer> observers;
    HikesRepository hikesRepository;
    UsersRepository usersRepository;
    TripsRepository tripsRepository;

    public Service(HikesRepository hikesRepository, UsersRepository usersRepository, TripsRepository tripsRepository) {
        observers = new ArrayList<>();
        this.hikesRepository = hikesRepository;
        this.usersRepository = usersRepository;
        this.tripsRepository = tripsRepository;
    }

    public synchronized String checkUser(String username, String password){
        User user = usersRepository.findUser(username, password);
        if (user == null)
            return "nonexistent";
        return user.getRole();
    }

    public synchronized Hike findHike(String name){
        return hikesRepository.findHike(name);
    }

    public synchronized int addTrip(String date){
        Trip trip = new Trip(0, date);
        return tripsRepository.addTrip(trip);
    }

    public synchronized void addHikeToTrip(int tripId, int hikeId, int noPersons){
        try{
            HikesInTrip hikesInTrip = new HikesInTrip(0, tripId, hikeId, noPersons);
            tripsRepository.addHikeInTrip(hikesInTrip);
            Hike hike = hikesRepository.findHike(hikeId);
            hike.extractFromSpots(noPersons);
            hikesRepository.update(hike);
            notifyObservers();
        }
        catch (Exception e){

        }
    }

    public synchronized double getTotalPrice(int orderId){
        return tripsRepository.getTotal(orderId);
    }

    public synchronized void addHike(String name, double price, int availableSpots, String guide, String location){
        try{
            Hike newHike = new Hike(0, name, price, availableSpots, guide, location);
            hikesRepository.add(newHike);
            notifyObservers();
        }
        catch (Exception e){

        }
    }

    public synchronized void updateHike(int id, String name, double price, int availableSpots, String guide, String location){
        try{
            Hike newHike = new Hike(id, name, price, availableSpots, guide, location);
            hikesRepository.update(newHike);
            notifyObservers();
        }
        catch (Exception e){

        }
    }

    public synchronized void deleteHike(int id, String name, double price, int availableSpots, String guide, String location){
        try{
            Hike newHike = new Hike(id, name, price, availableSpots, guide, location);
            hikesRepository.delete(newHike);
            notifyObservers();
        }
        catch (Exception e){

        }
    }

    public synchronized List<Hike> getAllHikes(){
        List<Hike> hikes = hikesRepository.getAll();
        return hikes;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    private final int defaultThreadsNo = 5;

    public void notifyObservers() throws Exception {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(Observer client: observers){
            if (client != null)
                executor.execute(() -> {
                    try {
                        client.update();
                    } catch (Exception e) {
                    }
                });
        }
        executor.shutdown();
    }
}
