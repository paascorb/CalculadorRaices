package Cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
		
		while(!TrataPolinomiosUtil.comprobarEstructura(polinomioStr)) {
			
			System.out.println("\n");
			System.out.println("Estructura inválida.");
			System.out.println("Introduzca un polinomio (estructura: ax^n+bx^n-1+...+cx+d):");
			
			polinomioStr = sc.nextLine();
			
		}
		
		polinomio = TrataPolinomiosUtil.construirPolinomio(polinomioStr);
		
		//YA ESTÃ� CONSTRUIDO
		//5x^4+x+1
		System.out.println(polinomio.toString());
		
		try {
			Socket cliente = new Socket("localhost",48500);
			ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
			out.writeObject(polinomio);
			out.flush();
			DataInputStream in = new DataInputStream(cliente.getInputStream());
			String cadena = in.readLine();
			while(cadena!=null) {
				System.out.println(cadena);
				cadena = in.readLine();
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
//private static boolean comprobarEstructura(String polStr) {
//		
//		boolean estructuraVálida = true;
//		ArrayList<Integer> exponentes = new ArrayList();
//		
//		polStr = polStr.replaceAll("-", "+-");
//		
//		String monomios[] = polStr.split("\\+");
//		String monomio;
//		String coefGrado[];
//		
//		for(int i = monomios.length-1;i>=0;i--) {
//			
//			monomio = monomios[i];
//			
//			monomio = monomio.replaceAll(" ", "");
//			
//			coefGrado = monomio.split("x\\^");
//			
//			if(coefGrado.length == 1) {
//				
//				if(isValidDouble(coefGrado[0])) {
//					
//					//Caso de un double normal, monomio de grado 0
//					if(!isValidInt(coefGrado[0].substring(coefGrado[0].length()-1))) {
//						
//						estructuraVálida = false;
//						
//					}else exponentes.add(0);
//					
//				}
//				else if(coefGrado[0].equals("x")) {
//					
//					//Una x sola
//					exponentes.add(1);
//					
//				}
//				else if(isValidDouble(coefGrado[0].substring(0, coefGrado[0].length()-1)) && coefGrado[0].substring(coefGrado[0].length()-1).equals("x")) {
//					
//					//Es un polinomio de la forma ax
//					exponentes.add(1);
//					
//				}
//				else {
//					
//					estructuraVálida = false;
//					
//				}
//				
////				if(coefGrado[0].length()!=1 || !coefGrado[0].equals("x"))
////				if(!isValidDouble(coefGrado[0]) && (!isValidDouble(coefGrado[0].substring(0, coefGrado[0].length()-1)) || !coefGrado[0].substring(coefGrado[0].length()-1).equals("x"))) {
////					
////					estructuraVálida = false;
////					
////				}
//				
//			}
//			else {
//				
//				if(coefGrado[0].equals("")) coefGrado[0] = "1";
//				if(coefGrado[0].equals("-")) coefGrado[0] = "-1";
//				
//				if(!isValidDouble(coefGrado[0]) || !isValidInt(coefGrado[1])) {
//					
//					estructuraVálida = false;
//				}
//				else {
//					
//					exponentes.add(Integer.parseInt(coefGrado[1]));
//					
//				}
//				
//			}
//			
//		}
//		
//		if(estructuraVálida) {
//			
//			for(int i = 1; i < exponentes.size(); i++) {
//				
//				if(exponentes.get(i) < exponentes.get(i-1)) {
//					estructuraVálida = false;
//				}
//				
//			}
//			
//		}
//		
//		return estructuraVálida;
//		
//	}
//	
//	private static ArrayList<Double> construirPolinomio(String polStr) {
//		
//		ArrayList<Double> polinomio = new ArrayList<Double>();
//		
//		polStr = polStr.replaceAll("-", "+-");
//		
//		String monomios[] = polStr.split("\\+");
//		String monomio;
//		String coefGrado[];
//		int gradoActual = -1;
//		
//		for(int i = monomios.length-1;i>=0;i--) {
//			
//			monomio = monomios[i];
//			
//			monomio = monomio.replaceAll(" ", "");
//			
//			coefGrado = monomio.split("x\\^");
//			
//			if(coefGrado.length == 1) {
//				
//				if(isValidDouble(coefGrado[0])) {
//					
//					//Caso de un double normal, monomio de grado 0
//					polinomio.add(Double.parseDouble(coefGrado[0]));
//					gradoActual++;
//					
//				}
//				else if(coefGrado[0].equals("x")) {
//					
//					//Una x sola
//					if(gradoActual == -1) {
//						polinomio.add((double) 0.0);
//						gradoActual++;
//					}
//					
//					polinomio.add(Double.parseDouble("1"));
//					gradoActual++;
//					
//				}
//				else if(isValidDouble(coefGrado[0].substring(0, coefGrado[0].length()-1)) && coefGrado[0].substring(coefGrado[0].length()-1).equals("x")) {
//					
//					//Es un polinomio de la forma ax
//					if(gradoActual == -1) {
//						polinomio.add((double) 0.0);
//						gradoActual++;
//					}
//					
//					coefGrado[0] = coefGrado[0].replaceAll("x", "");
//					polinomio.add(Double.parseDouble(coefGrado[0]));
//					
//					gradoActual++;
//					
//				}
//				
////				if(coefGrado[0].equals("x")) coefGrado[0] = "1";
////				
////				coefGrado[0] = coefGrado[0].replaceAll("x", "");
////				
////				polinomio.add(Double.parseDouble(coefGrado[0]));
////				gradoActual++;
//				
//			}
//			else {
//				
//				if(coefGrado[0].equals("")) coefGrado[0] = "1";
//				if(coefGrado[0].equals("-")) coefGrado[0] = "-1";
//				
//				while(Integer.parseInt(coefGrado[1])-1 > gradoActual) {
//					polinomio.add((double) 0.0);
//					gradoActual++;
//				}
//				
//				polinomio.add(Double.parseDouble(coefGrado[0]));
//				gradoActual++;
//				
//			}
//			
//		}
//		
//		return polinomio;
//		
//	}
//	
//	private static boolean isValidDouble(String s) {
//	    boolean isValid = true;
//
//	    try {
//	        Double.parseDouble(s);
//	    } catch(NumberFormatException nfe) {
//	        isValid = false;
//	    }
//
//	    return isValid;
//	}
	
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
