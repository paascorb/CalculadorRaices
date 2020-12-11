package principal;

import java.net.Socket;

public class CalcularRaices extends Thread{
	
	private Socket s;
	private String polinomio;
	
	public CalcularRaices(Socket s, String polinomio) {
		this.s = s;
		this.polinomio = polinomio;
	}
	
	public void run() {
		//En este hilo que será unico para cada cliente, 1º con la regla de signos de Descarte
		//tratar de ver más o menos cuantas raices positivas y negativas reales.
		//2º acotación de las raices con MacLaurin
		//3º
		
		
	}

}
