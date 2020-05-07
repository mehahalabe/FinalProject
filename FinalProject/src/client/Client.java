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
    private BufferedReader fromServer;
    private PrintWriter toServer;
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
			 	toServer = new PrintWriter(socket.getOutputStream());
			 	fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 	
		       
		        Thread readerThread = new Thread(new Runnable() {
		          @Override
		          public void run() {
		            String input;
		            try {
		            	
		              while ((input = fromServer.readLine()) != null) {
		            	  
		                System.out.println("From server: " + input);
		              
		                String[] variables = ((String) input).split(",");
			              if(variables.length-6!=controller.getSize())
			              {
			            	  for(int i =2; i< variables.length-2; i++)
			            	  {
			            		  itemNames.add(variables[i]);
			            		  
			            	  }
			            	  clientID = Integer.parseInt(variables[variables.length-1]);
			            	  ClientController temp = getController();
			  	    			temp.setNames(itemNames);			         
			  	    		}
		                
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
		        	 
		        
		        	 boolean flag = true;
		        	  System.out.println("whatshapp");
		            while (true) {
		            	if(count ==0)
		            	{
		            		sendToServer("start");
		            		count++;
		            	}
		            	
		            	//while(!controller.getFlag()) {       		}
		            	String input = controller.getItem();
		            	double bid = controller.getBid();
		            	//Bid and Input are now recieved to be sent to server
		            	String concat = input + "," + Double.toString(bid) + "," + clientID;
		            	
		             // String input = consoleInput.nextLine();
		              
		              //what do u want to send to server here
		            	if(bid !=0) {
		            		controller.setBid(0);
		            		sendToServer(concat);
		              }
		              flag = false;
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
    protected void processRequest(String input) {
    	String[] variables = ((String) input).split(",");
	    if(!variables[1].equals("items")) {
	    	
	    	 clientID = Integer.parseInt(variables[variables.length-1]);
	    	 if(variables[0].contentEquals("invalid"))
	    	 {
	    		 controller.setText(variables[1]);
	    	 }
	    	 else
	    	 {
	    		 String out = variables[2] + " is the highest bidder with a bid of " + variables[1] +" on "+ variables[3];
	    		 controller.setText(out);
	    	 }
	    	
	    	}
    		
    		
    	}
      

      protected void sendToServer(String string) {
        System.out.println("Sending to server: " + string);
        toServer.println(string);
		toServer.flush();
        
      }

    

    ClientController getController () { return controller; }
}
