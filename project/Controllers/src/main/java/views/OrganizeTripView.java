package views;

import controllers.OrganizeTripController;
import domain.Hike;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.observer.Observer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class OrganizeTripView extends UnicastRemoteObject implements Serializable {
    @FXML
    ChoiceBox<String> hikesNamesList;
    @FXML
    TextField quantityField;
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
    @FXML
    TextField dateField;
    ObservableList<Hike> model = FXCollections.observableArrayList();
    OrganizeTripController controller;
    List<Hike> hikesList = new ArrayList<>();

    public OrganizeTripView() throws RemoteException {

    }

    public void setUp(OrganizeTripController controller)
    {
        this.controller = controller;
        setHikesList();
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
        model.setAll(hikesList);
    }

    private void setHikesList()
    {
        List<String> hikes = controller.getHikesNames();
        for(String hikeName: hikes)
            hikesNamesList.getItems().add(hikeName);
    }

    public void handleAddHike(){
        String name = hikesNamesList.getValue();
        if (name == null && quantityField.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setContentText("All text field must be completed!");
            alert.showAndWait();
            return;
        }
        controller.addHike(name, Integer.valueOf(quantityField.getText()));
        hikesList = controller.getHikes();
        hikesNamesList.getItems().remove(name);
        quantityField.setText("");
        loadTable();
    }

    public void handleOrganizeTrip(ActionEvent event){
        if (dateField.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setContentText("Date field must be completed!");
            alert.showAndWait();
            return;
        }
        double total = controller.addTrip(dateField.getText());
        if(total >= 0.0)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Trip organization completed!");
            alert.setContentText("Price: " + total);
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setContentText("Not enough available spots for some hikes!");
            alert.showAndWait();
        }

        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.hide();
    }
}
