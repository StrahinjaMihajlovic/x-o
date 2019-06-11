package nigga1;

import java.io.*;
import java.net.*;

class igra{
	public static char[][] matrix = null;
	public static int igr1 = 0; // flagovi da se proveri da li su igraci povezani i koji je zadnji igrao
	public static int igr2 = 0;
	public static int zadnji = 0;
	public static int prvi = 0;
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
		// proveravamo svaku mogucnost za pobedom i zamenimo odgovarajucim simbolima
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
		// i onda proverimo da li je nereseno i restartujemo matricu u svakim slucajevima.
		int brojac = 0;;
		for(int i = 0; i<3;i++)
			for(int j = 0; j<3; j++)
				brojac += igra.matrix[i][j] == '0' ? 1:0;
		if(brojac == 0) {
			igra.prvi = igra.prvi == 1 ? 2:1;
			for(int i = 0; i<3;i++)
				for(int j = 0; j<3; j++)
					igra.matrix[i][j] = '0';
			//igra.prvi = igra.prvi == 1 ? 2:1;
			}
	}
	
	public void potez(int polje, int igrac) throws Exception {
		if(prvi == 0) 
			prvi = igrac;
		try {
			if(zadnji == igrac) {
				return; //preskoci ako je vec igrao.
			}else if(polje>=7 && matrix[2][polje-7] == '0') {
				zadnji = igrac;
				matrix[2][polje-7] = igrac == 1 ? 'x':'o';
			}else if(polje>=4 && matrix[1][polje -4] == '0') {
				zadnji = igrac;
				matrix[1][polje-4] = igrac == 1 ? 'x':'o';
			}else if(matrix[0][polje-1] == '0'){
				zadnji = igrac;
				matrix[0][polje-1] = igrac == 1 ? 'x':'o';
			}
		provera(igrac);
		}catch(ArrayIndexOutOfBoundsException e) {
			throw new Exception();
		}
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
                if(text>=10) { //proverimo da li je igrac novopovezan ili neko ko je
                	//jedan od igraca. Sa klijentske strane dobijemo broj koji kad se
                	//podeli daje polje koje je igrac igrao, a modul daje koji je igrac
                	//pitanju
                try {
					Igra.potez(text/10, text%10);
					 writer.println( Igra.toString() );
				} catch (Exception e) {
					 writer.println( Igra.toString() );
				}
               
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
                //restartujemo matricu i menjamo prvog igraca ako dodje do pobede
                if(Igra.toString().indexOf("t") >(-1) || Igra.toString().indexOf("r") >(-1)) {
                	for(int i = 0; i<3;i++) {
        				for(int j = 0; j<3; j++) {
        					igra.matrix[i][j] = '0';
	                	}
	                		
	                }
                	igra.prvi = igra.prvi == 1 ? 2:1;
                }
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


