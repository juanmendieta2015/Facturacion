import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ventana2 extends javax.swing.JFrame {

	private JFrame frmVentana2;

	/**
	 * Launch the application.
	 */
	public static void NewScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana2 window = new Ventana2();
					window.frmVentana2.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Ventana2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmVentana2 = new JFrame();
		frmVentana2.setTitle("Ventana2");
		frmVentana2.setBounds(100, 100, 450, 300);
		frmVentana2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmVentana2.getContentPane().setLayout(null);
		
		JButton btnBoton = new JButton("boton2");
		btnBoton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ventana1 v1 = new Ventana1();
				//v1.main();
			}
		});
		btnBoton.setBounds(123, 86, 89, 23);
		frmVentana2.getContentPane().add(btnBoton);
	}

}
