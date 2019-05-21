package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class sound_serverController implements Initializable{
    @FXML 
    public  static  Label lab_server;
	  Socket sound_server;
	 ServerSocket serversound2;
	 public   sound_serverController(ServerSocket soceket_server ,Socket socket) {
		  
				
				serversound2= soceket_server;
				 sound_server= socket;
	       System.out.println("socekteteasflkjdsakljfkdslj");			
				  
			  
 	}
	 @Override
	public void initialize(URL location, ResourceBundle resources) {
 		
	}
	 @FXML
	public   void MAKE_CALL_server(){
		System.out.println("server sound");
		Platform.runLater(() -> {
			//lab_server.setText("Server  ON");
		    Thread thread3 = new Thread(new threadssound(sound_server));
		    thread3.setDaemon(true);
		    thread3.start();
		});
		
	}
}



class threadssound implements Runnable {

	Socket conn;

	public threadssound(Socket c) {
		conn = c;
	}

	@Override
	public void run() {
		try {

			AudioFormat af = new AudioFormat(8000.0f, 8, 1, true, false);
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, af);
			TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
			microphone.open(af);
			microphone.start();
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			int bytesRead = 0;
			byte[] soundData = new byte[1];
			Thread inThread = new Thread(new SoundReceiver(conn));
			inThread.start();
			while (bytesRead != -1) {
				bytesRead = microphone.read(soundData, 0, soundData.length);
				if (bytesRead >= 0) {
					dos.write(soundData, 0, bytesRead);
				}
			}
			System.out.println("IT IS DONE.");
		} catch (Exception e) {

		}
	}

}




