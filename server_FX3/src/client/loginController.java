package client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.sun.xml.internal.ws.api.pipe.ServerPipeAssemblerContext;
import com.sun.xml.internal.ws.dump.LoggingDumpTube.Position;

import application.Main_windowController;
import application.ServerManager;
import client_fx.playsournd;
import database.sqliteconnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.userdata;

public class loginController implements Initializable {
	int j;
	ObservableList<String> list_model_friends = FXCollections.observableArrayList();
	ObservableList<String> list_model__ONLINE = FXCollections.observableArrayList();
	@FXML
	TextField userName; // for username
	@FXML
	TextField server_IP; // ip of server
	@FXML
	TextField server_Port;// port of server
	@FXML
	Button signin; // sign in button
	@FXML
	Label Lab_status;
	@FXML
	StackPane stack_pane;
	@FXML
	TabPane tabPane;
	@FXML
	TabPane USER_tabPane;
	@FXML
	VBox log_pane;
	@FXML
	Pane main_Pane; // it is show after login
	@FXML
	Button btn_frineds_wind;
	@FXML
	ListView<String>ONLINE_users
	;
	@FXML
	ListView<HBox>friends ;
	
	
	
	ClientManager clientManager;
	ClientStatusListener clientStatus;
	ClientListListener clientListListener;
	ClientWindowListener clientWindowListener;
	ServerSettings serverSettings;
	String USer;
	int messagingFrameNo = 0;
 
 public	message_windowController[] messagingFrames;

	
	
	@FXML
	ImageView img_user ,img_background,Background_imag;
	@FXML Pane	Detials_pane;
	@FXML StackPane	backgrond_parent;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
           Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();

           img_user.setFitWidth(primScreenBounds.getWidth()/7);
           img_user.setFitHeight(primScreenBounds.getHeight()/4);
           img_background.setFitWidth((primScreenBounds.getWidth()/4)*3.5);
           img_background.setFitHeight(primScreenBounds.getHeight()/4);
		Detials_pane.setTranslateX ( primScreenBounds.getWidth()/4); 
		
		
		server_IP.setText("127.0.0.1");
		server_Port.setText("12345");
		log_pane.toFront();
		main_Pane.setVisible(false);

		
		
		

	 	main_Pane.setPrefWidth(primScreenBounds.getWidth());
	 	main_Pane.setPrefHeight(primScreenBounds.getHeight());

		// friends list
		USER_tabPane.setPrefHeight(primScreenBounds.getHeight());
		USER_tabPane.setPrefWidth(primScreenBounds.getWidth()*.2);
		USER_tabPane.setPadding(new Insets(30, 10, 10, 10)); 
		
		backgrond_parent.setPrefHeight(primScreenBounds.getHeight());
		backgrond_parent.setPrefWidth(primScreenBounds.getWidth()*.8);
		Background_imag.setFitHeight(primScreenBounds.getHeight());
		
		Background_imag.setFitWidth(primScreenBounds.getWidth()*.8);
fill_friend_list();
		
	
		
		
		
	}
	public void fill_friend_list(){
		// fill friends list
		 
		ObservableList<userdata> data = FXCollections.observableArrayList();
		
		data = new sqliteconnection().dataget();
		for (userdata User : data) {
             File fil =new File("src/resources/Offline.png");
             Label label =new Label(User.getIp());
             label .setTranslateY(25);
             label.setWrapText(true);
             label.setStyle("-fx-font: 20px 'Serif';-fx-text-fill: #f3530a;");
             ImageView img=new ImageView(new Image(fil.toURI().toString()));
             img.setFitHeight(25);
             img.setFitWidth(20);
             img .setTranslateY(25);
             ImageView img_user;
              if (User.getPath().equals("")) {
                 fil =new File("src/resources/profile_placeholder.png");
                 img_user =new ImageView(new Image(fil.toURI().toString()));
              }
             else
                 img_user=new ImageView(new Image(User.getPath()));

 
             System.out.println(User.getPath()+User.getIp());
             img_user.setFitHeight(50);
             img_user.setFitWidth(50);
             HBox hbo=new HBox();
             hbo.setSpacing(10);
    hbo.getChildren().addAll(img_user,label,img);
friends.getItems().add(hbo);
		}
		
		
	}
	public loginController(ClientManager getClientManager) {
		clientStatus = new myClientStatus();
		clientListListener = new myClientListListener();
		clientWindowListener = new MyClientWindowListener();
		messagingFrames = new message_windowController[100];
		// initComponents();
		clientManager = getClientManager;
		// myPanel.setLayout(new BorderLayout());
		// addLogInPanel();
	}

	@FXML
	public void click_list() throws IOException {
		int selected = friends.getSelectionModel().getSelectedIndex();
	Label l =	 (Label) friends.getItems().get(selected).getChildren().get(1);
 		String to = l.getText().toString();
System.out.println(">>>>>>>"+to);
		 	boolean isWindowOpen = false;
		for (int i = 0; i < messagingFrameNo; i++) {
			if (messagingFrames[i].to.equalsIgnoreCase(to)) {
				isWindowOpen = true;
				System.out.println("1111111111");
				break;
			}
		}
		if (!isWindowOpen) {
			System.out.println("clicked");
			Tab tab = new Tab();
			tab.setText(to);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("message_window.fxml"));
			message_windowController control = new message_windowController(to, USer, clientManager);
			System.out.println("_________________________________" + to + USer);
			loader.setController(control);
			messagingFrames[messagingFrameNo] = control;
			// messagingFrames[messagingFrameNo].setVisible(true);
			messagingFrameNo++;
			tab.setClosable(false);
			tab.setContent((Node) loader.load());
			tabPane.getTabs().add(tab);
		}
	}

	@FXML
	public void sign_function() {
		if (!userName.getText().isEmpty()) {
			String sIp = server_IP.getText();
			String sPort = server_Port.getText();
 			if (sIp != null && sPort != null) {

				main_Pane.toFront();
				log_pane.setVisible(false);
				log_pane.toBack();
				main_Pane.setVisible(true);
				clientManager.connect(clientStatus, sIp, sPort);
				// addBuddyList();
				USer = userName.getText();
				// setTitle("Video Messenger ("+userName+")");
				// passssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
				clientManager.sendMessage("login " + USer);
				clientManager.receiveMessage(clientListListener, clientWindowListener);

			} else {
				// javax.swing.JOptionPane.showMessageDialog(this,"Please enter
				// your Server Settings");
				System.out.println("Please enter your Server Settings");
			}

		} else
			System.out.println("Please enter your Name ");
		// javax.swing.JOptionPane.showMessageDialog(this,"Please enter your
		// Name ");
	}

	class myClientStatus implements ClientStatusListener {
		public void loginStatus(String status) {
			Lab_status.setText(status);
		}

		@Override
		public void logout(String status) {
			Lab_status.setText(status);

		}
	}

	class myClientListListener implements ClientListListener {
		public void addToList(String usersName) {
			if (!usersName.equalsIgnoreCase(USer)) {

				Platform.runLater(() -> {
					list_model__ONLINE.add(usersName);
					ONLINE_users.getItems().add(usersName);
				
					//	ObservableList<userdata> data = FXCollections.observableArrayList();
					
					//data = new sqliteconnection().dataget();
					/*for (userdata User : data) {

						if (User.getIp().equals(usersName)) {

							list_model_friends.add(usersName);
							HBox h =new HBox();
							h.getChildren().add(new Label(usersName));
							friends.getItems().add(h);
							break;
						}  
					}*/
				/*	for (int i = 0; i < data.size(); i++) {
						if (data.get(i).getIp().equals(usersName)) {
				             File fil =new File("src/resources/online.png");
							ImageView v=new ImageView(new Image(fil.toURI().toString()));
							v.setFitHeight(25);
				             v.setFitWidth(20);
				             v .setTranslateY(30);
							//fil =new File("src/resources/profile_placeholder.png");
							fil =new File(data.get(i).getPath());
		
							ImageView vd=new ImageView(new Image(fil.toURI().toString()));

							vd.setFitHeight(50);
							vd.setFitWidth(50);
							Label la =new Label(data.get(i).getIp());
                             la .setTranslateY(30);
							HBox h =new HBox();
							h.getChildren().addAll(vd,la,v);
							
							friends.getItems().get(i).getChildren().clear();
							friends.getItems().get(i).getChildren().addAll(vd,la,v);

						}
						
						 
					}
*/
					ObservableList<userdata> data = FXCollections.observableArrayList();
					
					data = new sqliteconnection().dataget();
					int i =0;
					for (userdata User : data) {
						
						if (User.getIp().equals(usersName)) {
							
			             File fil =new File("src/resources/online.png");
			             Label label =new Label(User.getIp());
			             label .setTranslateY(25);
			             label.setWrapText(true);
			             label.setStyle("-fx-font: 20px 'Serif';-fx-text-fill: #f3530a;");
			             ImageView img=new ImageView(new Image(fil.toURI().toString()));
			             img.setFitHeight(25);
			             img.setFitWidth(20);
			             img .setTranslateY(25);
			             ImageView img_user;
			              if (User.getPath().equals("")) {
			                 fil =new File("src/resources/profile_placeholder.png");
			                 img_user =new ImageView(new Image(fil.toURI().toString()));
			              }
			             else{
			                 img_user=new ImageView(new Image(User.getPath()));
				             System.out.println(User.getPath()+User.getIp()+"ellllllse");

			             }

			 
			             img_user.setFitHeight(50);
			             img_user.setFitWidth(50);
			             friends.getItems().get(i).getChildren().clear();
							friends.getItems().get(i).getChildren().addAll(img_user,label,img);
					}
						i++;
					}
				});
			}
		}

		public void removeFromList(String userName) {
			/*Platform.runLater(() -> {
				list_model_friends.remove((Object) userName);
				friends.getItems().clear();
				ONLINE_users.getItems().addAll(list_model_friends);

				list_model__ONLINE.remove((Object) userName);
				ONLINE_users.getItems().clear();
				//ONLINE_users.getItems().addAll(list_model_friends);

			});*/
			
			
			if (!userName.equalsIgnoreCase(USer)) {

				Platform.runLater(() -> {
					//list_model__ONLINE.add(usersName);
					//ONLINE_users.getItems().add(usersName);
				
					//	ObservableList<userdata> data = FXCollections.observableArrayList();
					
					//data = new sqliteconnection().dataget();
					/*for (userdata User : data) {

						if (User.getIp().equals(usersName)) {

							list_model_friends.add(usersName);
							HBox h =new HBox();
							h.getChildren().add(new Label(usersName));
							friends.getItems().add(h);
							break;
						}  
					}*/
				/*	for (int i = 0; i < data.size(); i++) {
						if (data.get(i).getIp().equals(usersName)) {
				             File fil =new File("src/resources/online.png");
							ImageView v=new ImageView(new Image(fil.toURI().toString()));
							v.setFitHeight(25);
				             v.setFitWidth(20);
				             v .setTranslateY(30);
							//fil =new File("src/resources/profile_placeholder.png");
							fil =new File(data.get(i).getPath());
		
							ImageView vd=new ImageView(new Image(fil.toURI().toString()));

							vd.setFitHeight(50);
							vd.setFitWidth(50);
							Label la =new Label(data.get(i).getIp());
                             la .setTranslateY(30);
							HBox h =new HBox();
							h.getChildren().addAll(vd,la,v);
							
		192.168.43.27					friends.getItems().get(i).getChildren().clear();
							friends.getItems().get(i).getChildren().addAll(vd,la,v);

						}
						
						 
					}
*/
					ObservableList<userdata> data = FXCollections.observableArrayList();
					
					data = new sqliteconnection().dataget();
					int i =0;
					for (userdata User : data) {
						
						if (User.getIp().equals(userName)) {
							
			             File fil =new File("src/resources/Offline.png");
			             Label label =new Label(User.getIp());
			             label .setTranslateY(25);
			             label.setWrapText(true);
			             label.setStyle("-fx-font: 20px 'Serif';-fx-text-fill: #f3530a;");
			             ImageView img=new ImageView(new Image(fil.toURI().toString()));
			             img.setFitHeight(25);
			             img.setFitWidth(20);
			             img .setTranslateY(25);
			             ImageView img_user;
			              if (User.getPath().equals("")) {
			                 fil =new File("src/resources/profile_placeholder.png");
			                 img_user =new ImageView(new Image(fil.toURI().toString()));
			              }
			             else{
			                 img_user=new ImageView(new Image(User.getPath()));
				             System.out.println(User.getPath()+User.getIp()+"ellllllse");

			             }

			 
			             img_user.setFitHeight(50);
			             img_user.setFitWidth(50);
			             friends.getItems().get(i).getChildren().clear();
							friends.getItems().get(i).getChildren().addAll(img_user,label,img);
					}
						i++;
					}
				});
			}
		}
	}

	class MyClientWindowListener implements ClientWindowListener {
		int openWindowNo=0;
		boolean result;

		public void openWindow(String message) {
			String temp = message;
			// tag to [this client] tag from [other client]
			StringTokenizer tokenss = new StringTokenizer(temp);
			String tag = tokenss.nextToken();
			String Voice_to = tokenss.nextToken(); // лси
			tokenss.nextToken();
			String FROM = tokenss.nextToken();
			if (tag.equalsIgnoreCase("#VoiceVoice")) {
				Platform.runLater(() -> {
					try {
						 
						Stage primaryStage = new Stage();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("approval_meassage.fxml"));
						Parent root;
						root = loader.load();
						Scene scene = new Scene(root);
						primaryStage.setScene(scene);
						primaryStage.initModality(Modality.APPLICATION_MODAL);
						approval_meassageController controller = loader.getController();
						primaryStage.showAndWait();
						result = controller.app;

						if (primaryStage.isShowing()) {

						} else {
							String message0 = "Response" + " " + FROM + " " + USer + " " + result;
							clientManager.sendMessage(message0);
							// show server window
							if (result) {

								Stage sound_stage = new Stage();
								FXMLLoader loader1 = new FXMLLoader(getClass().getResource("sound_server.fxml"));
								Parent root1;
								ServerSocket serversound2=new ServerSocket(4000);
								 Socket sound_server=serversound2.accept();
							System.out.println("beeeeeeeeekoooo");
								sound_serverController con =new sound_serverController(serversound2,sound_server);
								loader1.setController(con);	
								root1 = loader1.load(); 
								
								Scene scene1 = new Scene(root1, 400, 400);
								sound_stage.setScene(scene1);
								sound_stage.initModality(Modality.APPLICATION_MODAL);
								sound_stage.showAndWait();
								controller.p.stop();
                                  System.out.println("ddddd");
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			} else if (tag.equalsIgnoreCase("#VOICE_RESOPNSE")) {
				System.out.println("VOICE_RESOPNSEVOICE_RESOPNSE):):):):):):):):):):):):):");
				System.out.println(message + "oooooooooooooooorginal message");
				// close waiting window
				String Resopnse = message;
				StringTokenizer tok = new StringTokenizer(Resopnse);
				tok.nextToken();//Tag
				tok.nextToken();//to
				tok.nextToken();//tag
				String res = tok.nextToken(); //resopnse
				String from = tok.nextToken();//from
				String IP = tok.nextToken(); //ip
				// this code for sender
				if (res.equalsIgnoreCase("true")) {
					// make voice call
					System.out.println("yeeeeeeeeesssssssssessssssssssssss");

					for (int l = 0; l < messagingFrameNo; l++) {
						j = l;
						if (messagingFrames[l].to.equalsIgnoreCase(from)) {
							Platform.runLater(() -> {
								messagingFrames[j].MAKE_CALL_client(IP);
								messagingFrames[j].p.stop();
							});
						}
					}
				} else {

					for (int i = 0; i < messagingFrameNo; i++) {
						j = i;
						if (messagingFrames[i].to.equalsIgnoreCase(from)) {
							Platform.runLater(() -> {
								messagingFrames[j].waiting_stage.close();
								messagingFrames[j].p.stop();

							});
						}
					}
				}
			}
			// for reciever client
			else if (tag.equalsIgnoreCase("#VideoVideo")) {

				Platform.runLater(() -> {
					try {

						Stage primaryStage = new Stage();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("approval_meassage.fxml"));
						Parent root;
						root = loader.load();
						Scene scene = new Scene(root, 400, 400);
						primaryStage.setScene(scene);
						primaryStage.initModality(Modality.APPLICATION_MODAL);
						approval_meassageController controller = loader.getController();
						primaryStage.showAndWait();
						result = controller.app;
						System.out.println(result + "loooooooooooooooog");
						if (primaryStage.isShowing()) {
						} else {
							String message0 = "Response_Video" + " " + FROM + " " + USer + " " + result;
							clientManager.sendMessage(message0);
							// show server window
							if (result) {
								Stage Video_stage = new Stage();
								FXMLLoader loader1 = new FXMLLoader(getClass().getResource("video_window.fxml"));
								Parent root1;
								root1 = loader1.load();
								video_windowController contrller = loader1.getController();
								// contrller.Start_SEND();
								Scene scene1 = new Scene(root1, 1000, 1000);
								Video_stage.setScene(scene1);
								Video_stage.initModality(Modality.APPLICATION_MODAL);
								Video_stage.showAndWait();
							}
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});

			}

			else if (tag.equalsIgnoreCase("#Video_RESOPNSE")) {
				System.out.println("Video_RESOPNSE):):):):):):):):):):):):):");
				System.out.println(message + "oooooooooooooooorginal message");

				// close waiting window
				String Resopnse = message;
				StringTokenizer tok = new StringTokenizer(Resopnse);
				tok.nextToken();
				tok.nextToken();
				tok.nextToken();
				String res = tok.nextToken();
				String from = tok.nextToken();
				// this code for sender
				if (res.equalsIgnoreCase("true")) {
					// make video call
					System.out.println("yeeeeeeeeesssssssssessssssssssssss");
					for (int l = 0; l < messagingFrameNo; l++) {
						j = l;
						if (messagingFrames[l].to.equalsIgnoreCase(from)) {
							Platform.runLater(() -> {
								// messagingFrames[j].MAKE_CALL_client();
								// messagingFrames[j].video_controller.Start_SEND();
							});
						}
					}
				} else {

					for (int i = 0; i < messagingFrameNo; i++) {
						j = i;
						if (messagingFrames[i].to.equalsIgnoreCase(from)) {
							Platform.runLater(() -> {
								messagingFrames[j].waiting_stage.close();
							});
						}
					}
				}
			} 
			
			
			
			else if (tag.equalsIgnoreCase("#FILE")) {
            	System.out.println("Recivigng FILE");
            	
            //	#FILE to FILE From Filename
            	String Resopnse = message;
            	
				StringTokenizer tok = new StringTokenizer(Resopnse);
				tok.nextToken();
				tok.nextToken();
				tok.nextToken();
				
               String from = tok.nextToken();
               String FileName= tok.nextToken();
				for (int i = 0; i < messagingFrameNo; i++) {
					j = i;
					if (messagingFrames[i].to.equalsIgnoreCase(from))
					{
		
 								messagingFrames[j].Send_resposne_file();
								messagingFrames[j].OPEn_FILE_RECIVER(FileName);
								 try {
									messagingFrames[j].sock.close() ;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

						 
							}
							 
						 
			
					
					 
				}	 
			}
			
			
			else if (tag.equalsIgnoreCase("#FILE_RESOPNESE")) {
            	//System.out.println("Recivigng FILE");
            	
            //	#FILE /to /FILE /From /reciver_ip
            	String Resopnse = message;
            	
				StringTokenizer tok = new StringTokenizer(Resopnse);
				tok.nextToken();
				tok.nextToken();
				tok.nextToken();
				
               String from = tok.nextToken();
               String IP=tok.nextToken();
				for (int i = 0; i < messagingFrameNo; i++) {
					j = i;
					if (messagingFrames[i].to.equalsIgnoreCase(from)) {
 							//messagingFrames[j].OPEn_FILE_RECIVER();
 							messagingFrames[j].OPEn_client(IP);
 					}
				}	 
			}
				
			else {
 				boolean isWindowOpen = false;
				StringTokenizer tokens = new StringTokenizer(message);
				String to = tokens.nextToken();
				String from = tokens.nextToken();
				// to get the client window if it is opened before
				for (int i = 0; i < messagingFrameNo; i++) {
					if (messagingFrames[i].to.equalsIgnoreCase(from)) {
						isWindowOpen = true;
						openWindowNo = i;
						break;
					}
				}
				// if the widnow is opened before it will append text
				if (isWindowOpen){
					Platform.runLater(() -> {
						// messagingFrames[openWindowNo].txt_show.appendText(message.replaceFirst(to,
						// "") + "\n");
						HBox h = new HBox();
						h.setAlignment(Pos.BASELINE_LEFT);
						ImageView image;
						image = new ImageView(messagingFrames[openWindowNo].Image_path);
						image.setFitHeight(50);
						image.setFitWidth(50);
						h.getChildren().add(image);
						Label lab = new Label(message.replaceFirst(to, "") + "\n");
						lab.setStyle("-fx-font: 35px 'Bodoni MT Bold Italic';-fx-text-fill: #f3530a;");

						h.getChildren().add(lab);
                          
						messagingFrames[openWindowNo].txt_show.getItems().add(h);
						playsournd play=new playsournd();
						play.sound_func("src/resources/telegram.mp3");
					});
				} else {
					 
 					Tab tab = new Tab();
					tab.setText(from);
					FXMLLoader loader = new FXMLLoader(getClass().getResource("message_window.fxml"));
					message_windowController control = new message_windowController(from, USer, clientManager);
					loader.setController(control);
					messagingFrames[messagingFrameNo] = control;
					Platform.runLater(() -> {
						// messagingFrames[openWindowNo].txt_show.appendText(message.replaceFirst(to,
						// "") + "\n");
						HBox new_message = new HBox();
						new_message.setAlignment(Pos.BASELINE_LEFT);
						ImageView image;
						image = new ImageView(messagingFrames[openWindowNo].Image_path);
						image.setFitHeight(50);
						image.setFitWidth(50);
						new_message.getChildren().add(image);
						String ds=message.replaceFirst(from, "");
						ds =ds.replaceFirst(to, "");
						Label lab = new Label(ds + "\n");
						lab.setStyle("-fx-font: 35px 'Serif';-fx-text-fill: #f3530a;");

						new_message.getChildren().add(lab);
						System.out.println(message.replaceFirst(to, "") +"??????????  "+openWindowNo);
						tab.setClosable(false);

						try {
							tab.setContent((Node) loader.load());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						tabPane.getTabs().add(tab);
						messagingFrames[messagingFrameNo].txt_show.getItems().add(new_message);
						messagingFrameNo++;
						playsournd play=new playsournd();
					play.sound_func("src/resources/notification.wav");});
					
				}
			}
		}

		public void closeWindow(String getMessage) {
			// myPanel.remove(buddyList);
			// addLogInPanel();
			// mi_sign_out.setEnabled(false);
			// mi_sign_in.setEnabled(true);
			// lb_status.setText(getMessage);
		}

		public void fileStatus(String filesStatus) {
			// lb_status.setText(filesStatus);
		}
	}
	
	@FXML
	public void show_friend_window() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_freind.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void show_grouChat() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("groupChat.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			groupChatController group =fxmlLoader.getController();
			stage.setScene(new Scene(root1));
			stage.initModality(Modality.APPLICATION_MODAL);

			stage.showAndWait();
			Tab tab = new Tab();
			 tab.setText(group.group_name.getText().toString());
			FXMLLoader loader = new FXMLLoader(getClass().getResource("message_window.fxml"));
			message_windowController control = new message_windowController("#group "+group.group_name.getText().toString(),USer,clientManager);
			loader.setController(control);
			messagingFrames[messagingFrameNo] = control;
 

			// messagingFrames[messagingFrameNo].setVisible(true);
			messagingFrameNo++;
			tab.setClosable(true);
			tab.setContent((Node) loader.load());
			tabPane.getTabs().add(tab);

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




}
