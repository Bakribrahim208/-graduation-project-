package application;

import static application.ServerConstant.*;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class Clients implements Runnable {
	Socket client;
	ObjectInputStream input;
	ObjectOutputStream output;
	boolean keepListening;
	ServerManager serverManager;
	int clientNumber;
	ClientListener clientListener;

	public Clients(ClientListener getClientListener, Socket getClient, ServerManager getServerManager,
			int getClientNumber) {
		client = getClient;
		clientListener = getClientListener;
		try {
			serverManager = getServerManager;
			clientNumber = getClientNumber;
			input = new ObjectInputStream(client.getInputStream());
			output = new ObjectOutputStream(client.getOutputStream());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		keepListening = true;
	}
	// passssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss

	public void run() {
		String message = "", name = "";
		boolean sameName = false;
		while (keepListening) {
			try {
				message = (String) input.readObject();
				System.out.println("Server is receiving " + message);
				 
				StringTokenizer tokens = new StringTokenizer(message);
				String header = tokens.nextToken();
				name = tokens.nextToken();

				if (header.equalsIgnoreCase("login")) {
					if (header.equalsIgnoreCase("111")) {
						System.out.println("passssssssssssssssssssss true");
						serverManager.client[clientNumber] = null;
						serverManager.clientNumber--;
						System.out.println("passssssssssss  >>>" + serverManager.clientNumber);

						clientListener.signOut(name);
						serverManager.sendNameToAll(message);
						ServerManager.clientTracker[clientNumber] = "";
						keepListening = false;
					} else {
						// if make success login send his name to all clients
						serverManager.sendNameToAll(message);
						ServerManager.clientTracker[clientNumber] = name;

						for (int i = 0; i < serverManager.clientNumber; i++) // Create
																				// &
																				// send
																				// users
																				// list
						{
							String userName = ServerManager.clientTracker[i];
							if (!userName.equalsIgnoreCase("")) {
								output.writeObject("login " + userName + " " + client.getInetAddress());
								System.out.println("$$_------->>>" + client.getInetAddress().toString());
								output.flush();
							}
						}

						clientListener.signIn(name);
						clientListener.clientStatus(
								name + ": is signIn , IPaddress :" + "  ,portNumber :" + client.getPort());
						String ip = "";
						ip += client.getInetAddress();
						clientListener.mapped(name, ip);
					}

				} else if (header.equalsIgnoreCase(DISCONNECT_STRING)) {
					clientListener.signOut(name);
					serverManager.sendNameToAll(message);
					ServerManager.clientTracker[clientNumber] = "";
					keepListening = false;
				}

				else if (header.equalsIgnoreCase("video")) {

					System.out.println("videovideovideovideovideovideovideovideo");
					StringTokenizer tok = new StringTokenizer(message);
					tok.nextToken();
					String name_to = tok.nextToken();
					String name_from = tok.nextToken();
					String message1 = name_to + " video " + name_from;
					System.out.println("server server serverrrrrrrr video>>." + message1);
					serverManager.sendInfo(message1);
				} else if (header.equalsIgnoreCase("Voice")) {
					System.out.println(message + "oooooooooooooooorrrrrrrrr");
					StringTokenizer tok = new StringTokenizer(message);
					tok.nextToken();
					String name_to = tok.nextToken();
					String name_from = tok.nextToken();
					String message1 = name_to + " Voice " + name_from;
					System.out.println("server server serverrrrrrrr voice>>." + message1);
					serverManager.sendInfo(message1);

					// String rec_ip = Main_windowController.hm.get(name1);
					// String sen_ip = Main_windowController.hm.get(name);
					// String message1 = name+" Voice "+rec_ip.replaceFirst("/",
					// "");
				} else if (header.equalsIgnoreCase("Response")) {
					String mess = message;
					// header/to/from /response [true,false]
					StringTokenizer tok = new StringTokenizer(mess);
					tok.nextToken();// skip header
					String Res_to = tok.nextToken();
					String Res_from = tok.nextToken();// use it to get ip
					String sen_ip = Main_windowController.hm.get(Res_from);
					String response = tok.nextToken();
					String resoponse_message = Res_to + " VOICE_RESOPNSE " + response + " " + Res_from + " "
							+ sen_ip.replaceFirst("/", "");
					System.out.println("server ip backkkkkkkkkkkkkkkkkkkk" + resoponse_message);
					serverManager.sendInfo(resoponse_message);

				} else if (header.equalsIgnoreCase("Response_Video")) {
					String mess = message;
					StringTokenizer tok = new StringTokenizer(mess);
					tok.nextToken();// skip header
					String Res_to = tok.nextToken();
					String Res_from = tok.nextToken();// use it to get ip
					String sen_ip = Main_windowController.hm.get(Res_from);
					String response = tok.nextToken();
					String resoponse_message = Res_to + " Video_RESOPNSE " + response + " " + Res_from + " "
							+ sen_ip.replaceFirst("/", "");
					System.out.println("server ip backkkkkkkkkkkkkkkkkkkk" + resoponse_message);
					serverManager.sendInfo(resoponse_message);

				} 
				// for reciver
				else if (header.equalsIgnoreCase("FILE")) {
					 System.out.println( "#FILEFILEFILEFILEFILEFILEFILEFILEFILEFILE" );
						String mess = message;
						StringTokenizer tok = new StringTokenizer(message);
						tok.nextToken();
						String name_to = tok.nextToken();
						String name_from = tok.nextToken();
					
						String FIleName=tok.nextToken();
		     	       String reply=name_to+" FILE "+name_from +" "+FIleName;
					 serverManager.sendInfo(reply);
				
				}
				// send to sender
				else if (header.equalsIgnoreCase("FILE_RESOPNESE")) {
					 System.out.println( "#FILE_RESOPNESEFILE_RESOPNESEFILE_RESOPNESEFILE_RESOPNESEFILE_RESOPNESE" );
					 	String mess = message;
						StringTokenizer tok = new StringTokenizer(message);
						tok.nextToken();
						String  name_to = tok.nextToken();
						String name_from = tok.nextToken();
						String ip =  Main_windowController.hm.get(name_from);
		     	       String reply=name_to+" FILE_RESOPNESE "+name_from+" "+ip.replaceAll("/", "");
					 serverManager.sendInfo(reply); 
				
				}
				
				else {
					serverManager.sendInfo(message);
				}

			}
			catch (IOException ex) 
			{
				clientListener.signOut(name);
				serverManager.sendNameToAll(DISCONNECT_STRING + " " + name);
				ServerManager.clientTracker[clientNumber] = "";
				break;
			} 
			catch (ClassNotFoundException ex) 
			{
			}
		}
	}
}
