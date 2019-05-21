package client;
	
import org.opencv.core.Core;
import org.opencv.core.Core;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

	FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
  		ClientManager manager =new ClientManager();
        loginController control=new loginController(manager);
     loader.setController(control);
	Parent	root=loader.load();
	Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();

		Scene scene = new Scene(root,primScreenBounds.getWidth(), primScreenBounds.getHeight());
 			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
