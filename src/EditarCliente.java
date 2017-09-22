import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;

public class EditarCliente extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombres;
	private JTextField txtApellidos;
	private JTextField txtCedula;
	private JTextField txtTelefono;
	private JTextArea txtDireccion;
	private ButtonGroup bgSexo;
	private JRadioButton rbMasculino, rbFemenino;

	/**
	 * Launch the application.
	 */
	public static void main(int args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditarCliente frame = new EditarCliente(args);
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
	public EditarCliente(int clienteId) {
		setTitle("Editar Cliente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombres = new JLabel("Nombres (*):");
		lblNombres.setBounds(67, 33, 76, 14);
		contentPane.add(lblNombres);
		
		JLabel lblApellidos = new JLabel("Apellidos (*):");
		lblApellidos.setBounds(67, 64, 76, 14);
		contentPane.add(lblApellidos);
		
		JLabel lblCdua = new JLabel("C\u00E9dula (*):");
		lblCdua.setBounds(67, 95, 76, 14);
		contentPane.add(lblCdua);
		
		JLabel lblSexo = new JLabel("Sexo (*):");
		lblSexo.setBounds(67, 123, 76, 14);
		contentPane.add(lblSexo);
		
		JLabel lblTelefono = new JLabel("Tel\u00E9fono:");
		lblTelefono.setBounds(67, 152, 76, 14);
		contentPane.add(lblTelefono);
		
		JLabel lblDireccin = new JLabel("Direcci\u00F3n:");
		lblDireccin.setBounds(67, 196, 76, 14);
		contentPane.add(lblDireccin);		
		
		// Nombres
		txtNombres = new JTextField();
		txtNombres.setBounds(176, 30, 120, 20);
		contentPane.add(txtNombres);
		txtNombres.setColumns(10);
		
		// Apellidos
		txtApellidos = new JTextField();
		txtApellidos.setBounds(176, 61, 120, 20);
		contentPane.add(txtApellidos);
		txtApellidos.setColumns(10);
		
		// Cedula
		txtCedula = new JTextField();
		txtCedula.setBounds(176, 92, 86, 20);
		contentPane.add(txtCedula);
		txtCedula.setColumns(10);
		
		// Sexo
		bgSexo = new ButtonGroup();
		rbMasculino = new JRadioButton("Masculino");
		rbMasculino.setBounds(173, 119, 109, 23);
		contentPane.add(rbMasculino);
		rbFemenino = new JRadioButton("Femenino");
		rbFemenino.setBounds(284, 119, 109, 23);
		contentPane.add(rbFemenino);
		bgSexo.add(rbMasculino);
		bgSexo.add(rbFemenino);
		
		// Telefono
		txtTelefono = new JTextField();
		txtTelefono.setBounds(176, 149, 86, 20);
		contentPane.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		// Direccion
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(176, 190, 209, 89);
		contentPane.add(scrollPane);
		txtDireccion = new JTextArea();
		scrollPane.setViewportView(txtDireccion);
		
		// Boton Guardar	
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(ActualizarCliente(clienteId)) {
					EditarCliente.this.setVisible(false);
					Cliente Cliente = new Cliente();
					Cliente.setVisible(true);				
				}		
				
			}
		});	
		btnGuardar.setBounds(176, 316, 89, 23);
		contentPane.add(btnGuardar);
		
		// Boton Cancelar			
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				EditarCliente.this.setVisible(false);
				Cliente Cliente = new Cliente();
				Cliente.setVisible(true);
			}
		});
		btnCancelar.setBounds(284, 316, 89, 23);
		contentPane.add(btnCancelar);		
		BuscarCliente(clienteId);
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

	//Verificacion si el clienteId existe en la base de datos
	public void BuscarCliente(int clienteId) {		
		try {					
			Connection conection = ConexionBD.Conectar();
			String sql = 					
					"SELECT  \r\n" + 
					"cliente_id\r\n" + 
					", cedula\r\n" + 
					", nombre\r\n" + 
					", apellido\r\n" +
					", sexo\r\n" +
					", telefono\r\n" + 
					", direccion\r\n" + 
					", fecha_creacion\r\n" + 
					", fecha_modificacion\r\n" + 
					", creado_por\r\n" + 
					", modificado_por\r\n" + 
					"FROM cliente\r\n" + 
					"WHERE estado = '1'\r\n" + 
					"AND cliente_id = ?\r\n" ;
			PreparedStatement preparedStatement = conection.prepareStatement(sql);
			preparedStatement.setInt(1, clienteId);
			ResultSet resultSet = preparedStatement.executeQuery();	
			
			if(resultSet.next() == true) {
				txtNombres.setText(resultSet.getString("nombre"));
				txtApellidos.setText(resultSet.getString("apellido"));
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
				JOptionPane.showMessageDialog(null, Mensaje.NOT_FOUND, "Nuevo Cliente", JOptionPane.WARNING_MESSAGE);
			}			
			conection.close();							
		} catch (SQLException l) {
			l.printStackTrace();
		}				
	}	
	
	// Guarda los nuevos cambios
	public boolean ActualizarCliente(int clienteId) {
		Map<String, String> cliente = new HashMap<String, String>();
		//int client_exists = 0;

		/* Se agregan campos que seran validados (obligatorios) los otros (opcionales)
		 * se los agrega mas abajo, para saltar su validacion en 
		 * "Validar.ClienteActualizar()", es decir los opcionales
		 * no estan siendo validados, en esta version.
		 * */
		
		// Campos Obligatorios, que seran validados
		cliente.put("clienteId", String.valueOf(clienteId));
		cliente.put("nombre", txtNombres.getText());
		cliente.put("apellido", txtApellidos.getText());				
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
			return false;
		}	
		
		// Verificar si el Cliente Id existe en la base de datos
		if(!ClienteExiste(Integer.parseInt(cliente.get("clienteId")))){
			return false;
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
			System.out.println("Cliente actualizado!");					
			conection.close();
			JOptionPane.showMessageDialog(null, Mensaje.SAVE_OK);
			return true;
		} catch (SQLException l) {
			l.printStackTrace();
			return false;
		}				
	}
	
	
}
