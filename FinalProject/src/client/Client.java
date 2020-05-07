package client;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.fxml.*;
import javafx.scene.Parent;

/*
 * Author: Vallath Nandakumar and the EE 422C Instructors
 * Date: April 20, 2020
 * This code is for starting  out your JavaFX application from SceneBuilder. It doesn't compile.
 */
public class Client extends Application {

    ClientController controller;
    private static String host = "127.0.0.1";
    private ObjectInputStream fromServer;
    private ObjectOutputStream toServer;
    private Scanner consoleInput = new Scanner(System.in);
    private ArrayList<String> itemNames = new ArrayList<String>();
    private int clientID;
    private int count =0;
	public static void main(String[] args)
	{
		 launch(args);
	}
    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("client.fxml").openStream());
        controller = fxmlLoader.getController();
        primaryStage.setTitle("Customer");
        primaryStage.setScene(new Scene(root, 700, 600));
        primaryStage.show();
        //controller.myClient = this;
        //customer = new Customer("", "");
        
        try {      
        	connectToServer(); 
        	} catch (Exception e) {  
        		e.printStackTrace();   
        		}
    }

    void connectToServer () {
        @SuppressWarnings("resource")
		Socket socket;
		try {
			socket = new Socket(host, 5000);
			 System.out.println("Connecting to... " + socket);
		        fromServer = new ObjectInputStream(socket.getInputStream());//BufferedReader(new InputStreamReader(socket.getInputStream()));
		        System.out.println("yikes");
		        toServer = new ObjectOutputStream(socket.getOutputStream());//PrintWriter(socket.getOutputStream());
		        System.out.println("here");
		        Thread readerThread = new Thread(new Runnable() {
		          @Override
		          public void run() {
		            Object input;
		            try {
		              while ((input = fromServer.readObject()) != null) {
		                System.out.println("From server: " + input);
		                if(input instanceof String) {
		                	input = input.toString();
		                String[] variables = ((String) input).split(",");
			              if(variables[0].equals("items"))
			              {
			            	  for(int i =1; i< variables.length-2; i++)
			            	  {
			            		  itemNames.add(variables[i]);
			            		  
			            	  }
			            	  clientID = Integer.parseInt(variables[variables.length-1]);
			            	  processRequest("items");
			              }}
		                else
			              processRequest(input);
		              }
		            } catch (Exception e) {
		              e.printStackTrace();
		            }
		          }
		        });

		        Thread writerThread = new Thread(new Runnable() {
		          @Override
		          public void run() {
		            while (true) {
		            	String input = controller.getItem();
		            	double bid = controller.getBid();
		            	System.out.println("help im stuck");
		            	while(input.equals("")&& bid == 0.0) {input = controller.getItem(); bid = controller.getBid();}
		            	System.out.println("help im stuck");
		            	//Bid and Input are now recieved to be sent to server
		            	
		            	String concat = input + "," + Double.toString(bid) + "," + clientID;
		            	
		             // String input = consoleInput.nextLine();
		              
		              //what do u want to send to server here
		              sendToServer(concat);
		             /*
		              Message request = new Message(variables[0], variables[1], Integer.valueOf(variables[2]));
		              GsonBuilder builder = new GsonBuilder();
		              Gson gson = builder.create();
		              sendToServer(gson.toJson(request));*/
		            }
		          }
		        });

		        readerThread.start();
		        writerThread.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

    }
    protected void processRequest(Object input) {
    	if(input instanceof String) {
	    	if(input.equals("items"))
	    	{
	    		ClientController temp = getController();
	    		temp.setNames(itemNames);
	    	}
	    	else {
	    	 String[] variables = ((String) input).split(",");
	    	 //clientID = Integer.parseInt(variables[variables.length-1]);
	    	 if(variables[0].contentEquals("invalid"))
	    	 {
	    		 controller.setText(variables[1]);
	    	 }
	    	}
    	}
    	else if(input instanceof Item)
    	{
    		String out = ((Item) input).getHighestName() + " is the highest bidder with a bid of " + ((Item) input).getHighestBid();
    		controller.setText(out);
    	}
      }

      protected void sendToServer(String string) {
        System.out.println("Sending to server: " + string);
        try {
			toServer.writeObject(string);
			toServer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
      }

    

    ClientController getController () { return controller; }
}
