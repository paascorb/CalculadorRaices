package principal;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalcularRaices extends Thread{
	
	private Socket s;
	private ArrayList<Float> polinomio;
	
	public CalcularRaices(Socket s,ArrayList<Float> polinomio) {
		this.s = s;
		this.polinomio = polinomio;
	}
	
	public void run() {
		//En este hilo que será unico para cada cliente, 1º con la regla de signos de Descartes
		//tratar de ver más o menos cuantas raices positivas y negativas reales.
		//2º acotación de las raices con MacLaurin
		//3º (puramente educativo) buscamos posibles raíces enteras usando la regla de Horner.
		//4º aplicamos sturm para obtener la secuencia del mismo {P(x),P1(x),...,Pn(x)} donde P es nuestro polinomio, P1 su derivada y 
		//Pk(x) es el resto cambiado de signo de Pk-2(x)/Pk-1(x) para 2<=k<=n.
		int maxRaicesPositivas,maxRaicesNegativas;
		int NumCambiosDeSignoRNeg=0,NumCambiosDeSignoRPos=0;
		
		//Aplicamos la regla de signos de Descartes
		for(int i=0; i<this.polinomio.size()-1; i++) {
			if(this.polinomio.get(i)*this.polinomio.get(i+1)<0) {
				NumCambiosDeSignoRPos++;
			}else {
				if(i%2 == 0) {
					if(this.polinomio.get(i)*(-1*this.polinomio.get(i+1))<0) {
						NumCambiosDeSignoRNeg++;
					}
				}else {
					if(this.polinomio.get(i+1)*(-1*this.polinomio.get(i))<0) {
						NumCambiosDeSignoRNeg++;
					}
				}
				
			}
		}
		maxRaicesPositivas=NumCambiosDeSignoRPos;
		maxRaicesNegativas=NumCambiosDeSignoRNeg;
		
		System.out.println("Positivas: "+maxRaicesPositivas+" Negativas: "+maxRaicesNegativas);
		
		//Ahora vamos a calcular las cotas con el Método de MacLaurin -> (1/(1+μ) < |ζi| < (1+λ) donde {ζ1,...,ζn} C Complejos las raices n raices. 
		//Primero calculamos μ, con μ = máximo de {|ak/an|} donde 1<=k<=n y a es el coeficiente de x.
		float mu=(this.polinomio.get(0)/this.polinomio.get(this.polinomio.size()-1));
		for(int i=0;i<this.polinomio.size();++i) {
			if(mu<this.polinomio.get(i)/this.polinomio.get(this.polinomio.size()-1))
				mu=this.polinomio.get(i)/this.polinomio.get(this.polinomio.size()-1);
		}
		
		float cotaMinima = 1/(1+mu);
		System.out.println("Cota Minima: ±"+cotaMinima);
		//Ahora calculamos λ, con λ = máximo de {|ak/a0|} donde 1<=k<=n y a es el coeficiente de x.
		float lambda=1;
		for(int i=0;i<this.polinomio.size();++i) {
			if(lambda<this.polinomio.get(i)/this.polinomio.get(0))
				lambda=this.polinomio.get(i)/this.polinomio.get(0);
		}
		float cotaMaxima = 1+lambda;
		System.out.println("Cota Máxima: ±"+cotaMaxima);
		
		//Usando la regla de Horner calculamos las posibles raices enteras, este paso es totalmente opcional, es solo puramente lúdico.
		ArrayList<Float> PosiblesRaicesEnteras = new ArrayList();
		Float a0 = this.polinomio.get(0);
		for(int i=1;i<=a0;i++) {
			if(a0%i==0) {
				PosiblesRaicesEnteras.add((float)i);
				PosiblesRaicesEnteras.add((float)-i);
			}
		}
		
		System.out.println(PosiblesRaicesEnteras.toString());
		
		//aplicamos sturm:
		
		//Primero calculamos la derivada:
		ArrayList<Float> p1 = this.Derivar(this.polinomio);
		System.out.println(p1.toString());
		
		//Calculamos la secuencia:
		ArrayList<ArrayList<Float>> listaDePolinomios = new ArrayList();
		listaDePolinomios.add(this.polinomio);
		listaDePolinomios.add(p1);
		boolean RaicesSimples = SecuenciaSturm(p1,this.polinomio,listaDePolinomios,this.polinomio.size()-1,2);
		
		for(int i=0; i<listaDePolinomios.size();i++) {
			System.out.println(listaDePolinomios.get(i).toString());
		}
		
		//Teniendo la secuencia de sturm aplicamos el teorema sirviendonos del metodo de la bisección hasta encontrar 1 solo cambio de signo:
		System.out.println("Nº cambios de signo en la cota superior negativa: "+TeoremaSturm(listaDePolinomios,cotaMaxima*-1));
		System.out.println("Nº cambios de signo en cero: "+TeoremaSturm(listaDePolinomios,(float)0));
		System.out.println("Nº cambios de signo en la cota superior positiva: "+TeoremaSturm(listaDePolinomios,cotaMaxima));

		//if(!RaicesSimples&&(TeoremaSturm(listaDePolinomios,cotaMaxima*-1)-TeoremaSturm(listaDePolinomios,(float)0))==this.polinomio.size()-1) {
			try{
				ExecutorService pool = Executors.newCachedThreadPool();
				CyclicBarrier barrera = new CyclicBarrier(2);
				CalcularRaiz a = new CalcularRaiz(this.polinomio,(cotaMaxima*-1+0)/2,(float)Math.pow(10, -10),barrera);
				pool.execute(a);
				barrera.await();
				System.out.println("Resultado: "+a.getResultado());
			}catch(BrokenBarrierException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		//}
		
		
		
	}
	
	public static ArrayList<Float> Derivar(ArrayList<Float> polinomio){
		ArrayList<Float> derivada = new ArrayList();
		for(int i=1; i<polinomio.size();i++) {
			derivada.add(polinomio.get(i)*i);
		}
		return derivada;
	}
	
	public boolean SecuenciaSturm(ArrayList<Float> imenos2,ArrayList<Float> imenos1, ArrayList<ArrayList<Float>> listaDePolinomios, int grado, int numPaso){
		if(numPaso<=grado) {
			float ultimoCoefi1 = imenos1.get(imenos1.size()-1);
			int gradoDeCoefi1 = imenos1.size()-1;
			float ultimoCoefi2 = imenos2.get(imenos2.size()-1);
			int gradoDeCoefi2 = imenos2.size()-1;
			ArrayList<Float> aux = new ArrayList();
			ArrayList<Float> copia = (ArrayList<Float>)imenos1.clone();
			while(gradoDeCoefi2<=gradoDeCoefi1) {
				float coeficoci = ultimoCoefi1/ultimoCoefi2;
				int gradococi = gradoDeCoefi1-gradoDeCoefi2;
				for(int i=0;i<gradoDeCoefi1;i++) {
					aux.add(i, null);
				}
				for(int i=0;i<=gradoDeCoefi2-1;i++) {
					float resta = imenos2.get(i)*coeficoci;
					aux.set(i+gradococi,copia.get(i+gradococi)-resta);
				}
				for(int i=0;i<gradoDeCoefi1;i++) {
					if(aux.get(i)==null) {
						aux.set(i, copia.get(i));
					}
				}
				copia = (ArrayList<Float>)aux.clone();
				aux.clear();
				gradoDeCoefi1 = copia.size()-1;
				ultimoCoefi1 = copia.get(copia.size()-1);
			}
			if(copia.size()==1) {
				if(copia.get(copia.size()-1)==0) {
					return false;
				}else {
					for(int i=0; i<copia.size();i++) {
						copia.set(i,copia.get(i)*-1);
					}
					listaDePolinomios.add(copia);
					return true;
				}
			}else {
				for(int i=0; i<copia.size();i++) {
					copia.set(i,copia.get(i)*-1);
				}
				listaDePolinomios.add(copia);
				return SecuenciaSturm(copia,imenos2,listaDePolinomios,grado,(numPaso+1));
			}
		}else {
			return false;
		}
	}
	
	public int TeoremaSturm(ArrayList<ArrayList<Float>> listaDePolinomios,float x) {
		ArrayList<String> signos = new ArrayList();
		for(int i=0;i<listaDePolinomios.size();i++) {
			float total=0;
			for(int j=0;j<listaDePolinomios.get(i).size();j++) {
				total += listaDePolinomios.get(i).get(j)*Math.pow(x, j);
			}
			if(total<0) {
				signos.add("-");
			}else {
				signos.add("+");
			}
		}
		int cambios=0;
		for(int i=0;i<signos.size();i++) {
			if(i<signos.size()-1&&!(signos.get(i).equals(signos.get(i+1)))) {
				cambios++;
			}
		}
		return cambios;
	}

}
