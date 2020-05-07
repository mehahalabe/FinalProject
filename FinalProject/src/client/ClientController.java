package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.*;

import java.util.ArrayList;

import javafx.event.ActionEvent;

public class ClientController {
	private ArrayList<String> names = new ArrayList<String>();

    @FXML
    private ComboBox items;
    
    @FXML
    private TextArea action;

    @FXML
    private TextField bid;

    @FXML
    private Button money;

    private String itemChosen="";
    private double bidMoney=0;
    public boolean flag=false;
    @FXML
    void itemChosen(ActionEvent event) {
    	//has the item that they are bidding on
    	itemChosen = (String)items.getValue();
    	
    }
    public String getItem()
    {
    	return itemChosen;
    }

    @FXML
    void placed(ActionEvent event) {
    	//amount of money they placed on the item
    	bidMoney = Double.parseDouble(bid.getText());
    	bid.clear();
    	flag = true;
    	
    }
    public boolean getFlag()
    {
    	return flag;
    }
    public double getBid()
    {
    	return bidMoney;
    	
    }
    public void setBid(double b)
    {
    	bidMoney = b;
    }
    
    void setNames(ArrayList<String> n)
    {
    	names = n;
    	for(int i = 0; i<names.size(); i++)
    	{
    		items.getItems().add(names.get(i));
    	}
    	
    }
    int getSize()
    {
    	return items.getItems().size();
    }
    
    void setText(String input)
    {
    	action.setText(input);
    }

}
