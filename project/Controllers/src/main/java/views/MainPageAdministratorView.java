package views;

import controllers.MainPageAdministratorController;
import controllers.AddUpdateController;
import domain.Hike;
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
import services.IService;
import services.observer.Observer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class MainPageAdministratorView  extends UnicastRemoteObject implements Serializable, Observer {
    @FXML
    TableView<Hike> hikesTable;
    @FXML
    TableColumn<Hike, Integer> idColumn;
    @FXML
    TableColumn<Hike, String> nameColumn;
    @FXML
    TableColumn<Hike, Double> priceColumn;
    @FXML
    TableColumn<Hike, Integer> availableSpotsColumn;
    @FXML
    TableColumn<Hike, String> guideColumn;
    @FXML
    TableColumn<Hike, String> locationColumn;
    ObservableList<Hike> model = FXCollections.observableArrayList();
    MainPageAdministratorController controller;

    public MainPageAdministratorView() throws RemoteException {
    }

    public void setUp(MainPageAdministratorController controller){
        this.controller = controller;
        IService service = controller.getService();
        service.addObserver(this);
        loadTable();
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<Hike, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Hike, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Hike, Double>("price"));
        availableSpotsColumn.setCellValueFactory(new PropertyValueFactory<Hike, Integer>("availableSpots"));
        guideColumn.setCellValueFactory(new PropertyValueFactory<Hike, String>("guide"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Hike, String>("location"));
        hikesTable.setItems(model);
    }

    void loadTable()
    {
        List<Hike> hikeList = controller.getHikes();
        model.setAll(hikeList);
    }

    public void handleAdd() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AddUpdatePage.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Add Hike");
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

    public void handleDelete() {
        Hike hike = hikesTable.getSelectionModel().getSelectedItem();
        if(hike == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setContentText("No hike was selected!");
            alert.showAndWait();
            return;
        }
        controller.setSelectedHike(hike);
        controller.handleDelete();
    }

    public void handleUpdate() {
        Hike hike = hikesTable.getSelectionModel().getSelectedItem();
        if(hike == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setContentText("No hike was selected!");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AddUpdatePage.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Update Hike");
            stage.setScene(scene);

            AddUpdateView view = loader.getController();
            AddUpdateController new_controller = new AddUpdateController();
            new_controller.setUp("update", controller.getService());
            new_controller.setSelectedHike(hike);
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
