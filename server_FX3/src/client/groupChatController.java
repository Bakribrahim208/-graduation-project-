package client;

import java.awt.TextField;
import java.net.URL;
import java.util.ResourceBundle;

import database.sqliteconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.userdata;

public class groupChatController implements Initializable {
	@FXML
	ListView<String>friends_list;
	@FXML
	ListView<String>online_list;
	@FXML
	ListView<String>choosen_list;
	@FXML javafx.scene.control.TextField group_name;
	@FXML 
 Button btn_finsh;
	ObservableList<String> list_model_friends = FXCollections.observableArrayList();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<userdata> data = FXCollections.observableArrayList();
		data = new sqliteconnection().dataget();
		for (userdata User : data) {
			friends_list.getItems().add(User.getIp());
		}
	}	
	@FXML 
       public void friends_click( )  {
		String to = friends_list.getSelectionModel().getSelectedItem();
           boolean test=false;
		for (String string : list_model_friends) {
			if ( string.equalsIgnoreCase(to)) {
				test=true;
				break;
			}
		}
		
		if (!test) {
			choosen_list.getItems().add(to);
			list_model_friends.add(to);
		}
		
	}

 	@FXML
	public void BTN_FINISH( ){
		if (!group_name.getText().toString().isEmpty()) {
			System.out.println(group_name.getText());
 		    Stage stage =(Stage) btn_finsh.getScene().getWindow();
		    stage.close();
		}
		
	}
	
}