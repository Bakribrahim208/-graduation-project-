package client;

import javax.media.*;   
import javax.media.rtp.*;   
import javax.media.rtp.event.*;   
import javax.media.rtp.rtcp.*;   
import javax.media.protocol.*;   
import javax.media.protocol.DataSource;   
import javax.media.format.AudioFormat;   
import javax.media.format.VideoFormat;   
import javax.media.Format;   
import javax.media.format.FormatChangeEvent;   
import javax.media.control.BufferControl;   
   

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
 

public class MessageRecever implements Runnable
{
    ObjectInputStream input;
    boolean keepListening=true;
    ClientListListener clientListListener;
    ClientWindowListener clientWindowListener;
    ClientManager clientManager;
    Socket clientSocket;
    ExecutorService clientExecutor;
   


    MessageRecever(Socket getClientSocket,ClientListListener getClientListListener ,ClientWindowListener getClientWindowListener,ClientManager getClientManager)
    {
        clientExecutor=Executors.newCachedThreadPool();
        clientManager=getClientManager;
        clientSocket=getClientSocket;
        try
        {
            input = new ObjectInputStream(getClientSocket.getInputStream());
        }
        catch (IOException ex)
        {}
        clientListListener=getClientListListener;
        clientWindowListener=getClientWindowListener;
    }
    public void run()
    {
        String message,name="",ips = "";
        while(keepListening)
        {
            try
            {
                message = (String) input.readObject();
                System.out.println("user is receiving "+ message);
                StringTokenizer tokens=new StringTokenizer(message);
                String header=tokens.nextToken();
                if(tokens.hasMoreTokens())
                    name=tokens.nextToken();
     
                	
                if(header.equalsIgnoreCase("login"))
                {
                    clientListListener.addToList(name);
 
                }
                else if(header.equalsIgnoreCase(ClientConstant.DISCONNECT_STRING))
                {
                    clientListListener.removeFromList(name);
                     
                }
                else if(header.equalsIgnoreCase("server"))
                {
                    clientWindowListener.closeWindow(message);
                }
                
                // Video 
                
                else if(name.equalsIgnoreCase("video"))
                {
                	String s ="#VideoVideo "+message;
                  	 System.out.println("EEEEEEEEnter VideoVideo VideoVideo ");
                       	System.out.println("VideoVideoVideoVideoVideoVideoVideoVideo  :)");
                       	System.out.println(message+" :)");
                           clientWindowListener.openWindow(s);
                }
                
                else if(name.equalsIgnoreCase("Video_RESOPNSE"))
                {
                	String s ="#Video_RESOPNSE"+message;
            	 System.out.println("VOICE_RESOPNSE VOICE_RESOPNSE VOICE_RESOPNSE ");
                	System.out.println("VOICE_RESOPNSE  :)");
                	System.out.println(message+" :)");
                    clientWindowListener.openWindow(s);
                    
                }
                else if(name.equalsIgnoreCase("Voice"))
                {
                	String s ="#VoiceVoice "+message;
           	 System.out.println("EEEEEEEEnter voice VoiceVoiceVoiceVoice ");
                	System.out.println("VoiceVoiceVoiceVoiceVoiceVoiceVoice  :)");
                	System.out.println(message+" :)");
                    clientWindowListener.openWindow(s);
                    
                }
                
                else if(name.equalsIgnoreCase("VOICE_RESOPNSE"))
                {
                	String s ="#VOICE_RESOPNSE "+message;
            	 System.out.println("VOICE_RESOPNSE VOICE_RESOPNSE VOICE_RESOPNSE ");
                	System.out.println("VOICE_RESOPNSE  :)");
                	System.out.println(message+" :)");
                    clientWindowListener.openWindow(s);
                    
                }
                
                else if(name.equalsIgnoreCase("FILE"))
                {
                	String s ="#FILE "+message;
                    clientWindowListener.openWindow(s);
                    
                }
                else if(name.equalsIgnoreCase("FILE_RESOPNESE"))
                {
                	String s ="#FILE_RESOPNESE "+message;
           	 
                    clientWindowListener.openWindow(s);
                    
                }
                
                else
                {
                    clientWindowListener.openWindow(message);
                }
            }
            catch (IOException ex)
            {
                clientListListener.removeFromList(name);
            }
            catch (ClassNotFoundException ex)
            {

            }
        }
    }

    void stopListening()
    {
        keepListening=false;
    }
}
