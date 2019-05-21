package client;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.java.swing.plaf.windows.resources.windows;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.Window;

public class approval_meassageController implements Initializable{

	
	
	
	
	
	
	public static MediaPlayer p ;
	public static void sound_func(String path) {
		
			Group root = new Group();
			File file = new File(path);
			Media m = new Media(file.toURI().toString());
			  p = new MediaPlayer(m);
			MediaView v = new MediaView(p);
			root.getChildren().add(v);
			p.setCycleCount(100);
			p.play();
		 
	}
	
	
	
	public static boolean app=false;
	@FXML
	Button btn_ok ,btn_no;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btn_ok.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
 app=true;		
 System.out.println(app +"YSSSSSSSSSSSSSSSS");
 Stage stage = (Stage) btn_ok.getScene().getWindow();
  stage.close();
			}
		});
		
		btn_no.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				 app=false;
				 System.out.println(app +"close");

				 Stage stage = (Stage) btn_no.getScene().getWindow();
 				    stage.close();
              
			}
		});
		sound_func( "resources/ring.mp3");

	}
	
	public static boolean get_approval(){
		return app;
	}
	
	
}
