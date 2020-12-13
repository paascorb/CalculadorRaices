package principal;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClienteConsola {

	public static void main(String[] args) {
		
		//PEDIR EL POLINOMIO
		
		Scanner sc = new Scanner(System.in);
		String polinomioStr;
		ArrayList<Double> polinomio = null;
		
		System.out.println("-------------------------------------------------");
		System.out.println(".......BIENVENIDO AL CALCULADOR DE RAÍCES........");
		System.out.println("-------------------------------------------------");
		
		System.out.println("\n");
		System.out.println("Introduzca un polinomio (estructura: ax^n+bx^n-1+...+cx+d):");
		
		polinomioStr = sc.nextLine();
		
		while(!comprobarEstructura(polinomioStr)) {
			
			System.out.println("\n");
			System.out.println("Estructura inválida.");
			System.out.println("Introduzca un polinomio (estructura: ax^n+bx^n-1+...+cx+d):");
			
			polinomioStr = sc.nextLine();
			
		}
		
		polinomio = construirPolinomio(polinomioStr);
		
		//OLE OLE, YA ESTÁ CONSTRUIDO
		
		for(Double coef : polinomio) {
			System.out.println(coef + "");
		}
		
		try {
			Socket cliente = new Socket("localhost",48500);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean comprobarEstructura(String polStr) {
		
		boolean estructuraVálida = true;
		
		String monomios[] = polStr.split("\\+");
		String monomio;
		String coefGrado[];
		
		for(int i = monomios.length-1;i>=0;i--) {
			
			monomio = monomios[i];
			
			monomio = monomio.replaceAll(" ", "");
			
			coefGrado = monomio.split("x\\^");
			
			if(coefGrado.length == 1) {
				if(coefGrado[0].length()!=1 || !coefGrado[0].equals("x"))
				if(!isValidDouble(coefGrado[0]) && !isValidDouble(coefGrado[0].substring(0, coefGrado[0].length()-1))) {
					
					System.out.println("1");
					estructuraVálida = false;
					
				}
				
			}
			else {
				
				if(coefGrado[0].equals("")) coefGrado[0] = "1";
				
				if(!isValidDouble(coefGrado[0]) || !isValidInt(coefGrado[1])) {
					
					estructuraVálida = false;
				}
				
			}
			
		}
		
		return estructuraVálida;
		
	}
	
	private static ArrayList<Double> construirPolinomio(String polStr) {
		
		ArrayList<Double> polinomio = new ArrayList<Double>();
		
		String monomios[] = polStr.split("\\+");
		String monomio;
		String coefGrado[];
		int gradoActual = -1;
		
		for(int i = monomios.length-1;i>=0;i--) {
			
			monomio = monomios[i];
			
			monomio = monomio.replaceAll(" ", "");
			
			coefGrado = monomio.split("x\\^");
			
			if(coefGrado.length == 1) {
				
				if(coefGrado[0].equals("x")) coefGrado[0] = "1";
				
				coefGrado[0] = coefGrado[0].replaceAll("x", "");
				
				polinomio.add(Double.parseDouble(coefGrado[0]));
				gradoActual++;
				
			}
			else {
				
				if(coefGrado[0].equals("")) coefGrado[0] = "1";
				
				while(Integer.parseInt(coefGrado[1])-1 > gradoActual) {
					polinomio.add((double) 0.0);
					gradoActual++;
				}
				
				polinomio.add(Double.parseDouble(coefGrado[0]));
				gradoActual++;
				
			}
			
		}
		
		return polinomio;
		
	}
	
	private static boolean isValidDouble(String s) {
	    boolean isValid = true;

	    try {
	        Double.parseDouble(s);
	    } catch(NumberFormatException nfe) {
	        isValid = false;
	    }

	    return isValid;
	}
	
	private static boolean isValidInt(String s) {
	    boolean isValid = true;

	    try {
	        Integer.parseInt(s);
	    } catch(NumberFormatException nfe) {
	        isValid = false;
	    }

	    return isValid;
	}
	
}
