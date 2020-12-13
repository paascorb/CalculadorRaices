package principal;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CalcularRaiz extends Thread{
	
	private ArrayList<Double> polinomio;
	private double puntoInicio;
	private double error;
	private double resultado;
	private CyclicBarrier cb;
	private Socket s;
	
	public CalcularRaiz(ArrayList<Double> pol, double puntoInicio,double error,CyclicBarrier cb,Socket s) {
		this.polinomio=pol;
		this.puntoInicio=puntoInicio;
		this.error=error;
		this.cb=cb;
		this.s = s;
	}
	
	
	public void run() {
	
		//Aplicaremos el metodo iterativo de Newton-Raphson para calcular la raiz en cuestion:
		double aproximacionAnterior=this.puntoInicio;
		ArrayList<Double> derivada = CalcularRaices.Derivar(this.polinomio);
		double aproximacion = this.puntoInicio-evaluacion(polinomio,this.puntoInicio)/evaluacion(derivada,this.puntoInicio);
		while(Math.abs(aproximacion-aproximacionAnterior)>error) {
			aproximacionAnterior=aproximacion;
			aproximacion = aproximacion - (evaluacion(polinomio,aproximacion)/evaluacion(derivada,aproximacion));
		}
		try {
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			cb.await();
			this.resultado=aproximacionAnterior;
			if(this.resultado<Math.pow(10, -10)) {
				out.writeBytes(String.valueOf(resultado)+" Aprox: 0"+"\r");
			}else
				out.writeBytes(String.valueOf(resultado)+"\r");
			out.flush();
			
		}catch(InterruptedException e) {
			e.printStackTrace();
		}catch(BrokenBarrierException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public double evaluacion(ArrayList<Double> polinomio, double x) {
		double resultado=0;
		for(int i=0;i<polinomio.size();i++) {
			resultado+=polinomio.get(i)*Math.pow(x, i);
		}
		return resultado;
	}
	
	public double getResultado() {
		return this.resultado;
	}

}
