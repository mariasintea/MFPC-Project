package views;

import controllers.MainPageClientController;
import controllers.OrganizeTripController;
import domain.Hike;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

public class MainPageClientView extends UnicastRemoteObject implements Serializable, Observer{
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
    MainPageClientController controller;

    public MainPageClientView() throws RemoteException {
    }

    public void setUp(MainPageClientController controller){
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

    public void handleOrganizeTrip(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/OrganizeTripPage.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Organize Trip");
            stage.setScene(scene);

            OrganizeTripView view = loader.getController();
            OrganizeTripController new_controller = new OrganizeTripController();
            new_controller.setUp(controller.getService());
            view.setUp(new_controller);

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

