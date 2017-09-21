import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ventana1 extends javax.swing.JFrame{

	private JFrame frmVentana1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana1 window = new Ventana1();
					window.frmVentana1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Ventana1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmVentana1 = new JFrame();
		frmVentana1.setTitle("Ventana1");
		frmVentana1.setBounds(100, 100, 450, 300);
		frmVentana1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frmVentana1.getContentPane().setLayout(null);
		
		JButton btnBoton = new JButton("boton1");
		btnBoton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ventana2 ventana2 = new Ventana2();
				ventana2.NewScreen();
				//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				
				
				//frmVentana1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				//Ventana1.this.disp
				//ventana2.setVisible(true);
				//this.setVisible(false);
			}
		});
		btnBoton.setBounds(141, 105, 89, 23);
		frmVentana1.getContentPane().add(btnBoton);
	}
}
