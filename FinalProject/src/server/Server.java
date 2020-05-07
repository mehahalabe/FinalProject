package server;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import com.google.gson.*;

/*
 * Author: Vallath Nandakumar and the EE 422C instructors.
 * Data: April 20, 2020
 * This starter code assumes that you are using an Observer Design Pattern and the appropriate Java library
 * classes.  Also using Message objects instead of Strings for socket communication.
 * See the starter code for the Chat Program on Canvas.  
 * This code does not compile.
 */
public class Server extends Observable {

    static Server server;
    private ArrayList<PrintWriter> clientOutputStreams;
    private ArrayList<Item> items= new ArrayList<Item>();
    private String itemList="items,";
    private int clientId =0;
    public static void main (String [] args) {
        server = new Server();
        server.populateItems();
         
       /* new Thread( () -> {*/server.SetupNetworking();//}).start();
        
    }

    private void populateItems() {
		// TODO Auto-generated method stub
    	  try {
    	      File myObj = new File("items.txt");
    	      Scanner myReader = new Scanner(myObj);
    	      while (myReader.hasNextLine()) {
	    	       String data = myReader.nextLine();
	    	       int price = (int) Math.floor(Math.random() * 100); 
	    	       
	    	       itemList += data +",";
	    	       Item x = new Item(data, 0, -1, price);
	    	       items.add(x);
    	      }
    	      myReader.close();
    	    } catch (FileNotFoundException e) {
    	      System.out.println("An error occurred.");
    	      e.printStackTrace();
    	    }
		
	}

	private void SetupNetworking() {
		@SuppressWarnings("resource")
		ServerSocket serverSock;
	    while (true) {
	    	Socket clientSocket;
		try {
			serverSock = new ServerSocket(5000);
			clientSocket = serverSock.accept();
			 System.out.println("Connecting to... " + clientSocket);
			 

		      ClientHandler handler = new ClientHandler(this, clientSocket);
		      server.addObserver(handler);
		      clientId++;
		     

		      Thread t = new Thread(handler);
		      t.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	    }
    }
	  protected String processRequest(String input) {
		  /*  String output = "Error";
		    Gson gson = new Gson();
		    Message message = gson.fromJson(input, Message.class);
		    try {
		      String temp = "";
		      switch (message.type) {
		        case "upper":
		          temp = message.input.toUpperCase();
		          break;
		        case "lower":
		          temp = message.input.toLowerCase();
		          break;
		        case "strip":
		          temp = message.input.replace(" ", "");
		          break;
		      }
		      output = "";
		      for (int i = 0; i < message.number; i++) {
		        output += temp;
		        output += " ";
		      }
		      System.out.println(output);*/
		   //  if(!output.equals("ITEMS")) {
		  Object output = null;
		  String[] variables = input.split(",");
		  if(!variables[0].equals("items")) {
			  for(int i = 0; i<items.size(); i++)
			  {
				  if(variables[0].equals(items.get(i).getName()))
				  {
					  if(Double.parseDouble(variables[1]) > items.get(i).getHighestBid()) 
					  {
						  //set customer name and bid
						  items.get(i).setHighestName(Integer.parseInt(variables[2]));
						  items.get(i).setHighestBid(Double.parseDouble(variables[1]));
						  //output = "valid," + Double.parseDouble(variables[1]);
						  output = items.get(i);
						  //valid bid
					  }
					  else
					  {
						  //invalid bid
						  output = "invalid,Bid is too low";
					  }
				  }
			  }
		  }
		  
		  this.setChanged();
		  this.notifyObservers(output);//}
		     //return output;
		 /*  } catch (Exception e) {
		      e.printStackTrace();
		    }
		    return output;*/
		      return input;
		  }

	
    class ClientHandler implements Runnable, Observer {
    	private Server server;
    	  private Socket clientSocket;
    	  private ObjectInputStream fromClient;
    	  private ObjectOutputStream toClient;
    	  private int count =0;

    	  protected ClientHandler(Server server, Socket clientSocket) {
    	    this.server = server;
    	    this.clientSocket = clientSocket;
    	    try {
    	    	System.out.println("yikes");
    	      fromClient = new ObjectInputStream(this.clientSocket.getInputStream());//BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
    	      toClient = new ObjectOutputStream(this.clientSocket.getOutputStream());//PrintWriter(this.clientSocket.getOutputStream());
    	      System.out.println("yikes");
    	    } catch (IOException e) {
    	      e.printStackTrace();
    	    }
    	  }

    	  protected void sendToClient(String string) {
    		//string += "," + clientId;
    	    System.out.println("Sending to client: " + string);
    	    try {
				toClient.writeObject(string);
	    	    toClient.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	  }

    	  @Override
    	  public void run() {
    	    Object input;
    	    try {
    	    	if(count ==0) {
    	    	 server.processRequest(itemList);  
    	    	 System.out.println("here hoe");
    	    	 count++;
    	    	}
    	    	else {
	    	      try {
					while ((input = fromClient.readObject()) != null) {
						  System.out.println("From client: " + input.toString());
						  String test = server.processRequest(input.toString());
						
					  }
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    	}
    	    } catch (IOException e) {
    	      e.printStackTrace();
    	    }
    	  }

    	  @Override
    	  public void update(Observable o, Object arg) {
    	    this.sendToClient((String) arg);
    	  }
        
    }
}
