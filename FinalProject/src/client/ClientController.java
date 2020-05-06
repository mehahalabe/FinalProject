package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class ClientController {

    @FXML
    private ComboBox<?> items;

    @FXML
    private TextField bid;

    @FXML
    private Button money;

    @FXML
    void itemChosen(ActionEvent event) {
    	//has the item that they are bidding on
    	
    }

    @FXML
    void placed(ActionEvent event) {
    	//amount of money they placed on the item

    }

}
