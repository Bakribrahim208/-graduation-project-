package client;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

 import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
 import javafx.scene.image.WritableImage;
import javafx.stage.Screen;
public class video_windowController implements Initializable {
    
	 private DaemonThread myThread = null;
	    int count = 0;
	    VideoCapture webSource = null;

	    Mat frame = new Mat();
	    MatOfByte mem = new MatOfByte();
	    public static byte[] byteImage;
	    Socket s,s1;
	@FXML 
    ImageView  Friend_cam, me_cam;
    @FXML 
   Button btn_video ,btn_text,btn_finish;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
       // Friend_cam.setFitWidth(primScreenBounds.getWidth());
       // Friend_cam.setFitHeight(primScreenBounds.getHeight());
	}
	  public DaemonThread1 myThread1 = null;
	  ServerSocket serverSocket ;
	    Socket socket;
		
	    @FXML 
	    public void Start_video_chat(){
	    	  myThread1 = new DaemonThread1();
	          Thread t1 = new Thread(myThread1);
	          t1.setDaemon(true);
	          myThread1.runnable = true;
	          t1.start();
 		
	}

		@FXML 
	public void Start_SEND(){
		System.out.println("prrrrrrrressssss");
		webSource =new VideoCapture(0);
		 myThread = new DaemonThread();
         Thread t = new Thread(myThread);
         t.setDaemon(true);
         myThread.runnable = true;
         t.start();  
	}

	// open my cam
		 class DaemonThread implements Runnable
		    {
			 public  WritableImage wr = null;

		    protected volatile boolean runnable = false; 

		    @Override
		    public  void run()
		    {
		        
		        try {
		            s=new Socket("192.168.43.27",3300);
		        } catch (IOException ex) {
		            //Logger.getLogger(videoClient.class.getName()).log(Level.SEVERE, null, ex);
		        }
		        synchronized(this) 
		        {
		            while(runnable)
		            {
		                if(webSource.grab())
		                {
				    	try
		                        {
		                webSource.retrieve(frame);
					    Highgui.imencode(".bmp", frame, mem);
					    Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));

					    BufferedImage buff = (BufferedImage) im;
					   /* if (buff!= null) {
					         wr = new WritableImage(buff.getWidth(), buff.getHeight());
					         PixelWriter pw = wr.getPixelWriter();
					         for (int x = 0; x < buff.getWidth(); x++) {
					             for (int y = 0; y < buff.getHeight(); y++) {
					                 pw.setArgb(x, y, buff.getRGB(x, y));
					             }
					         }
					     }*/
  	                    javafx.scene.image.Image image = SwingFXUtils.toFXImage(buff, null);

					    
					    Platform.runLater(() -> {
					    	 me_cam.setImage(image);			    
					  				});
 		                            
					    if(runnable == false)
		                            {
					    	System.out.println("Going to wait()");
					    	this.wait();
					    }
		                            
		                             ByteArrayOutputStream baos = new ByteArrayOutputStream();
		                            ImageIO.write(buff, "bmp", baos);
		                           // baos.flush();
		                            byteImage = baos.toByteArray();                      
		            OutputStream os = s.getOutputStream();
		 
					ObjectOutputStream oos = new ObjectOutputStream(os);
					oos.writeObject(byteImage); 
		                                
					 }
					 catch(Exception ex)
		                         {
					    System.out.println( ex.getMessage());
		                         }
		                }
		            }
		        }
		     }
		   }
		     public  class DaemonThread1 implements Runnable
		    {                        
				 public  WritableImage wr1 = null;
      
		    protected volatile boolean runnable = false;

		    @Override
		    public  void run()
		    {
		         try{
		            serverSocket = new ServerSocket(4400);
		            socket = serverSocket.accept();
		    }catch(Exception e)
		    {
		    }
		       synchronized(this)
		        {
		            while(true)
		            {
		                
				    	
		                        try
		                        {
		                            
		                            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		                            byte[] byteImage = (byte[])ois.readObject();
		                            InputStream in = new ByteArrayInputStream(byteImage);
		                             BufferedImage buff1 = ImageIO.read(in);
		                             //javafx.scene.image.Image image = SwingFXUtils.toFXImage(buff1, null);
		                           /*  if (buff1!= null) {
		        				         wr1 = new WritableImage(buff1.getWidth(), buff1.getHeight());
		        				         PixelWriter pw = wr1.getPixelWriter();
		        				         for (int x = 0; x < buff1.getWidth(); x++) {
		        				             for (int y = 0; y < buff1.getHeight(); y++) {
		        				                 pw.setArgb(x, y, buff1.getRGB(x, y));
		        				             }
		        				         }
		        				     }*/
		      	                    javafx.scene.image.Image image = SwingFXUtils.toFXImage(buff1, null);

		        				     Platform.runLater(() -> {
	     
		        				    	 Friend_cam.setImage(image);			    

		        				  				});
		                             
 
 		                                if(runnable == false)
		                            {
					    	System.out.println("Going to wait()");
					    	this.wait();
					    }
		                                }
		                        catch(Exception ex)
		                         {
					    System.out.println("Error hahahah");
		                         }
		                
		            }
		        }
		     }
		   }

	
	
}
