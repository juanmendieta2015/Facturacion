import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class test {

	private JFrame frame;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public test() {
		//JTextArea textArea = new JTextArea();
		//textArea.setBounds(42, 54, 102, 62);
		//frame.getContentPane().add(textArea);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(43, 23, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textArea = new JTextArea();
		textArea.setBounds(42, 54, 102, 62);
		frame.getContentPane().add(textArea);
		
		//System.out.println("se construyo");
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//textField.setText("");
				//textArea.setText("");
				Limpiar();
			}
		});
		
		btnNewButton.setBounds(216, 22, 89, 23);
		frame.getContentPane().add(btnNewButton);
	}
	
	private JTextField textField;
	private JTextArea textArea;
	//JTextArea textArea = new JTextArea();
	//JTextField textField = new JTextField();
	
	public void Limpiar() {
		//JTextArea textArea = new JTextArea();
		//JTextField textField = new JTextField();
		
		//System.out.println("limpiar funcion");
		textArea.setText("");
		textField.setText("");
	}	

}
