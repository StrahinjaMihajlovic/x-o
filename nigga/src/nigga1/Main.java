package nigga1;

import java.io.*;
import java.net.*;

class igra{
	public static char[][] matrix = null;
	public static int igr1 = 0;
	public static int igr2 = 0;
	public igra() {
		if (matrix == null) {
			matrix = new char[3][3];
			
			for(int i = 0; i<3;i++)
				for(int j = 0; j<3; j++)
					matrix[i][j] = '0';
				
		}
	}
	
	public void provera(int igrac) {
		char oznaka = igrac == 1 ? 'x':'o';
		char pobeda = igrac == 1 ? 't':'r';
		
		if(matrix[0][0]  == oznaka && matrix[0][1]  == oznaka &&matrix[0][2]  == oznaka ) {
			matrix[0][0] = pobeda;matrix[0][1] = pobeda;matrix[0][2] = pobeda;
		}
		if(matrix[1][0]  == oznaka && matrix[1][1]  == oznaka &&matrix[1][2]  == oznaka ) {
			matrix[1][0] = pobeda;matrix[1][1] = pobeda;matrix[1][2] = pobeda;
		}
		if(matrix[2][0]  == oznaka && matrix[2][1]  == oznaka &&matrix[2][2]  == oznaka ) {
			matrix[2][0] = pobeda;matrix[2][1] = pobeda;matrix[2][2] = pobeda;
		}
		
		if(matrix[0][0]  == oznaka && matrix[1][0]  == oznaka &&matrix[2][0]  == oznaka ) {
			matrix[0][0] = pobeda;matrix[1][0] = pobeda;matrix[2][0] = pobeda;
		}
		if(matrix[0][1]  == oznaka && matrix[1][1]  == oznaka &&matrix[2][1]  == oznaka ) {
			matrix[0][1] = pobeda;matrix[1][1] = pobeda;matrix[2][1] = pobeda;
		}
		if(matrix[0][2]  == oznaka && matrix[1][2]  == oznaka &&matrix[2][2]  == oznaka ) {
			matrix[0][2] = pobeda;matrix[1][2] = pobeda;matrix[2][2] = pobeda;
		}
		
		if(matrix[0][0]  == oznaka && matrix[1][1]  == oznaka &&matrix[2][2]  == oznaka ) {
			matrix[0][0] = pobeda;matrix[1][1] = pobeda;matrix[2][2] = pobeda;
		}
		if(matrix[0][2]  == oznaka && matrix[1][1]  == oznaka &&matrix[2][0]  == oznaka ) {
			matrix[0][2] = pobeda;matrix[1][1] = pobeda;matrix[2][0] = pobeda;
		}
	}
	
	public void potez(int polje, int igrac) {
		
			if(polje>=7 && matrix[2][polje-7] == '0') {
				
				matrix[2][polje-7] = igrac == 1 ? 'x':'o';
			}else if(polje>=4 && matrix[1][polje -4] == '0') {
				
				matrix[1][polje-4] = igrac == 1 ? 'x':'o';
			}else if(matrix[0][polje-1] == '0'){
				
				matrix[0][polje-1] = igrac == 1 ? 'x':'o';
			}
		provera(igrac);
	}

	@Override
	public String toString() {
		String str = "";
		for(char[] row : matrix)
			for(char x:row) {
				str += "" + x ;
			}
		return str;
	}
	
}

class ServerThread extends Thread {
    private Socket socket;
    private igra Igra;
    public ServerThread(Socket socket, igra Igra) {
        this.socket = socket;
        this.Igra = Igra;
    }
 
    public void run() {
       try {
        	
    	   
    	   
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
          
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            	
 
            int text = 0;
            	
           
            int ch = 0;
               while((ch = reader.read()) != -1 && Character.getNumericValue((char)ch) != -1) {
            	   text = text*10;
            	   text += Character.getNumericValue((char)ch);
            	   System.out.println(text);
            	   
               }
               text = text<10 ? 1:text;
            //text = reader.readLine();
                if(text>=10) {
                Igra.potez(text/10, text%10);
                writer.println( Igra.toString() );
                }else {
                	 if(igra.igr1 == 0) {
                		 igra.igr1 = 1;
                		 writer.println('1');
                	 }else if(igra.igr2 == 0) {
                		 igra.igr2 = 1;
                		 writer.println('2');
                	 }
                }
                
               
                output.flush();
                output.close();
                
                System.out.println(Igra.toString());
                
 			
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
		igra Igra = new igra();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
        	
            System.out.println("Server is listening on port " + port);
            
            while (true) {
            	
                Socket socket = serverSocket.accept();
               
                System.out.println("New client connected" + socket.isClosed());
                // Ima problem sa prijemom podataka posle prvog povezivanja.
                // ne znam da li je zbog thread-ova jer sa svakom konekcijom se pravi novi
                //a onaj stari i dalje radi. Videti da li se moze ugasiti thread kad
                // zavrsi ili na neki drugi nacin da se oslobodi socket(moja pretpostavka).
                new ServerThread(socket, Igra).start();
                
            }
            
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();

}
}
	}


