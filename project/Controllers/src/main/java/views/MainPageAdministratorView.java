package views;

import controllers.MainPageAdministratorController;
import controllers.AddUpdateController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import domain.Product;
import services.IService;
import services.observer.Observer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class MainPageAdministratorView  extends UnicastRemoteObject implements Serializable, Observer {
    @FXML
    TableView<Product> productsTable;
    @FXML
    TableColumn<Product, Integer> idColumn;
    @FXML
    TableColumn<Product, String> nameColumn;
    @FXML
    TableColumn<Product, Double> priceColumn;
    @FXML
    TableColumn<Product, Integer> availableQuantityColumn;
    ObservableList<Product> model = FXCollections.observableArrayList();
    MainPageAdministratorController controller;

    public MainPageAdministratorView() throws RemoteException {
    }

    /**
     * makes the set up for controller
     * @param controller - current service
     */
    public void setUp(MainPageAdministratorController controller){
        this.controller = controller;
        IService service = controller.getService();
        service.addObserver(this);
        loadTable();
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        availableQuantityColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("availableQuantity"));
        productsTable.setItems(model);
    }

    /**
     * loads the elements from database into the TableView productsTable
     */
    void loadTable()
    {
        List<Product> productList = controller.getProducts();
        model.setAll(productList);
    }

    /**
     * opens Add Product Page
     */
    public void handleAdd() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AddUpdatePage.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Add Product");
            stage.setScene(scene);

            AddUpdateView view = loader.getController();
            AddUpdateController new_controller = new AddUpdateController();
            new_controller.setUp("add", controller.getService());
            view.setUp("add", new_controller);

            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * deletes selected Product from database
     */
    public void handleDelete() {
        Product product = productsTable.getSelectionModel().getSelectedItem();
        if(product == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setContentText("No product was selected!");
            alert.showAndWait();
            return;
        }
        controller.setSelectedProduct(product);
        controller.handleDelete();
    }

    /**
     * opens Update Product Page
     */
    public void handleUpdate() {
        Product product = productsTable.getSelectionModel().getSelectedItem();
        if(product == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setContentText("No product was selected!");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AddUpdatePage.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Update Product");
            stage.setScene(scene);

            AddUpdateView view = loader.getController();
            AddUpdateController new_controller = new AddUpdateController();
            new_controller.setUp("update", controller.getService());
            new_controller.setSelectedProduct(product);
            view.setUp("update", new_controller);

            stage.show();
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void update() {
        loadTable();
    }
}
