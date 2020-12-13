package principal;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
						ArrayList<Double> polinomio = new ArrayList();
//						polinomio.add((double)5);
//						polinomio.add((double)3);
//						polinomio.add((double)-2);
//						polinomio.add((double)0);
//						polinomio.add((double)6);
//						polinomio.add((double)5);
//						polinomio.add((double)9);
//						polinomio.add((double)-12);
//					
//						polinomio.add((double)1);
//						polinomio.add((double)2);
//						polinomio.add((double)1);
//						polinomio.add((double)0);
//						polinomio.add((double)0);
//						polinomio.add((double)1);
						
//						polinomio.add((double)-6);
//						polinomio.add((double)4);
//						polinomio.add((double)2);
						
						polinomio.add((double)0);
						polinomio.add((double)2);
						polinomio.add((double)0);
						polinomio.add((double)-4);
						polinomio.add((double)0);
						polinomio.add((double)1);
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