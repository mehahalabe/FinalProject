package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.*;

import java.util.ArrayList;

import javafx.event.ActionEvent;

public class ClientController {
	private ArrayList<Items> names = new ArrayList<Items>();
    @FXML
    private TextArea oneText;

    @FXML
    private TextArea twoText;

    @FXML
    private TextArea threeText;

    @FXML
    private TextArea fourText;

    @FXML
    private TextArea fiveText;
    
    @FXML
    private Tooltip five;

    @FXML
    private Tooltip four;

    @FXML
    private Tooltip three;

    @FXML
    private Tooltip two;
    @FXML
    private Tooltip one;
    @FXML
    private ComboBox items;
    
    @FXML
    private TextArea action;

    @FXML
    private TextField bid;

    @FXML
    private Button money;

    private String itemChosen="";
    private int count = 0;
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
    
    void setNames(ArrayList<Items> n)
    {
    	names = n;
    	items.getItems().clear();
    	for(int i = 0; i<names.size(); i++)
    	{
    		items.getItems().add(names.get(i).getName());
    	}
    	if(count == 0)
    	{
    		one.setText(n.get(0).getDescription());
    		two.setText(n.get(1).getDescription());
    		three.setText(n.get(2).getDescription());
    		four.setText(n.get(3).getDescription());
    		five.setText(n.get(4).getDescription());
    		count++;
    	}
    }
    int getSize()
    {
    	return items.getItems().size();
    }
    
    void setText(String input)
    {
    	String set = "\n" + input;
    	action.appendText(set);
    }
    
    void setItems(ArrayList<Items> update)
    {
    	
    	oneText.clear();
    	twoText.clear();
    	threeText.clear();
    	fourText.clear();
    	fiveText.clear();
    	
    	String temp = update.get(0).getName()+ "\nCurrent Bid: "+ update.get(0).getHighestBid() + "\nCurrent Bidder: "+ update.get(0).getHighestName();
    	oneText.setText(temp);
    	temp = update.get(1).getName()+ "\nCurrent Bid: "+ update.get(1).getHighestBid() + "\nCurrent Bidder: "+ update.get(1).getHighestName();
    	twoText.setText(temp);
    	temp = update.get(2).getName()+ "\nCurrent Bid: "+ update.get(2).getHighestBid() + "\nCurrent Bidder: "+ update.get(2).getHighestName();
    	threeText.setText(temp);
    	temp = update.get(3).getName()+ "\nCurrent Bid: "+ update.get(3).getHighestBid() + "\nCurrent Bidder: "+ update.get(3).getHighestName();
    	fourText.setText(temp);
    	temp = update.get(4).getName()+ "\nCurrent Bid: "+ update.get(4).getHighestBid() + "\nCurrent Bidder: "+ update.get(4).getHighestName();
    	fiveText.setText(temp);
    }

}
