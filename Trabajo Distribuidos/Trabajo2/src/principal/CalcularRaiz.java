package principal;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CalcularRaiz extends Thread{
	
	private ArrayList<Float> polinomio;
	private float puntoInicio;
	private float error;
	private float resultado;
	private CyclicBarrier cb;
	
	public CalcularRaiz(ArrayList<Float> pol, float puntoInicio,float error,CyclicBarrier cb) {
		this.polinomio=pol;
		this.puntoInicio=puntoInicio;
		this.error=error;
		this.cb=cb;
	}
	
	
	public void run() {
	
		//Aplicaremos el metodo iterativo de Newton-Raphson para calcular la raiz en cuestion:
		float aproximacionAnterior=this.puntoInicio;
		ArrayList<Float> derivada = CalcularRaices.Derivar(this.polinomio);
		float aproximacion = evaluacion(polinomio,this.puntoInicio)/evaluacion(derivada,this.puntoInicio);
		while(Math.abs(aproximacion-aproximacionAnterior)>error) {
			aproximacionAnterior=aproximacion;
			aproximacion = aproximacion - (evaluacion(polinomio,aproximacion)/evaluacion(derivada,aproximacion));
		}
		try {
			cb.await();
			this.resultado=aproximacionAnterior;
		}catch(InterruptedException e) {
			e.printStackTrace();
		}catch(BrokenBarrierException e) {
			e.printStackTrace();
		} 	
	}
	
	public float evaluacion(ArrayList<Float> polinomio, float x) {
		float resultado=0;
		for(int i=0;i<polinomio.size();i++) {
			resultado+=polinomio.get(i)*Math.pow(x, i);
		}
		return resultado;
	}
	
	public float getResultado() {
		return this.resultado;
	}

}
