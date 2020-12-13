package principal;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.JList;
import java.awt.TextField;
import java.awt.List;
import java.awt.Scrollbar;
import java.awt.Label;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import java.awt.Insets;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class ClienteGUIa extends JFrame {

	private JPanel contentPane;
	private String polinomioStr;
	ArrayList<Double> polinomio = null;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteGUIa frame = new ClienteGUIa();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClienteGUIa() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClienteGUIa.class.getResource("/Imagenes/favicon_2.png")));
		setResizable(false);
		setForeground(Color.LIGHT_GRAY);
		setFont(new Font("Arial Black", Font.BOLD, 14));
		setTitle("Calculador de Ra\u00EDces");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 771, 530);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Informaci\u00F3n");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Esto muestra información sobre aquí los pibes gordos que han desarrollado la aplicación que están full gordos los pibes.
				
			}
		});
		menuBar.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Ayuda");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Al pulsar esto sale un tremendo tuto de cómo usar la aplicación y las vergas esas. Todo guapo emberdá.
				
			}
		});
		mntmNewMenuItem.setHorizontalAlignment(SwingConstants.RIGHT);
		menuBar.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setForeground(Color.BLACK);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(35, 30, 419, 106);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(524, 30, 195, 106);
		contentPane.add(panel_1);
		
		JButton btnInfo = new JButton("Info.");
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Con esto los atributos que ha recogido los manda a otra pestañita o lo que sea y los muestra súper refacheramente.
				
			}
		});
		btnInfo.setBounds(603, 420, 116, 22);
		contentPane.add(btnInfo);
		
		TextField tfPolinomio = new TextField();
		tfPolinomio.setBounds(35, 200, 684, 22);
		contentPane.add(tfPolinomio);
		
		TextField tfMensajes = new TextField();
		tfMensajes.setEnabled(false);
		tfMensajes.setBounds(35, 228, 536, 22);
		contentPane.add(tfMensajes);
		
		List listRaices = new List();
		listRaices.setEnabled(false);
		listRaices.setMultipleSelections(true);
		listRaices.setBounds(35, 312, 536, 130);
		contentPane.add(listRaices);
		
		Label label = new Label("Ra\u00EDces:");
		label.setFont(new Font("Arial Black", Font.BOLD, 17));
		label.setBounds(35, 277, 116, 22);
		contentPane.add(label);
		
		Label label_1 = new Label("Polinomio:");
		label_1.setFont(new Font("Arial Black", Font.BOLD, 17));
		label_1.setBounds(35, 164, 116, 22);
		contentPane.add(label_1);
		
		Label label_1_1 = new Label("Estructura: ax^m+bx^n+...+cx+d, m>n");
		label_1_1.setFont(new Font("Arial Black", Font.BOLD, 14));
		label_1_1.setBounds(448, 164, 271, 22);
		contentPane.add(label_1_1);
		
		JButton btnCalcularRacies = new JButton("Calcular ra\u00EDces");
		btnCalcularRacies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Con esto recoge lo del text y manda petiçao al servidor, ahí el pibe gordo refachero.
				//Después recibe las raíces y las pone en la lista. Luego recibe la info adicional y eso, y la guarda en atributos o lo que sea el man.
				
				listRaices.removeAll();
				
				polinomioStr = tfPolinomio.getText();
				
				if(!comprobarEstructura(polinomioStr)) {
					
					tfMensajes.setText("Estructura del polinomio inválida.");
					
				}
				else {
					
					polinomio = construirPolinomio(polinomioStr);
					
					//AHORA MANDAMOS EL MENSAJE AL SERVIDOR, OLE OLE
					
					ObjectOutputStream out = null;
					DataInputStream in = null;
					
					try {
						
						Socket cliente = new Socket("localhost",48500);
						
						out = new ObjectOutputStream(cliente.getOutputStream());
						out.writeObject(polinomio);
						out.flush();
						
						in = new DataInputStream(cliente.getInputStream());
						String cadena = in.readLine();
						
						while(cadena!=null) {
							
							listRaices.add(cadena);
							cadena = in.readLine();
							
						}
						
					}catch(IOException e) {
						e.printStackTrace();
					}
					
				}
				
			}
		});
		btnCalcularRacies.setBounds(577, 228, 142, 22);
		contentPane.add(btnCalcularRacies);
		
		Label label_2 = new Label("M\u00E1s info.");
		label_2.setFont(new Font("Arial Black", Font.BOLD, 17));
		label_2.setBounds(603, 364, 116, 22);
		contentPane.add(label_2);
		
		Label label_3 = new Label("de las ra\u00EDces:");
		label_3.setFont(new Font("Arial Black", Font.BOLD, 17));
		label_3.setBounds(603, 392, 116, 22);
		contentPane.add(label_3);
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
