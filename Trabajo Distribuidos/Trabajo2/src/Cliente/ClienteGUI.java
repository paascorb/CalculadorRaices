package Cliente;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.List;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ClienteGUI extends JFrame {

	private JPanel contentPane;
	private String polinomioStr;
	ArrayList<Double> polinomio = null;
	
	//Info de las raíces:
	String derivada;
	String descartes;
	String cotas;
	String sturm;
	String cambiosSigno;
	String numeroRaices;
	
	private JTextField tfServerIP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteGUI frame = new ClienteGUI();
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
	public ClienteGUI() {
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(ClienteGUI.class.getResource("/Imagenes/iconoRefachero.png")));
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

				// Esto muestra información sobre la aplicación y los creadores.
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Información frameInfo = new Información();
							frameInfo.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}
		});
		menuBar.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem = new JMenuItem("Ayuda");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Al pulsar esto se muestra la descripción básica de la aplicación y su uso.
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Ayuda frameAyuda = new Ayuda();
							frameAyuda.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}
		});
		mntmNewMenuItem.setHorizontalAlignment(SwingConstants.RIGHT);
		menuBar.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setForeground(Color.BLACK);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(ClienteGUI.class.getResource("/Imagenes/LogobienV2.png")));
		lblNewLabel_1.setBounds(503, 37, 216, 102);
		contentPane.add(lblNewLabel_1);

		TextField tfPolinomio = new TextField();
		tfPolinomio.setBounds(35, 200, 684, 22);
		contentPane.add(tfPolinomio);
		
		JButton btnInfo = new JButton("Info.");
		btnInfo.setEnabled(false);
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Con esto los atributos que ha recogido los manda a otra pestaña y los muestra detalladamente.
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							VentanaInfoRaícesExtra frameInfo = new VentanaInfoRaícesExtra(tfPolinomio.getText(), derivada, descartes, cotas,  sturm, cambiosSigno, numeroRaices);
							frameInfo.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}
		});
		btnInfo.setBounds(603, 360, 116, 22);
		contentPane.add(btnInfo);
		TextField tfMensajes = new TextField();
		tfMensajes.setEditable(false);
		tfMensajes.setForeground(Color.RED);
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

				// Con esto recoge lo del text y manda una petición al Servidor.
				// Después, recibe las raíces. Además recibe la información adicional, y la guarda en atributos.
				// Si no está activado el botón con el que se accede a la información, lo activa.

				listRaices.removeAll();

				polinomioStr = tfPolinomio.getText();

				if (!TrataPolinomiosUtil.comprobarEstructura(polinomioStr)) {

					tfMensajes.setText("Estructura del polinomio inválida.");
					btnInfo.setEnabled(false);

				} else {

					polinomio = TrataPolinomiosUtil.construirPolinomio(polinomioStr);

					// AHORA MANDAMOS EL MENSAJE AL SERVIDOR

					ObjectOutputStream out = null;
					DataInputStream in = null;

					try {

						Socket cliente = new Socket(tfServerIP.getText(), 48500);

						out = new ObjectOutputStream(cliente.getOutputStream());
						out.writeObject(polinomio);
						out.flush();

						in = new DataInputStream(cliente.getInputStream());
						String cadena;

						//AQUÍ VOY A IR GUARDANDO LA INFO
						
						cadena = in.readLine();
						derivada = cadena;
						
//						System.out.println(derivada);
						
						cadena = in.readLine();
						descartes = cadena;
						
						cadena = in.readLine();
						cotas = cadena;
						
						cadena = in.readLine();
						sturm = cadena;
						
						cadena = in.readLine();
						cambiosSigno = cadena;
						
						cadena = in.readLine();
						numeroRaices = cadena;
						
						if(isValidInt(numeroRaices)) {
							btnInfo.setEnabled(true);
							
							tfMensajes.setText("");
							
							while ((cadena = in.readLine()) != null) {
								
								listRaices.add(cadena);

							}
						}
						else {
							btnInfo.setEnabled(false);
							tfMensajes.setText(numeroRaices);
						}

					} catch (IOException e) {
						
						//Esto pinta la excepción. Si no queremos que aparezca el mensaje personalizado, entonces lo descomentamos
//						e.printStackTrace();
						
						tfMensajes.setText("Error al conectar con el servidor.");
					}

				}

			}
		});
		btnCalcularRacies.setBounds(577, 228, 142, 22);
		contentPane.add(btnCalcularRacies);

		Label label_2 = new Label("M\u00E1s info.");
		label_2.setFont(new Font("Arial Black", Font.BOLD, 17));
		label_2.setBounds(603, 312, 116, 22);
		contentPane.add(label_2);

		Label label_3 = new Label("de las ra\u00EDces:");
		label_3.setFont(new Font("Arial Black", Font.BOLD, 17));
		label_3.setBounds(603, 332, 116, 22);
		contentPane.add(label_3);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(35, 263, 684, 8);
		contentPane.add(separator);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(ClienteGUI.class.getResource("/Imagenes/Bannerv2.png")));
		lblNewLabel.setBounds(35, 31, 405, 115);
		contentPane.add(lblNewLabel);
		
		Label label_2_1 = new Label("Servidor (IP):");
		label_2_1.setFont(new Font("Arial Black", Font.BOLD, 17));
		label_2_1.setBounds(603, 392, 116, 22);
		contentPane.add(label_2_1);
		
		tfServerIP = new JTextField();
		tfServerIP.setText("localhost");
		tfServerIP.setBounds(603, 420, 116, 22);
		contentPane.add(tfServerIP);
		tfServerIP.setColumns(10);
	}
	
//private static boolean comprobarEstructura(String polStr) {
//		
//		if(polStr == null || polStr.equals("")) return false;
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
//		boolean isValid = true;
//
//		try {
//			Double.parseDouble(s);
//		} catch (NumberFormatException nfe) {
//			isValid = false;
//		}
//
//		return isValid;
//	}

	private static boolean isValidInt(String s) {
		boolean isValid = true;

		try {
			Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			isValid = false;
		}

		return isValid;
	}
}
