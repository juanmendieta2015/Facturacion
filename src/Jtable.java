/* Muestra un jtable con datos provenientes de una base de datos */

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class Jtable {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Jtable window = new Jtable();
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
	public Jtable() {
		initialize();
		mostrar();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		frame.setBounds(100, 100, 450, 300);
		frame.setSize(700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(89, 35, 434, 312);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
			
	}
	
	private void mostrar()
	{
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(new Object[] {"nombre", "apellido", "telefono"});
		
		try {					
			Connection conection = ConexionBD.Conectar();
			String sql =
					"SELECT nombre, apellido, telefono FROM CLIENTE WHERE estado = 1"; 
					//"WHERE cliente_id = ?\r\n" + 
					//"AND estado = 1";	
			PreparedStatement preparedStatement = conection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				//clienteExiste = resultSet.getInt("existe");
				modelo.addRow(new Object[] {resultSet.getString("nombre"), resultSet.getString("apellido"), resultSet.getString("telefono")});
			}
			table.setModel(modelo);

			conection.close();
			
	
							
		} catch (SQLException l) {
			l.printStackTrace();
		}		

			
		//JScrollPane JS = new JScrollPane(table);	
		//JS.setPreferredSize(new Dimension(400,150));
		//frame.add(JS);
		
		
		
		
	}
}
