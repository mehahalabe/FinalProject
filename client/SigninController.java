package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class SigninController {

    @FXML
    private TextField user;

    @FXML
    private Button signin;

     private boolean clicked=false;
     
    /* @FXML
     void done(ActionEvent event) {
    	 click();
    	
     }*/
     
     public Button sign()
     {
    	 return signin;
     }
    
    public void click() {
    	clicked = true;
    }

    public boolean getClick() {
    	return clicked;
    }
    public String getUser()
    {
    	return user.getText();
    }
}
