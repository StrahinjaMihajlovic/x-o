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
 
 
            String text;
            	
           //Strahinja: uklonio sam do{}while() 
            //jer nije odgovarao potrebi i bacao je exception,
            //sledeci kod radi i back end i front end
                text = reader.readLine();
                String reverseText = new StringBuilder(text).reverse().toString();
                writer.println("Server: " + reverseText);
                System.out.println(text);
            
 			
            socket.close();
        } catch (IOException ex) {
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
                System.out.println("New client connected");
                
                new ServerThread(socket).start();
            }
            
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();

}
}
	}


