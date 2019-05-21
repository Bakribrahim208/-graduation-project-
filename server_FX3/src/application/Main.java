package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("Main_window.fxml"));	
    ServerManager serverManager=new ServerManager();
   Main_windowController controller =new Main_windowController(serverManager);
 
loader.setController(controller);

	Parent	root=loader.load();
  
	Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();

 			Scene scene = new Scene(root,1000,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
