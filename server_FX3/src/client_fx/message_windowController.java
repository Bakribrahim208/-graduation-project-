package client_fx;
import client.*;

import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.IntBinaryOperator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import client.ClientManager;

public class message_windowController implements Initializable{

	
	@FXML
	TextField txt_message;
	@FXML
	TextArea txt_show;
	@FXML
	Button btn_send;
	
	
	ClientManager clientManager;
    String from,to;
    ObjectInputStream input;
    ExecutorService clientExecutor;
	public   message_windowController(String getto,String getfrom,ClientManager getClientManager) {
		 from=getfrom;
	        to=getto;
 	        //setTitle(to);
	        clientManager=getClientManager;
	        clientExecutor=Executors.newCachedThreadPool();
	}
	
	  void sendMessage()
	    {
	        String message=to+" "+from+" : " + txt_message.getText();
	        clientManager.sendMessage(message);
	        txt_message.setText(null);
	        txt_show.appendText(message.replaceFirst(to,"")+"\n");
	    }
	
	
	
	
	
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
 		
	}

}
