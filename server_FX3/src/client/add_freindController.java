package client;

 import java.io.File;
import java.net.URL;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.ResourceBundle;

//import controller.alert;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import database.sqliteconnection;
 
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import model.userdata;

public class add_freindController  implements Initializable{
	
	
	 
	 

	public static String ipaddress="";
	ObservableList<userdata> data = FXCollections.observableArrayList();
	//Path
	@FXML
	ListView<HBox> friends_list;
	@FXML 
	Button btn_friend;
	@FXML  
	javafx.scene.control.TextField name ,ip,Path;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fill_listview();
		 btn_friend.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
 				// TODO Auto-generated method stub
				
				try {
					if (!ip.getText().isEmpty()&&
							!name.getText().isEmpty()) {
						new sqliteconnection().Add(ip.getText().toString()
								, name.getText().toString(),Path.getText().toString());
						name.clear();
						ip.clear();
						Path.clear();
						friends_list.getItems().clear();
						fill_listview();

					}
				
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	 
	}
	public void fill_listview() {
		data = new sqliteconnection().dataget( );
		System.out.println(data.size());
		// System.out.println(data.size());
		HBox box =new HBox();

		Label label =new Label("UserName");
		label.setPrefWidth(friends_list.getPrefWidth()/3);
		box.getChildren().add(label );
		  label =new Label("IP");
		label.setPrefWidth(friends_list.getPrefWidth()/3);
		box.getChildren().add(label );
		
		  label =new Label( "Image Path");
		label.setPrefWidth(friends_list.getPrefWidth()/3);
		box.getChildren().add(label );
		friends_list.getItems().add(box);
		for (userdata d : data) {
			  box =new HBox();
			System.out.println(friends_list.getPrefWidth()+"////////");

		 
			//box.getChildren().add(new Label(d.getIp()));
			  label =new Label(d.getIp());
			label.setPrefWidth(friends_list.getPrefWidth()/3);
			box.getChildren().add(label );
			  label =new Label(d.getName());
		    label.setPrefWidth(friends_list.getPrefWidth()/3);
			box.getChildren().add(label );
			
			  label =new Label(d.getPath());
			label.setPrefWidth(friends_list.getPrefWidth());
			box.getChildren().add(label );
			
			
			//box.getChildren().add(new Label(d.getName()));
			//box.getChildren().add(new Label(d.getPath()));
			friends_list.getItems().add(box);
		}
		data.clear();

	}
	
	@FXML
	void showOnlineWindow() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("onlineUserwindow.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	public void selected(){
		//name.setText(friends_list.getSelectionModel().getSelectedItem());
		int id=friends_list.getSelectionModel().getSelectedIndex();
		name.setText(String.valueOf(id));
		closeButton.fire();
 	}
	@FXML
	public Button closeButton;
	@FXML
	public void handleCloseButtonAction(ActionEvent event) {
 	      Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.fireEvent(new WindowEvent(
                        stage,
                        WindowEvent.WINDOW_CLOSE_REQUEST
                ));
	}
	 
	@FXML
	public void Select_image(){
		//to open file dailoge
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select IMages files");
		 
 
		fileChooser.getExtensionFilters().addAll( new ExtensionFilter("Image Files", "*.PNG"
				,"*.png","*.JPG","*.jpg"));

		File selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			    Path.setText(selectedFile.toURI().toString());
			}
 			else {
 				Path.setText("no");
			  
			}

	}
	
	
	
	@FXML
	public void deleteUser(){
	 try {	
		 int index =friends_list.getSelectionModel().getSelectedIndex();
		data = new sqliteconnection().dataget();
		System.out.println(data.get(index-1).getName());
		
			sqliteconnection.delete(data.get(index-1).getName().toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 finally {
		 friends_list.getItems().clear();
			fill_listview();
	}
 	}
	
}
