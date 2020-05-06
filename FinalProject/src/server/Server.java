package server;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
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
    private ArrayList<Item> items;

    public static void main (String [] args) {
        server = new Server();
        server.populateItems();
        server.SetupNetworking();
        
    }

    private void populateItems() {
		// TODO Auto-generated method stub
    	  try {
    	      File myObj = new File("items.txt");
    	      Scanner myReader = new Scanner(myObj);
    	      while (myReader.hasNextLine()) {
	    	       String data = myReader.nextLine();
	    	       int price = (int) Math.floor(Math.random() * 100); 
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
        int port = 4442;
        System.out.println("reaching here");
        try {
            ServerSocket ss = new ServerSocket(port);
            ss.setSoTimeout(100000);
            System.out.println("ur error");
            while (true) {
            	
                Socket clientSocket = ss.accept();
                System.out.println("up and running");
                System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
                ClientObserver writer = new ClientObserver(clientSocket.getOutputStream());
                writer.write("Thank you for connecting!");
                Thread t = new Thread(new ClientHandler(clientSocket, writer));
                t.start();
                this.addObserver(writer);
                System.out.println("got a connection");
                //clientSocket.close();
                
            }
        } catch (IOException e) {e.printStackTrace(); System.out.println("cry");}
    }

	
    class ClientHandler implements Runnable {
        private  BufferedReader reader;
        private  ClientObserver writer; // See Canvas. Extends ObjectOutputStream, implements Observer
        Socket clientSocket;

        public ClientHandler(Socket clientSocket, ClientObserver writer) throws IOException{
        	Socket sock = clientSocket;
        	this.writer = writer;
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			System.out.println("reaching client handler");
			//System.out.println(reader.read());
        }
        
        
        public void run() {
        	String input;
        	try {     
        		while ((input = reader.readLine()) != null) 
        		{
        			System.out.println("From client: " + input);
        			server.processRequest(input);   
        			}    
        		} catch (IOException e) {     
        			e.printStackTrace();  
        		}
        }
        
    } // end of class ClientHandler
    protected void processRequest(String input) {
        String output = "Error";
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
          this.setChanged();
          this.notifyObservers(output);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
        
}
