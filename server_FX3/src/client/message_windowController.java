package client;

import client.*;
import client.video_windowController.DaemonThread;
import database.sqliteconnection;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.IntBinaryOperator;
import java.util.zip.Inflater;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import java.awt.Image;
import java.awt.image.BufferedImage;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//import  utils.Utils;
import java.io.ByteArrayInputStream;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.userdata;

public class message_windowController implements Initializable {
	// profile_placeholder.png
	public static String Image_path;
	// camera paramter
	private DaemonThread myThread = null;
	int count = 0;
	VideoCapture webSource = null;

	Mat frame = new Mat();
	MatOfByte mem = new MatOfByte();
	@FXML
	public ImageView image_show;
	@FXML
	public Button btn_voice;

	@FXML
	public ImageView friend_show;
	public static String path = "resources/telegram.mp3";
	public static boolean mute_sound;
	@FXML
	TextArea txt_message;
	@FXML
	ListView<HBox> txt_show;

	@FXML
	Button btn_send;
	@FXML
	HBox sendingPane;
	ClientManager clientManager;
	String from, to;
	ObjectInputStream input;
	ExecutorService clientExecutor;
  public static String GroupName ;
  Boolean isGroup;
  
  
  static String Path="";
	ObservableList<String> list_group = FXCollections.observableArrayList();

	public message_windowController(String getto, String getfrom, ClientManager getClientManager) {
		from = getfrom;
		to = getto;
		clientManager = getClientManager;
		clientExecutor = Executors.newCachedThreadPool();
		ObservableList<userdata> data = FXCollections.observableArrayList();
		data = new sqliteconnection().dataget();
		for (userdata userdata : data) {
			if (userdata.getIp().equals(to)) {
				Image_path = data.get(0).getPath();
				System.out.println(Image_path + "pppppppatttttttttt");
				break;
			} else {
				File f = new File("resources/profile_placeholder.png");
				Image_path = f.toURI().toString();
			}
		}

	}

	@FXML
	public void sendMessage() {
		 
			String message = to + " " + from + " : " + txt_message.getText();
		clientManager.sendMessage(message);
		txt_message.clear();
		HBox h = new HBox();
		Label lab = new Label(message.replaceFirst(to, "") + "\n");
		lab.setStyle("-fx-font: 35px 'Serif';-fx-text-fill: #020100;");

		h.getChildren().add(lab);
		h.setAlignment(Pos.BASELINE_RIGHT);
		//h.setStyle("-fx-background-color: #f9ddb1;");
		h.setPrefWidth(txt_show.getWidth() / 2);
		txt_show.getItems().add(h);
		
		// appendText();
		
 		
	}

	
	
	
	public static Stage waiting_stage;
	@FXML
	public void voice_fun() {
		String message = "Voice" + " " + to + " " + from;
		clientManager.sendMessage(message);
		System.out.println("voice function");
		System.out.println("voice function function function" + message);
		sound_func("resources/ringtone.mp3");
		show_waiting();
	}

	public static video_window_serverController video_controller;

	@FXML
	public void make_video_chat() {
		System.out.println(" video 000000000");
		try {
			String message = "video" + " " + to + " " + from;
			clientManager.sendMessage(message);
			System.out.println("video function   function" + message);
			Stage sound_stage = new Stage();
			FXMLLoader loader1 = new FXMLLoader(getClass().getResource("video_window_server.fxml"));
			Parent root1;
			video_controller = loader1.getController();
			root1 = loader1.load();
			Scene scene1 = new Scene(root1, 1000, 900);
			sound_stage.setScene(scene1);
			sound_stage.initModality(Modality.APPLICATION_MODAL);
			sound_stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void show_waiting() {
		Platform.runLater(() -> {
			waiting_stage = new Stage();
			Group root = new Group();
			Scene scene = new Scene(root, 300, 250);
			waiting_stage.setScene(scene);
			waiting_stage.setTitle("Progress Controls");
			final ProgressIndicator pin = new ProgressIndicator();
			pin.setPrefWidth(100);
			pin.setPrefHeight(100);
			pin.setStyle("-fx-progress-color: red;");
			pin.setProgress(-1);
			final VBox vb = new VBox();
			vb.setSpacing(5);
			vb.getChildren().addAll(pin, new Label("wait until accept"));
			scene.setRoot(vb);
			waiting_stage.initModality(Modality.APPLICATION_MODAL);
			 
			waiting_stage.showAndWait();

		});
	}
 public  static	MediaPlayer p  ;
	public static void sound_func(String path) {
		if (mute_sound) {
		} else {
			Group root = new Group();
			File file = new File(path);
			Media m = new Media(file.toURI().toString());
			  p = new MediaPlayer(m);
			MediaView v = new MediaView(p);
			root.getChildren().add(v);
			p.setCycleCount(100);
			p.play();
		}
	}

	public boolean test = false;
@FXML VBox texting_pane;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		try {
			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			texting_pane.setPrefWidth(primScreenBounds.getWidth()*.9);
			texting_pane.setPrefHeight(primScreenBounds.getHeight());
			sendingPane.setPrefWidth(primScreenBounds.getWidth()*.9);
	 	txt_show.setPrefWidth(primScreenBounds.getWidth()*.9);
	 	txt_show.setPrefHeight(primScreenBounds.getHeight()*.8);
	 	 btn_send.setPrefWidth(primScreenBounds.getWidth()*.1);
 	 	txt_message.setPrefWidth(primScreenBounds.getWidth()*.8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	// for sending and reciving File
	public   ServerSocket sock = null;

	@SuppressWarnings("resource")
	public   void OPEn_FILE_RECIVER(String FIleName) 
	{
		    try {
 			        sock = new ServerSocket(8080);	 
		    } catch (IOException e) {
		        System.out.println("Could not instantiate socket:");
		        e.printStackTrace();
		        return;
		    }
		    Socket clientSock = null;
		    while(true){
		        try{
		            System.out.println("Waiting for connection...");
		            clientSock = sock.accept();
		            final Socket fin = clientSock;
		            System.out.println("Connection accepted");
		            System.out.println("Spawning thread...");
		            Thread trd = new Thread(new Runnable(){
		                public void run(){
		                    try {
		                        try {
		                            Thread.sleep(5000);
		                        } catch (InterruptedException e) {
		                            e.printStackTrace();
		                        }
		                        System.out.println("Receiving video...");
	 	                        File video = new File("E:/"+FIleName );
		                        FileOutputStream fos = new FileOutputStream(video);
		                        byte[] data = new byte[1024];
		                        int count = fin.getInputStream().read(data, 0, 1024);
		                        while (count != -1){
		                            fos.write(data, 0, 1024);
		                            count = fin.getInputStream().read(data, 0, 1024);
		                        }
		                        fos.close();
		                        fin.close();
		                        System.out.println("Done receiving");
		    					Platform.runLater(() -> {
		    						HBox h	=new HBox();
		    						ImageView img=	new ImageView();
		    						img.setImage(new  javafx.scene.image.Image(video.toURI().toString()));
		    						img.setFitHeight(100);
		    						img.setFitWidth(100);
		    						h.getChildren().add(img);
		    					txt_show.getItems().add(h);
							
						 		
		    					});
		                        
		                    } catch (IOException e) {
		                        // TODO Auto-generated catch block
		                        e.printStackTrace();
		                    }}
		            });
		            trd.start();
 

		        }catch(IOException e){
		            System.out.println("Could not accept");
		            e.printStackTrace();
		        }
		    }
	}
	public static void OPEn_client(String IP)
	{Socket sock = null;

    try{	
        System.out.println("Connecting...");
        sock = new Socket(IP, 8080);
         InputStream is = new FileInputStream(new File("F:/ãÇí.jpg"));
        byte[] bytes = new byte[1024];
        OutputStream stream = sock.getOutputStream();
        int count = is.read(bytes, 0, 1024);
        while (count != -1){
            stream.write(bytes, 0, 1024);
           count = is.read(bytes, 0, 1024);
        }
        is.close();
        stream.close();
        sock.close();

    }catch (Exception e){
        e.printStackTrace();
    }
		
	}

	public     void Send_resposne_file(){
		 System.out.println( "#Send_resposne_fileSend_resposne_fileSend_resposne_file" );
		String message ="FILE_RESOPNESE "+ to + " " + from ;
		clientManager.sendMessage(message);
	}

	@FXML
	public void SendFile() {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select IMages files");
		fileChooser.getExtensionFilters().addAll( new ExtensionFilter("Image Files", "*.PNG"
				,"*.png","*.JPG","*.jpg","*.mp4"));
		File selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			    Path=selectedFile.getName();
			    System.out.println(Path);
			} 
		if (!Path.isEmpty()) {
			    String message ="FILE "+ to + " " + from +" "+Path;
				clientManager.sendMessage(message);
		}
	    Path=selectedFile.toURI().toString();

	   System.out.println("PathPathPathPath>>>>"+Path);
 	}
	
	
	
	
	
	
	
	
	static Socket sound_client;

	public static void MAKE_CALL_client(String IP) {
		try {
			// this iP for anther client

			sound_client = new Socket(IP, 8500);
			System.out.println("sssssssssssssssssound");
			Thread thread3 = new Thread(new threadssound(sound_client));
			thread3.setDaemon(true);
			thread3.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public class DaemonThread implements Runnable {

		protected volatile boolean runnable = false;

		public WritableImage wr = null;

		@Override
		public void run() {
			synchronized (this) {
				while (runnable) {
					if (webSource.grab()) {
						try {
							webSource.retrieve(frame);
							Highgui.imencode(".bmp", frame, mem);
							// System.out.println(frame.toString());
							// System.out.println("_______________________");

							if (test) {
								String message = to + " " + from + " : " + frame;
								clientManager.sendMessage(message);
							}
							Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));

							BufferedImage buff = (BufferedImage) im;
							// Graphics g=jPanel1.getGraphics();

							// if (g.drawImage(buff, 0, 0, getWidth(),
							// getHeight() -150 , 0, 0, buff.getWidth(),
							// buff.getHeight(), null))
							if (buff != null) {
								wr = new WritableImage(buff.getWidth(), buff.getHeight());
								PixelWriter pw = wr.getPixelWriter();
								for (int x = 0; x < buff.getWidth(); x++) {
									for (int y = 0; y < buff.getHeight(); y++) {
										pw.setArgb(x, y, buff.getRGB(x, y));
									}
								}
							}

							Platform.runLater(() -> {
								image_show.setImage(wr);

							});

							if (runnable == false) {
								System.out.println("Going to wait()");
								this.wait();
							}
						} catch (Exception ex) {
							System.out.println(ex.getMessage());
						}
					}
				}
			}
		}
	}

}


class SoundReceiver implements Runnable {
	Socket connection = null;
	DataInputStream soundIn = null;
	SourceDataLine inSpeaker = null;

	public SoundReceiver(Socket conn) throws Exception {
		connection = conn;
		soundIn = new DataInputStream(connection.getInputStream());
		AudioFormat af = new AudioFormat(8000.0f, 8, 1, true, false);
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
		inSpeaker = (SourceDataLine) AudioSystem.getLine(info);
		inSpeaker.open(af);
	}

	public void run() {
		int bytesRead = 0;
		byte[] inSound = new byte[1];
		inSpeaker.start();
		while (bytesRead != -1) {
			try {
				bytesRead = soundIn.read(inSound, 0, inSound.length);
			} catch (Exception e) {
			}
			if (bytesRead >= 0) {
				inSpeaker.write(inSound, 0, bytesRead);
			}
		}
	}
}
