/*
 * Ventana para administracion de Clientes
 * Autor: Juan Mendieta
 * Fecha de modificacion: 11/09/2017
 * */

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class Cliente extends JFrame implements ChangeListener {

	private JFrame Cliente;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtCedula;
	private JTextField txtTelefono;
	private JTextField txtClienteId;
	private JTextArea txtDireccion;
	private ButtonGroup bgSexo;
	private JRadioButton rbMasculino, rbFemenino;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente window = new Cliente();
					window.Cliente.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Cliente() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Cliente = new JFrame();
		Cliente.setTitle("Cliente");
		Cliente.setBounds(100, 100, 686, 572);
		//Cliente.setSize(700, 700);
		Cliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Cliente.getContentPane().setLayout(null);
		
		
		
		JLabel lblNewLabel = new JLabel("Nombre (*)");
		lblNewLabel.setBounds(30, 75, 73, 14);
		Cliente.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Apellido (*)");
		lblNewLabel_1.setBounds(30, 107, 73, 14);
		Cliente.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Cedula (*)");
		lblNewLabel_2.setBounds(30, 138, 56, 14);
		Cliente.getContentPane().add(lblNewLabel_2);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(143, 75, 150, 20);
		Cliente.getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(143, 107, 150, 20);
		Cliente.getContentPane().add(txtApellido);
		txtApellido.setColumns(10);
		
		txtCedula = new JTextField();
		txtCedula.setBounds(143, 138, 86, 20);
		Cliente.getContentPane().add(txtCedula);
		txtCedula.setColumns(10);
		
		/********************		Insert		********************/
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Map<String, String> cliente = new HashMap<String, String>();

				/* Se agregan campos que seran validados (obligatorios), los otros (opcionales)
				 * se los agrega mas abajo, esto es para saltar su validacion en 
				 * "Validar.ClienteCrear()", es decir los opcionales
				 * no estan siendo validados, en esta version.
				 * */
				
				// Campos Obligatorios
				cliente.put("nombre", txtNombre.getText());
				cliente.put("apellido", txtApellido.getText());				
				cliente.put("cedula", txtCedula.getText());
				cliente.put("sexo", "");

				if(rbMasculino.isSelected()) {
					cliente.put("sexo", "Masculino");
				} 
				if(rbFemenino.isSelected()) {
					cliente.put("sexo", "Femenino");
				}
				
				if(!Validar.ClienteCrear(cliente)) {
					System.out.println("Los datos ingresados son invalidos");
					return;
				}

				// Campos Opcionales				
				cliente.put("telefono", txtTelefono.getText());
				cliente.put("direccion", txtTelefono.getText());

				try {
					
					Connection conection = ConexionBD.Conectar();
					String sql =
							"INSERT into CLIENTE(cliente_id, cedula, nombre, apellido, sexo, telefono, direccion, estado, fecha_creacion, creado_por)\r\n" + 
							"VALUES (mi_secuencia.nextval, ?, ?, ?, UPPER(?), ?, ?, '1', current_date, '5')";	
					PreparedStatement preparedStatement = conection.prepareStatement(sql);
					
					preparedStatement.setString(1, cliente.get("cedula"));
					preparedStatement.setString(2, cliente.get("nombre"));
					preparedStatement.setString(3, cliente.get("apellido"));
					preparedStatement.setString(4, cliente.get("sexo"));
					preparedStatement.setString(5, cliente.get("telefono"));
					preparedStatement.setString(6, cliente.get("direccion"));
					preparedStatement.executeUpdate();
					conection.close();
					Limpiar();		
					System.out.println("Cliente creado!");
					JOptionPane.showMessageDialog(null, "El cliente ha sido creado satisfactoriamente.");
					
				} catch (SQLException l) {
					l.printStackTrace();
				}
				
			}	
			
		});	// Fin btnCrear.addActionListener
		
		btnCrear.setBounds(378, 80, 119, 23);
		Cliente.getContentPane().add(btnCrear);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(143, 200, 86, 20);
		Cliente.getContentPane().add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Sexo (*)");
		lblNewLabel_3.setBounds(30, 169, 73, 14);
		Cliente.getContentPane().add(lblNewLabel_3);
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(30, 200, 73, 14);
		Cliente.getContentPane().add(lblTelefono);
		
		JLabel lblDireccion = new JLabel("Direccion");
		lblDireccion.setBounds(30, 231, 73, 14);
		Cliente.getContentPane().add(lblDireccion);
		
		
		
		/********************		Select		********************/
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(!Validar.ClienteBuscar(txtClienteId.getText())) {
					return;
				}

				Limpiar();
				try {
					Connection conection = ConexionBD.Conectar();

					String sql = "SELECT\r\n" + 
							"    cliente_id\r\n" + 
							"    , cedula\r\n" + 
							"    , nombre\r\n" + 
							"    , apellido\r\n" + 
							"    , sexo\r\n" + 
							"    , telefono\r\n" + 
							"    , direccion\r\n" + 
							"    , fecha_creacion\r\n" + 
							"    , fecha_modificacion\r\n" + 
							"    , creado_por\r\n" + 
							"    , modificado_por\r\n" + 
							"FROM cliente\r\n" + 
							"WHERE estado = '1'\r\n" + 
							"AND cliente_id = ?";	
					
					PreparedStatement statement = conection.prepareStatement(sql);
					statement.setInt(1, Integer.parseInt(txtClienteId.getText()));
					ResultSet resultSet = statement.executeQuery();
					
					if(resultSet.next() == true) {
						txtNombre.setText(resultSet.getString("nombre"));
						txtApellido.setText(resultSet.getString("apellido"));
						txtCedula.setText(resultSet.getString("cedula"));

						if (resultSet.getString("sexo").equals("MASCULINO") ) {
							rbMasculino.setSelected(true);
						}
						
						if (resultSet.getString("sexo").equals("FEMENINO")) {
							rbFemenino.setSelected(true);
						}
						
						txtTelefono.setText(resultSet.getString("telefono"));
						txtDireccion.setText(resultSet.getString("direccion"));
					} else {
						JOptionPane.showMessageDialog(null, "No se encontró resultados", "Nuevo Cliente", JOptionPane.WARNING_MESSAGE);
					}
				
					conection.close();
									
				} catch (SQLException l) {
					l.printStackTrace();
				}				
				
				
			}
		});	// Fin btnBuscar.addActionListener
		
		btnBuscar.setBounds(378, 48, 119, 23);
		Cliente.getContentPane().add(btnBuscar);
		
		txtClienteId = new JTextField();
		txtClienteId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		txtClienteId.setBounds(143, 48, 86, 20);
		Cliente.getContentPane().add(txtClienteId);
		txtClienteId.setColumns(10);
		
		JLabel lblClienteId = new JLabel("Cliente Id");
		lblClienteId.setBounds(30, 51, 73, 14);
		Cliente.getContentPane().add(lblClienteId);
		
		
		
		/********************		Update		********************/
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Map<String, String> cliente = new HashMap<String, String>();
				int client_exists = 0;

				/* Se agregan campos que seran validados (obligatorios) los otros (opcionales)
				 * se los agrega mas abajo, para saltar su validacion en 
				 * "Validar.ClienteActualizar()", es decir los opcionales
				 * no estan siendo validados, en esta version.
				 * */
				
				// Campos Obligatorios, que seran validados
				cliente.put("clienteId", txtClienteId.getText());
				cliente.put("nombre", txtNombre.getText());
				cliente.put("apellido", txtApellido.getText());				
				cliente.put("cedula", txtCedula.getText());
				cliente.put("sexo", "");

				if(rbMasculino.isSelected()) {
					cliente.put("sexo", "Masculino");
				} 
				if(rbFemenino.isSelected()) {
					cliente.put("sexo", "Femenino");
				}				
				
				// Validar Datos del Cliente
				if(!Validar.ClienteActualizar(cliente)) {
					System.out.println("Los datos ingresados son invalidos");
					return;
				}	
				
				// Verificar si el Cliente Id existe en la base de datos
				if(!ClienteExiste(Integer.parseInt(cliente.get("clienteId")))){
					return;
				}				
				
				//Campos Opcionales, que no seran validados
				cliente.put("telefono", txtTelefono.getText());
				cliente.put("direccion", txtDireccion.getText());
				
				
				// Actualizacion de los datos del Cliente
				try {
					Connection conection = ConexionBD.Conectar();
					String sql =
							"UPDATE  CLIENTE\r\n" + 
							"SET cedula = ?\r\n" + 
							"    , nombre = ?" + 
							"    , apellido = ?\r\n" + 
							"    , sexo = UPPER(?)\r\n" + 
							"    , telefono = ?\r\n" + 
							"    , direccion = ?\r\n" + 
							"    , fecha_modificacion = current_date\r\n" + 
							"    , modificado_por = '5' \r\n" + 
							"WHERE estado = 1 AND cliente_id = ?";	
					
					PreparedStatement preparedStatement = conection.prepareStatement(sql);
					preparedStatement.setString(1, cliente.get("cedula"));
					preparedStatement.setString(2, cliente.get("nombre"));
					preparedStatement.setString(3, cliente.get("apellido"));
					preparedStatement.setString(4, cliente.get("sexo"));
					preparedStatement.setString(5, cliente.get("telefono"));
					preparedStatement.setString(6, cliente.get("direccion"));
					preparedStatement.setString(7, cliente.get("clienteId"));
					
					preparedStatement.executeUpdate();
					conection.close();

					System.out.println("Cliente actualizado!");
					JOptionPane.showMessageDialog(null, "Sus cambios han sido guardados satisfactoriamente.");
					
				} catch (SQLException l) {
					l.printStackTrace();
				}				
				
			}
		});	//Fin btnActualizar.addActionListener
		
		btnActualizar.setBounds(378, 115, 119, 23);
		Cliente.getContentPane().add(btnActualizar);
		
		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Limpiar();
				
			}
		});
		btnNuevo.setBounds(378, 149, 119, 23);
		Cliente.getContentPane().add(btnNuevo);
		
		
		
		/**********		Delete		**********/
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Map<String, String> cliente = new HashMap<String, String>();
				int client_exists = 0;
				
				cliente.put("clienteId", txtClienteId.getText());
				
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
					String clienteId	= txtClienteId.getText();
					
					String sql =
							"UPDATE  CLIENTE\r\n" + 
							"SET estado = 0\r\n" + 
							"    , fecha_eliminacion = current_date\r\n" + 
							"WHERE cliente_id = ?";	
					
					PreparedStatement preparedStatement = conection.prepareStatement(sql);
					preparedStatement.setString(1, clienteId);
					
					preparedStatement.executeUpdate();
					conection.close();
					Limpiar();
					System.out.println("Cliente eliminado!");
					JOptionPane.showMessageDialog(null, "El cliente se ha eliminado satisfactoriamente.");
					
				} catch (SQLException l) {
					l.printStackTrace();
				}								
				
			}
		});	//Fin btnEliminar.addActionListener
		
		btnEliminar.setBounds(378, 183, 119, 23);
		Cliente.getContentPane().add(btnEliminar);
		
		bgSexo = new ButtonGroup();
		
		rbMasculino = new JRadioButton("Masculino");
		rbMasculino.setBounds(143, 165, 86, 23);
		rbMasculino.addChangeListener(this);		// para detectar los cambios de estado del option button para el sexo del cliente
		Cliente.getContentPane().add(rbMasculino);
		
		rbFemenino = new JRadioButton("Femenino");
		rbFemenino.setBounds(236, 165, 86, 23);
		rbFemenino.addChangeListener(this);		// para detectar los cambios de estado del option button para ele sexo del cliente
		Cliente.getContentPane().add(rbFemenino);
		
		
		bgSexo.add(rbMasculino);
		bgSexo.add(rbFemenino);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(143, 239, 208, 60);
		Cliente.getContentPane().add(scrollPane);
		
		txtDireccion = new JTextArea();
		scrollPane.setViewportView(txtDireccion);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Limpiar();
			}
		});
		btnNewButton.setBounds(517, 241, 89, 23);
		Cliente.getContentPane().add(btnNewButton);

	}		//Fin Inicialize()
	
	
	public void Limpiar() {
		
		txtNombre.setText("");		 
		txtNombre.setText("");
		txtApellido.setText("");
		txtCedula.setText("");
		bgSexo.clearSelection();
		txtTelefono.setText("");
		txtDireccion.setText("");	
		txtNombre.requestFocus();
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		/*if(rbMasculino.isSelected()) {
			System.out.println("Masculino");
		}
		if(rbFemenino.isSelected()) {
			System.out.println("Femenino");
		}*/		
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
}
