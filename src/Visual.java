/*Para controlar el evento clic de los botones en la tabla
 * clic derecho en la tabla, mouse y mouseClicked*/
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Visual {

	private JFrame frame;
	private JTable tabla;
	Tabla t = new Tabla();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Visual window = new Visual();
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
	public Visual() {
		initialize();
		t.ver_tabla(tabla);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		tabla = new JTable();
		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int column = tabla.getColumnModel().getColumnIndexAtX(arg0.getX());
				int row = arg0.getY()/tabla.getRowHeight();
				
				if(row < tabla.getRowCount() && row >= 0 && column < tabla.getColumnCount() && column >= 0) {
					Object value = tabla.getValueAt(row, column);
					if(value instanceof JButton) {
						((JButton)value).doClick();
						//JButton boton = (JButton) value;

						/*
						if (boton.getName().equals("m")) {
							System.out.println("Clic en el boton modificar");
						}
						
						if (boton.getName().equals("e")) {
							System.out.println("Clic en el boton eliminar");
						}
						*/
					}
				}
				
			}
		});
		tabla.setBounds(40, 60, 330, 158);
		frame.getContentPane().add(tabla);
	}
}
