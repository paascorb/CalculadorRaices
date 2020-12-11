package principal;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

	public class Servidor {

		public static void main(String[] args) {
			// TODO Auto-generated method stub
			try(ServerSocket server = new ServerSocket(48500)){
				ExecutorService pool = Executors.newCachedThreadPool();
				while(true) {
					try {
						Socket s = server.accept();
						DataInputStream in = new DataInputStream(s.getInputStream());
						String polinomio = in.readLine();
						CalcularRaices a = new CalcularRaices(s,polinomio);
						pool.execute(a);
					}catch(IOException e) {
						System.out.println(e);
					}	
				}
			}catch(IOException e) {
					System.out.println(e);
				}	
		}

	}