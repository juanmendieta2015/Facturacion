import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Formulario1 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Formulario1 frame = new Formulario1();
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
	public Formulario1() {
		setTitle("Formulario1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnIrAFormulario = new JButton("Ir a Formulario2");
		btnIrAFormulario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Formulario2 f2 = new Formulario2();
				f2.setVisible(true);
				Formulario1.this.setVisible(false);
			}
		});
		btnIrAFormulario.setBounds(137, 108, 178, 23);
		contentPane.add(btnIrAFormulario);
	}

}
