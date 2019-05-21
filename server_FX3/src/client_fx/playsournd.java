package client_fx;

import java.io.File;

import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class playsournd {
	//public static String path = "resources/telegram.mp3";

 	public playsournd( ){
 	}
	
	
	     	MediaPlayer p  ;
		public   void sound_func(String path) {
 				Group root = new Group();
				File file = new File(path);
				Media m = new Media(file.toURI().toString());
				  p = new MediaPlayer(m);
				MediaView v = new MediaView(p);
				root.getChildren().add(v);
 				p.play();
			 
		}
	
}
