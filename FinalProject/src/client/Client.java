package client;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JTextArea;
import javax.swing.JTextField;

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
    BufferedReader reader;
    PrintWriter writer;
    private JTextArea incoming;
	private JTextField outgoing;

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

        connectToServer();
    }

    void connectToServer () {
        int port = 4442;
        try {
            Socket sock = new Socket("localhost", port);
            System.out.println("r u reaching");
            System.out.println("Just connected to "+ sock.getRemoteSocketAddress());
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
    		reader = new BufferedReader(streamReader);
    		System.out.println("Server says " + reader.read());
    		writer = new PrintWriter(sock.getOutputStream());
            System.out.println("networking established");
          
         
           
            //Thread readerThread = new Thread(new IncomingReader()); // see Canvas's Chat for IncomingReader class
            //readerThread.start();
            sock.close();

        } catch (IOException e) {}
    }
    class IncomingReader implements Runnable {
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					
						incoming.append(message + "\n");
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

    ClientController getController () { return controller; }
}
