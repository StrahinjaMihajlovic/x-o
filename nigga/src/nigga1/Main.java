package nigga1;

import java.io.*;
import java.net.*;



class ServerThread extends Thread {
    private Socket socket;
 
    public ServerThread(Socket socket) {
        this.socket = socket;
    }
 
    public void run() {
       try {
        	
    	   
    	   
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
          
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            	
 
            String text = "";
            	
           //Strahinja: uklonio sam do{}while() 
            //jer nije odgovarao potrebi i bacao je exception,
            //sledeci kod radi i back end i front end
            int ch = 0;
               while((ch = reader.read()) != -1) {
            	   char[] a = {(char)ch};
            	   System.out.println(a);
            	   text += new String(a,0,1);
               }
            //text = reader.readLine();
                
                String reverseText = new StringBuilder(new String(text)).reverse().toString();
                writer.println( reverseText );
                output.flush();
                output.close();
                
                System.out.println(reverseText);
                
 			
            socket.close();
            System.out.println(socket.isClosed());
            
           
        } catch (IOException ex ) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
       
    }
}

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//if (args.length < 1) return;
		 
        //int port = Integer.parseInt(args[0]);
 
		int port = 11000;
		
        try (ServerSocket serverSocket = new ServerSocket(port)) {
        	
            System.out.println("Server is listening on port " + port);
            
            while (true) {
            	
                Socket socket = serverSocket.accept();
               
                System.out.println("New client connected" + socket.isClosed());
                // Ima problem sa prijemom podataka posle prvog povezivanja.
                // ne znam da li je zbog thread-ova jer sa svakom konekcijom se pravi novi
                //a onaj stari i dalje radi. Videti da li se moze ugasiti thread kad
                // zavrsi ili na neki drugi nacin da se oslobodi socket(moja pretpostavka).
                new ServerThread(socket).start();
                
            }
            
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();

}
}
	}


