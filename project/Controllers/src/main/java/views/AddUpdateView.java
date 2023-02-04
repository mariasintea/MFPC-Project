package views;

import controllers.AddUpdateController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import domain.Hike;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AddUpdateView extends UnicastRemoteObject implements Serializable {
    @FXML
    Button sendButton;
    @FXML
    Label title;
    @FXML
    TextField nameField;
    @FXML
    TextField priceField;
    @FXML
    TextField availableSpotsField;
    @FXML
    TextField locationField;
    @FXML
    TextField guideField;
    AddUpdateController controller;

    public AddUpdateView() throws RemoteException {
    }

    public void setUp(String operation, AddUpdateController controller){
        this.controller = controller;
        switch (operation){
            case "add": {
                sendButton.setText("Add");
                title.setText("Add Hike");
            }break;
            case "update": {
                sendButton.setText("Update");
                title.setText("Update Hike");
                setSelectedHike();
            }break;
        }
    }

    public void setSelectedHike(){
        Hike hike = controller.getSelectedHike();
        nameField.setText(hike.getName());
        priceField.setText(String.valueOf(hike.getPrice()));
        availableSpotsField.setText(String.valueOf(hike.getAvailableSpots()));
        locationField.setText(hike.getLocation());
        guideField.setText(hike.getGuide());
    }

    public void handleSendResponse(ActionEvent event) {
        if(nameField.getText().equals("") || priceField.getText().equals("") || availableSpotsField.getText().equals("")
                || locationField.getText().equals("") || guideField.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR!");
            alert.setContentText("All text field must be completed!");
            alert.showAndWait();
            return;
        }

        controller.handleSendResponse(nameField.getText(), priceField.getText(), availableSpotsField.getText(),
                locationField.getText(), guideField.getText());

        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.hide();
    }
}
