package client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
	public static void main(String[] args){
	    ServerSocket sock = null;
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
 	                        File video = new File("E:/bakr.jpg");
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
}
