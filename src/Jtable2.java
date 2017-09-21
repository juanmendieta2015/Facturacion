/* Busca en la base de datos y muestra el resultado de dicha consulta
 * en un jtable */

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
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.ScrollPane;
import javax.swing.JTextArea;

public class Jtable2 {

	private JFrame frame;
	private JTable table;
	private JTextField txtBuscar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Jtable2 window = new Jtable2();
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
	public Jtable2() {
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
		
		// Scroll Bar, Botones Editar y Eliminar
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				int column = table.getColumnModel().getColumnIndex(arg0.getX());
				int row = arg0.getY()/table.getRowHeight();
				
					if(row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
						Object value = table.getValueAt(row, column);
						
						if(value instanceof JButton) {
							((JButton)value).doClick();
							JButton boton = (JButton) value;
						}
						
					}
				
			}
		});
		scrollPane.setBounds(97, 144, 434, 402);
		frame.getContentPane().add(scrollPane);
		
		
		// Tabla
		table = new JTable();
		scrollPane.setViewportView(table);
		
		// Textbox Buscar		
		txtBuscar = new JTextField();
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				//buscar();
			}
			@Override
			public void keyReleased(KeyEvent e) {
				buscar();
			}
		});
		txtBuscar.setBounds(401, 99, 130, 20);
		frame.getContentPane().add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		// Boton Nuevo
		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cliente cliente = new Cliente();
				cliente.setVisible(true);
		
				
				
			}
		});
		btnNuevo.setBounds(97, 96, 89, 23);
		frame.getContentPane().add(btnNuevo);
		
			
	}
	
	private void mostrar()
	{
		DefaultTableModel modelo = new DefaultTableModel() 
		/*{
			public boolean isCellEditabe(int row, int column)
			{
				return false;
			}
		}*/
		;
		modelo.setColumnIdentifiers(new Object[] {"nombre", "apellido", "telefono", "editar", "eliminar"});
		
		table.setDefaultRenderer(Object.class, new Render());
		
		// Botones
		JButton btn1 = new JButton("Editar");
		btn1.setName("editar");
		JButton btn2 = new JButton("Eliminar");
		btn1.setName("eliminar");		
		
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
				modelo.addRow(new Object[] {resultSet.getString("nombre"), resultSet.getString("apellido"), resultSet.getString("telefono"), btn1, btn2});
			}
			//modelo.isCellEditable(row, column)
			table.setModel(modelo);
			table.setRowHeight(30);
			

			conection.close();
			
	
							
		} catch (SQLException l) {
			l.printStackTrace();
		}		
	}
	
	private void buscar()
	{
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(new Object[] {"nombre", "apellido", "telefono", "editar", "eliminar"});
		
		table.setDefaultRenderer(Object.class, new Render());
		
		// Botones
		JButton btn1 = new JButton("Editar");
		btn1.setName("editar");
		JButton btn2 = new JButton("Eliminar");
		btn1.setName("eliminar");
		
		
		try {					
			Connection conection = ConexionBD.Conectar();
			String sql = 
					"SELECT\r\n" + 
					"      nombre\r\n" + 
					"    , apellido\r\n" + 
					"    , telefono\r\n" + 
					"\r\n" + 
					"FROM cliente\r\n" + 
					"WHERE estado = '1'\r\n" + 
					"AND (\r\n" + 
					"    UPPER(nombre) LIKE UPPER('%" +  txtBuscar.getText() + "%')\r\n" + 
					"    OR UPPER(apellido) LIKE UPPER('%" +  txtBuscar.getText() + "%')\r\n" + 
					"    OR telefono LIKE '%" +  txtBuscar.getText() + "%'\r\n" + 
					")";

					
			PreparedStatement preparedStatement = conection.prepareStatement(sql);
		
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				modelo.addRow(new Object[] {resultSet.getString("nombre"), resultSet.getString("apellido"), resultSet.getString("telefono"), btn1, btn2});
			}
			table.setModel(modelo);

			conection.close();
			
	
							
		} catch (SQLException l) {
			l.printStackTrace();
		}		
		
		
		
		
	}
}
