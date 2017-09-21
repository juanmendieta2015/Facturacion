import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.ScrollPane;
import javax.swing.JTable;
import java.awt.TextArea;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Clientev2 extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField txtBuscar;
	//private JButton boton;
	private JButton btn1, btn2;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientev2 frame = new Clientev2();
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
	public Clientev2() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 718, 539);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Boton Nuevo Cliente		
		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NuevoCliente nuevoCliente = new NuevoCliente();
				nuevoCliente.setVisible(true);
				Clientev2.this.setVisible(false);
			}
		});
		btnNuevo.setBounds(51, 69, 89, 23);
		contentPane.add(btnNuevo);
		
		// Scroll Bar	
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(51, 114, 599, 332);
		contentPane.add(scrollPane);
		
		
		// Tabla
		table = new JTable() {
	        private static final long serialVersionUID = 1L;
	        public boolean isCellEditable(int row, int column) {                
                return false;               
	        }		
		}
		;
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
//				int filaSeleccionada = table.getSelectedRow();	
				Object clienteId;
				clienteId =  model.getValueAt(table.getSelectedRow(), 0);
				System.out.println(clienteId.toString());
//				System.out.println(model.getValueAt(selectedRowIndex,0));
				
				

		        int column = table.getColumnModel().getColumnIndexAtX(arg0.getX());
		        int row = arg0.getY()/table.getRowHeight();
		        
		        if(row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0){
		            Object value = table.getValueAt(row, column);
		            if(value instanceof JButton){
		                ((JButton)value).doClick();
		               JButton boton = (JButton) value;
		                //boton =    

		                if(boton.getName().equals("editar")){
		                    System.out.println("Click en el boton editar");
		                    EditarCliente editarCliente = new EditarCliente(Integer.parseInt(clienteId.toString()));
		                    editarCliente.setVisible(true);
		                    Clientev2.this.setVisible(false);
		                    

		                }
		                if(boton.getName().equals("eliminar")){
//		                    JOptionPane.showConfirmDialog(null, "Desea eliminar este registro", "Confirmar", JOptionPane.OK_CANCEL_OPTION);
		                    System.out.println("Click en el boton eliminar");
		                    Clientev2.this.Eliminar(Integer.parseInt(clienteId.toString()));
//		                    Mostrar();
		                    Buscar();
		                }
		            }

		        }				
				
				
				
			}
		});
		scrollPane.setViewportView(table);
		
		// Caja de texto Buscar		
		txtBuscar = new JTextField();
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				Buscar();
			}
		});
		txtBuscar.setBounds(403, 70, 247, 20);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		btn1 = new JButton("editar");
		btn1.setName("editar");
		btn2 = new JButton("eliminar");
		btn2.setName("eliminar");	
		
		
		// Mostrar Datos
		Mostrar();

		
	}
	

	
	
	private void Mostrar() {
		DefaultTableModel modelo = new DefaultTableModel()
		
		/*{
			public boolean isCellEditabe(int row, int column)
			{
				return false;
			}
		}*/
		;
		modelo.setColumnIdentifiers(new Object[] {"Id", "Nombres", "Apellidos", "Teléfono","Dirección", "Editar", "Eliminar"});
		
		table.setDefaultRenderer(Object.class, new Render());
		
		// Botones
//		JButton btn1 = new JButton("editar");
//		btn1.setName("editar");
//		JButton btn2 = new JButton("eliminar");
//		btn2.setName("eliminar");		
		
		try {					
			Connection conection = ConexionBD.Conectar();
			String sql =
					"SELECT cliente_id"
					+ ", nombre"
					+ ", apellido"
					+ ", telefono"
					+ ", direccion "
					+ "FROM "
					+ "(\r\n" + 
					"    SELECT * \r\n" + 
					"    FROM cliente\r\n" + 
					"    ORDER BY cliente_id ASC \r\n" + 
					") "
					+ "WHERE estado = 1"; 
					//"WHERE cliente_id = ?\r\n" + 
					//"AND estado = 1";	
			PreparedStatement preparedStatement = conection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				//clienteExiste = resultSet.getInt("existe");
				modelo.addRow(new Object[] {resultSet.getString("cliente_id"), resultSet.getString("nombre"), resultSet.getString("apellido"), resultSet.getString("telefono"), resultSet.getString("direccion"), btn1, btn2});
			}
			//modelo.isCellEditable(row, column)
			table.setModel(modelo);
			table.setRowHeight(30);
			

			conection.close();
			
	
							
		} catch (SQLException l) {
			l.printStackTrace();
		}		
	}	// Fin Mostrar()
	
	
	
	
	private void Buscar() {
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(new Object[] {"Id", "Nombres", "Apellidos", "Teléfono","Dirección", "Editar", "Eliminar"});
		
		table.setDefaultRenderer(Object.class, new Render());
		
		// Botones
//		JButton btn1 = new JButton("Editar");
//		btn1.setName("editar");
//		JButton btn2 = new JButton("Eliminar");
//		btn1.setName("eliminar");
		
		
		try {					
			Connection conection = ConexionBD.Conectar();
			String sql = 
					"SELECT\r\n" +
					"      cliente_id\r\n" + 
					"    , nombre\r\n" + 
					"    , apellido\r\n" + 
					"    , telefono\r\n" +
					"    , direccion\r\n" + 
					"\r\n" + 
					"FROM \r\n"  +
					"(\r\n" + 
					"    SELECT * \r\n" + 
					"    FROM cliente\r\n" + 
					"    ORDER BY cliente_id ASC \r\n" + 
					") "+		
					"WHERE estado = '1'\r\n" + 
					"AND (\r\n" + 
					"    UPPER(cliente_id) LIKE UPPER('%" +  txtBuscar.getText() + "%')\r\n" +
					"    OR UPPER(nombre) LIKE UPPER('%" +  txtBuscar.getText() + "%')\r\n" + 
					"    OR UPPER(apellido) LIKE UPPER('%" +  txtBuscar.getText() + "%')\r\n" + 
					"    OR telefono LIKE '%" +  txtBuscar.getText() + "%'\r\n" + 
					"    OR UPPER(direccion) LIKE UPPER('%" +  txtBuscar.getText() + "%')\r\n" +
					")";

					
			PreparedStatement preparedStatement = conection.prepareStatement(sql);
		
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				modelo.addRow(new Object[] {resultSet.getString("cliente_id"), resultSet.getString("nombre"), resultSet.getString("apellido"), resultSet.getString("telefono"), resultSet.getString("direccion"), btn1, btn2});
			}
			table.setModel(modelo);

			conection.close();
			
	
							
		} catch (SQLException l) {
			l.printStackTrace();
		}		
		
		
		
		
	}	// Fin Buscar()	

	private void Eliminar(int clienteId) {

		Map<String, String> cliente = new HashMap<String, String>();
		int client_exists = 0;
		
		cliente.put("clienteId", String.valueOf(clienteId));
		
		// Verificacion si el Cliente Id es un valor numerico
		if(!Validar.ClienteEliminar(cliente)) {
			System.out.println("Los datos ingresados son invalidos");
			return;
		}					
		
		// Verificar si el Cliente Id existe en la base de datos
		if(!ClienteExiste(Integer.parseInt(cliente.get("clienteId")))){
			return;
		}
		
		// Confirmacion de eliminacion del cliente
		int n = JOptionPane.showConfirmDialog(null, "Realmente desea eliminar el cliente", "title",JOptionPane.YES_NO_OPTION);
		if (n == 1)	{ return; }
		
		
		// Eliminacion Lógica de la base de datos 
		try {
			Connection conection = ConexionBD.Conectar();
			//String clienteId	= txtClienteId.getText();
			
			String sql =
					"UPDATE  CLIENTE\r\n" + 
					"SET estado = 0\r\n" + 
					"    , fecha_eliminacion = current_date\r\n" + 
					"WHERE cliente_id = ?";	
			
			PreparedStatement preparedStatement = conection.prepareStatement(sql);
			preparedStatement.setString(1, String.valueOf(clienteId) );
			
			preparedStatement.executeUpdate();
			conection.close();
			//Limpiar();
			System.out.println("Cliente eliminado!");
			JOptionPane.showMessageDialog(null, "El cliente se ha eliminado satisfactoriamente.");
			
		} catch (SQLException l) {
			l.printStackTrace();
		}								
		
	}
		
		
	//Verificacion si el clienteId existe en la base de datos
	public boolean ClienteExiste(int clienteId) {
		int clienteExiste = 0;
		
		try {					
			Connection conection = ConexionBD.Conectar();
			String sql =
					"SELECT count(*) AS existe \r\n" + 
					"FROM CLIENTE\r\n" + 
					"WHERE cliente_id = ?\r\n" + 
					"AND estado = 1";	
			PreparedStatement preparedStatement = conection.prepareStatement(sql);
			preparedStatement.setInt(1, clienteId);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				clienteExiste = resultSet.getInt("existe");
			}
			
			if(clienteExiste == 0) {
				JOptionPane.showMessageDialog(null, "El Cliente Id no existe.", "Editar Cliente", JOptionPane.ERROR_MESSAGE);
				return false;
			} 				

			conection.close();
							
		} catch (SQLException l) {
			l.printStackTrace();
		}		
		
		return true;
		
	}	
		
		
		
		
	
	
}	// Fin Clientev2 
