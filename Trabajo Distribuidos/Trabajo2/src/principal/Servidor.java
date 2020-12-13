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
						ArrayList<Float> polinomio = new ArrayList();
//						polinomio.add((float)5);
//						polinomio.add((float)3);
//						polinomio.add((float)-2);
//						polinomio.add((float)0);
//						polinomio.add((float)6);
//						polinomio.add((float)5);
//						polinomio.add((float)9);
//						polinomio.add((float)-12);
//					
//						polinomio.add((float)1);
//						polinomio.add((float)2);
//						polinomio.add((float)1);
						polinomio.add((float)0);
						polinomio.add((float)0);
						polinomio.add((float)1);
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