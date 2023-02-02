package services;

import domain.*;
import repository.OrdersRepository;
import repository.ProductsRepository;
import repository.UsersRepository;
import services.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements IService {
    List<Observer> observers;
    ProductsRepository productRepository;
    UsersRepository userRepository;
    OrdersRepository orderRepository;

    public Service(ProductsRepository productRepository, UsersRepository userRepository, OrdersRepository orderRepository) {
        observers = new ArrayList<>();
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public synchronized String checkUser(String username, String password){
        User user = userRepository.findUser(username, password);
        if (user == null)
            return "nonexistent";
        return user.getRole();
    }

    public synchronized Product findProduct(String name){
        return productRepository.findProduct(name);
    }

    public synchronized int addOrder(int address){
        Order order = new Order(0, address);
        return orderRepository.addOrder(order);
    }

    public synchronized void addProductToOrder(int orderId, int productId, int quantity){
        try{
            ProductsInOrder productsInOrder = new ProductsInOrder(0, orderId, productId, quantity);
            orderRepository.addProductInOrder(productsInOrder);
            Product product = productRepository.findProduct(productId);
            product.extractFromQuantity(quantity);
            productRepository.update(product);
            notifyObservers();
        }
        catch (Exception e){

        }
    }

    public synchronized double getTotalPrice(int orderId){
        return orderRepository.getTotal(orderId);
    }

    public synchronized int addAddress(String street, int number, String city, String county, String country){
        Address newAddress = new Address(0, street, number, city, county, country);
        return orderRepository.addAddress(newAddress);
    }

    public synchronized void addProduct(String name, double price, int quantity){
        try{
            Product newProduct = new Product(0, name, price, quantity);
            productRepository.add(newProduct);
            notifyObservers();
        }
        catch (Exception e){

        }
    }

    public synchronized void updateProduct(int id, String name, double price, int quantity){
        try{
            Product newProduct = new Product(id, name, price, quantity);
            productRepository.update(newProduct);
            notifyObservers();
        }
        catch (Exception e){

        }
    }

    public synchronized void deleteProduct(int id, String name, double price, int quantity){
        try{
            Product newProduct = new Product(id, name, price, quantity);
            productRepository.delete(newProduct);
            notifyObservers();
        }
        catch (Exception e){

        }
    }

    /**
     * selects all products from database
     * @return list of existing products
     */
    public synchronized List<Product> getAllProducts(){
        List<Product> products = productRepository.getAll();
        return products;
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
