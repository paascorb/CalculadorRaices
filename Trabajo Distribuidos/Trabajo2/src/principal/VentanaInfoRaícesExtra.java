package principal;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.JList;
import java.awt.List;

public class VentanaInfoRaícesExtra extends JFrame {

	private JPanel contentPane;
	private JTextField tfPolinomio;
	private JTextField tfDescartesPositivas;
	private JTextField tfDerivada;
	private JTextField tfCotasMínima;
	private JTextField tfCotasMáxima;
	private JTextField tfDescartesNegativas;
	private JTextField tfCambiosNegativa;
	private JTextField tfCambiosCero;
	private JTextField tfCambiosPositiva;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VentanaInfoRaícesExtra frame = new VentanaInfoRaícesExtra();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public VentanaInfoRaícesExtra(String polinomio, String derivada, String descartes, String cotas, String sturm, String cambiosSigno) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaInfoRaícesExtra.class.getResource("/Imagenes/iconoRefachero.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 684, 573);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Descartes (num. m\u00E1x.):");
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblNewLabel.setBounds(38, 92, 197, 22);
		contentPane.add(lblNewLabel);
		
		tfPolinomio = new JTextField();
		tfPolinomio.setEditable(false);
		tfPolinomio.setBounds(258, 28, 373, 20);
		contentPane.add(tfPolinomio);
		tfPolinomio.setColumns(10);
		tfPolinomio.setText(polinomio);
		
		JLabel lblNewLabel_1 = new JLabel("Polinomio:");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblNewLabel_1.setBounds(38, 28, 119, 22);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblPositivas = new JLabel("Positivas:");
		lblPositivas.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblPositivas.setBounds(258, 95, 87, 22);
		contentPane.add(lblPositivas);
		
		JLabel lblNegativas = new JLabel("Negativas:");
		lblNegativas.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblNegativas.setBounds(452, 95, 93, 22);
		contentPane.add(lblNegativas);
		
		tfDescartesPositivas = new JTextField();
		tfDescartesPositivas.setEditable(false);
		tfDescartesPositivas.setBounds(355, 98, 76, 20);
		contentPane.add(tfDescartesPositivas);
		tfDescartesPositivas.setColumns(10);
		tfDescartesPositivas.setText(descartes.split(" ")[0]);
		
		JLabel lblNewLabel_1_1 = new JLabel("Derivada:");
		lblNewLabel_1_1.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(38, 59, 119, 22);
		contentPane.add(lblNewLabel_1_1);
		
		tfDerivada = new JTextField();
		tfDerivada.setEditable(false);
		tfDerivada.setColumns(10);
		tfDerivada.setBounds(258, 59, 373, 20);
		contentPane.add(tfDerivada);
		tfDerivada.setText(derivada);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Cotas:");
		lblNewLabel_1_1_1.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblNewLabel_1_1_1.setBounds(38, 125, 119, 22);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblMnima = new JLabel("M\u00EDnima:");
		lblMnima.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblMnima.setBounds(258, 127, 87, 22);
		contentPane.add(lblMnima);
		
		JLabel lblMxima = new JLabel("M\u00E1xima:");
		lblMxima.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblMxima.setBounds(452, 127, 93, 22);
		contentPane.add(lblMxima);
		
		tfCotasMínima = new JTextField();
		tfCotasMínima.setEditable(false);
		tfCotasMínima.setColumns(10);
		tfCotasMínima.setBounds(355, 131, 76, 20);
		contentPane.add(tfCotasMínima);
		tfCotasMínima.setText(cotas.split(" ")[0]);
		
		tfCotasMáxima = new JTextField();
		tfCotasMáxima.setEditable(false);
		tfCotasMáxima.setColumns(10);
		tfCotasMáxima.setBounds(555, 130, 76, 20);
		contentPane.add(tfCotasMáxima);
		tfCotasMáxima.setText(cotas.split(" ")[1]);
		
		tfDescartesNegativas = new JTextField();
		tfDescartesNegativas.setEditable(false);
		tfDescartesNegativas.setColumns(10);
		tfDescartesNegativas.setBounds(555, 97, 76, 20);
		contentPane.add(tfDescartesNegativas);
		tfDescartesNegativas.setText(descartes.split(" ")[1]);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Coeficientes de la secuencia de Sturm:");
		lblNewLabel_1_1_1_1.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblNewLabel_1_1_1_1.setBounds(38, 177, 343, 22);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(38, 162, 593, 8);
		contentPane.add(separator);
		
		//Aquí genero los elementos
//		String elementos = "";
//		for(String elemento : sturm) {
//					
//			elementos = elementos + "\r\n" + elemento;
//					
//		}
//		elementos = elementos.substring(2);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Cambios de signo en la cota superior negativa:");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblNewLabel_1_1_1_1_1.setBounds(38, 423, 415, 22);
		contentPane.add(lblNewLabel_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_2 = new JLabel("Cambios de signo en cero:");
		lblNewLabel_1_1_1_1_2.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblNewLabel_1_1_1_1_2.setBounds(38, 456, 343, 22);
		contentPane.add(lblNewLabel_1_1_1_1_2);
		
		JLabel lblNewLabel_1_1_1_1_3 = new JLabel("Cambios de signo en la cota superior positiva");
		lblNewLabel_1_1_1_1_3.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblNewLabel_1_1_1_1_3.setBounds(38, 489, 393, 22);
		contentPane.add(lblNewLabel_1_1_1_1_3);
		
		tfCambiosNegativa = new JTextField();
		tfCambiosNegativa.setEditable(false);
		tfCambiosNegativa.setColumns(10);
		tfCambiosNegativa.setBounds(555, 426, 76, 20);
		contentPane.add(tfCambiosNegativa);
		tfCambiosNegativa.setText(cambiosSigno.split(" ")[0]);
		
		tfCambiosCero = new JTextField();
		tfCambiosCero.setEditable(false);
		tfCambiosCero.setColumns(10);
		tfCambiosCero.setBounds(555, 459, 76, 20);
		contentPane.add(tfCambiosCero);
		tfCambiosCero.setText(cambiosSigno.split(" ")[1]);
		
		tfCambiosPositiva = new JTextField();
		tfCambiosPositiva.setEditable(false);
		tfCambiosPositiva.setColumns(10);
		tfCambiosPositiva.setBounds(555, 492, 76, 20);
		contentPane.add(tfCambiosPositiva);
		tfCambiosPositiva.setText(cambiosSigno.split(" ")[2]);
		
		List list = new List();
		list.setBounds(38, 205, 593, 212);
		contentPane.add(list);
		
		//Aquí genero los elementos
		for(String elemento : procesarSturm(sturm)) {
			
			list.add(elemento);
							
		}
	}
	
	private static ArrayList<String> procesarSturm(String sturm){
		
		ArrayList<String> sturmArr = new ArrayList<String>();
		
		String sturmAux = sturm.substring(1,sturm.length()-1).replace(" ", "");
		String sturmAux2 = sturmAux.replace("]", "]]");
		String[] sturmVec = sturmAux2.split("],");
		for(String sturmElem : sturmVec) {
			sturmArr.add(sturmElem.replace("[", "").replace("]", ""));
		}
		
		return sturmArr;
		
	}
}
