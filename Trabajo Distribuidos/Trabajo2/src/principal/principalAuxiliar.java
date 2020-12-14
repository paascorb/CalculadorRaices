package principal;

import java.awt.EventQueue;
import java.util.ArrayList;

public class principalAuxiliar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<String> sturm = new ArrayList();
		
		sturm.add("Primer elemento");
		sturm.add("Segundo elemento");
		sturm.add("Tercer elemento");
		sturm.add("Cuarto elemento");
		sturm.add("Quinto elemento");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInfoRaícesExtra frameInfo = new VentanaInfoRaícesExtra("polinomio", "derivada", "1 4", "2 5",  sturm, "1 2 3");
					frameInfo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
