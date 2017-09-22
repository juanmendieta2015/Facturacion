/*
 * Formulario para agregar un nuevo cliente
 * Autor: Juan mendieta
 * Fecha de Modificacion: 21/09/2017
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;

public class NuevoCliente extends JFrame implements ChangeListener{

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
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NuevoCliente frame = new NuevoCliente();
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
	// Constructor
	public NuevoCliente() {
			
		setTitle("Nuevo Cliente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombres (*):");
		lblNombre.setBounds(35, 26, 76, 14);
		contentPane.add(lblNombre);
		
		JLabel lblNewLabel = new JLabel("Apellidos (*):");
		lblNewLabel.setBounds(35, 63, 76, 14);
		contentPane.add(lblNewLabel);

		JLabel lblCdula = new JLabel("C\u00E9dula (*):");
		lblCdula.setBounds(35, 103, 76, 14);
		contentPane.add(lblCdula);
		
		JLabel lblSexo = new JLabel("Sexo (*):");
		lblSexo.setBounds(35, 140, 70, 14);
		contentPane.add(lblSexo);
		
		JLabel lblTelfono = new JLabel("Tel\u00E9fono:");
		lblTelfono.setBounds(35, 179, 70, 14);
		contentPane.add(lblTelfono);
		
		JLabel lblDireccin = new JLabel("Direcci\u00F3n:");
		lblDireccin.setBounds(35, 234, 76, 14);
		contentPane.add(lblDireccin);
		
		// Nombres
		txtNombres = new JTextField();
		txtNombres.setBounds(142, 26, 120, 20);
		contentPane.add(txtNombres);
		txtNombres.setColumns(10);
		
		// Apellidos
		txtApellidos = new JTextField();
		txtApellidos.setBounds(142, 63, 120, 20);
		contentPane.add(txtApellidos);
		txtApellidos.setColumns(10);
		
		// Cedula
		txtCedula = new JTextField();
		txtCedula.setBounds(142, 103, 86, 20);
		contentPane.add(txtCedula);
		txtCedula.setColumns(5);
			
		// Sexo
		bgSexo = new ButtonGroup();
		rbMasculino = new JRadioButton("Masculino");
		rbMasculino.setBounds(142, 139, 109, 23);
		rbMasculino.addChangeListener(this);		// para detectar los cambios de estado del radio button
		NuevoCliente.this.getContentPane().add(rbMasculino);
		rbFemenino = new JRadioButton("Femenino");
		rbFemenino.setBounds(284, 139, 109, 23);
		rbFemenino.addChangeListener(this);		// para detectar los cambios de estado del radio button
		NuevoCliente.this.getContentPane().add(rbFemenino);
		bgSexo.add(rbMasculino);
		bgSexo.add(rbFemenino);		
				
		// Telefono
		txtTelefono = new JTextField();
		txtTelefono.setBounds(142, 179, 86, 20);
		contentPane.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		// Direccion
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(142, 231, 217, 76);
		contentPane.add(scrollPane);		
		txtDireccion = new JTextArea();
		scrollPane.setViewportView(txtDireccion);
		
		// Boton Guardar		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				CrearCliente();
			}
		});		
		
		btnGuardar.setBounds(142, 332, 89, 23);
		contentPane.add(btnGuardar);
		
		// Boton Cancelar
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NuevoCliente.this.setVisible(false);
				Clientev2 clientev2 = new Clientev2();
				clientev2.setVisible(true);
				NuevoCliente.this.setVisible(false);				
			}
		});
		btnCancelar.setBounds(241, 332, 89, 23);
		contentPane.add(btnCancelar);
	}
	
	
	public void Limpiar() {		
		txtNombres.setText("");		 
		txtApellidos.setText("");
		txtCedula.setText("");
		bgSexo.clearSelection();
		txtTelefono.setText("");
		txtDireccion.setText("");	
		txtNombres.requestFocus();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}	
	
	public void CrearCliente() {

		Map<String, String> cliente = new HashMap<String, String>();

		/* Se agregan campos que seran validados (obligatorios), los otros (opcionales)
		 * se los agrega mas abajo, esto es para saltar su validacion en 
		 * "Validar.ClienteCrear()", es decir los opcionales
		 * no estan siendo validados, en esta version.
		 * */
		
		// Campos Obligatorios
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
		
		if(!Validar.ClienteCrear(cliente)) {
			System.out.println("Los datos ingresados son invalidos");
			return;
		}

		// Campos Opcionales				
		cliente.put("telefono", txtTelefono.getText());
		cliente.put("direccion", txtDireccion.getText());

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
			System.out.println("Cliente creado.");
			conection.close();
			Limpiar();	
			JOptionPane.showMessageDialog(null, Mensaje.SAVE_OK);
			
		} catch (SQLException l) {
			l.printStackTrace();
		}		
	}
}
