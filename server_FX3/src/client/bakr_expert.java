package client;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class bakr_expert  extends Application{
	public static void main(String[] args) {
    Application.launch(args);
}

@Override
public void start(Stage primaryStage) throws IOException {
    primaryStage.setTitle("Tabs");
    Group root = new Group();
    Scene scene = new Scene(root, 400, 250, Color.WHITE);

    TabPane tabPane = new TabPane();

    BorderPane borderPane = new BorderPane();
 
        Tab tab = new Tab();
        tab.setText("Tab" );
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
  		ClientManager manager =new ClientManager();
        loginController control=new loginController(manager);
         loader.setController(control);
         tab.setClosable(false);
         tab.setContent((Node) loader.load());
        tabPane.getTabs().add(tab);
     
    // bind to take available space
    borderPane.prefHeightProperty().bind(scene.heightProperty());
    borderPane.prefWidthProperty().bind(scene.widthProperty());

    
    root.getChildren().add(tabPane);
    primaryStage.setScene(scene);
    primaryStage.show();
}

}
