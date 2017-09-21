import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Formulario2 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Formulario2 frame = new Formulario2();
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
	public Formulario2() {
		setTitle("Formulario2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnIrAFormulario = new JButton("Ir a Formulario 1");
		btnIrAFormulario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Formulario1 f1 = new Formulario1();
				f1.setVisible(true);
				Formulario2.this.setVisible(false);				
				
			}
		});
		btnIrAFormulario.setBounds(134, 105, 196, 23);
		contentPane.add(btnIrAFormulario);
	}

}
