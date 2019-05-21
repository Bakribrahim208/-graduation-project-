package client;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.IntBinaryOperator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class fileController implements Initializable{

@FXML
Label lab;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		lab.setText("bakr");
	}

}
