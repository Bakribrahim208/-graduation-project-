/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author UsersFiles
 */
public class Main_windowController implements Initializable {

	@FXML
 	ListView<String> monitor_CLient;
	
	
	@FXML
 	ListView<Label> monitor_users;
	
	
	@FXML
	ListView<String> online_user;
	@FXML
	Button start;
	@FXML
	Button stop;
	@FXML
	Label status,nub_Online;
	
 	//@FXML
	//TextArea ta_monitor_clients;
	ObservableList<String> list_model = FXCollections.observableArrayList();

	public Main_windowController(ServerManager getManager) {
		serverManager = getManager;
		statusListener = new MyStatusListener();
		clientListener = new MyClientListener();

	}

	ServerManager serverManager;
	ServerStatusListener statusListener;
	ClientListener clientListener;
	public static HashMap<String, String> hm = new HashMap<String, String>();

 
	 
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		stop.setDisable(true);
 
		 monitor_users.setPrefHeight(390);
		System.out.println(center1.getPrefHeight());
  	}

	@FXML
	public void start_server() {
		//message.set("open");
		serverManager.startServer(statusListener, clientListener);
		stop.setDisable(false);
		start.setDisable(true);

	}
	@FXML
	public void Close_window() {
		Platform.exit();
	     System.exit(0);	 

	}
	
	
	@FXML
  HBox top;
	@FXML  Pane left1 ;
	@FXML  VBox center1,right1,bottom1 ;
 
	
	
	 
	@FXML
	public void STOP_server() {
		try{
			//message.set("open");
		serverManager.stopServer(statusListener); 
		stop.setDisable(true);
		start.setDisable(false);
		}
		catch(Exception ex) {
			
		}
	}
	class MyStatusListener implements ServerStatusListener {
		public void status(String message) {
			status.setText(message);
		}
	}

	class MyClientListener implements ClientListener {
		public void signIn(String userName) {
			// list_model.add((String)userName);
			  Platform.runLater(() -> {
				  list_model.add(userName);
				  System.out.println(list_model.size());
				  online_user.getItems().add(userName);
				  int count=list_model.size();
				  nub_Online.setText(String.valueOf(count));
				});
		}
		public void signOut(String userName) {
		 
			 Platform.runLater(() -> {
					list_model.remove((Object) userName);
					online_user.getItems().clear();
					online_user.getItems().addAll(list_model);
					  System.out.println("remove"+list_model.size());
					  int count=list_model.size();
					  nub_Online.setText(String.valueOf(count));
				});
		}

		public void clientStatus(String status) {
			// ta_monitor_clients.appendText(status+"\n") ;
			// message.set(message.get()+"jkjjkj");
			Platform.runLater(() -> {
				
				// Õÿ «··Ì”  Â‰« 
				Label lab= new Label(status.toString());
				//lab.setStyle("-fx-font: 16px 'Serif';-fx-text-fill: ##172d34;");
				lab.setPrefWidth(monitor_users.getWidth());
				monitor_users.getItems().add(lab);
				//message.set(message.get()+status.toString() +"\n");
				//ta_monitor_clients.appendText(status.toString() +"\n");
			});
		}
		public void mapped(String nam, String ip) {
			if (hm.get(nam) == null) {

				hm.put(nam, ip);
				System.out.println(nam + " " + ip + "1111");
			}
		}

	}

}
