/*
 * Acceso a la base de datos de Cliente
 * Autor: Juan Mendieta
 * Fecha de Modificacion: 22/09/2017
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ClienteModel {
	
	public Map<String, String> getJTable() {
		String nombre = null;
		Map<String, String> cliente = new HashMap<String, String>();
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
			PreparedStatement preparedStatement = conection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				//modelo.addRow(new Object[] {resultSet.getString("cliente_id"), resultSet.getString("nombre"), resultSet.getString("apellido"), resultSet.getString("telefono"), resultSet.getString("direccion"), btn1, btn2});
				//nombre = resultSet.getString("nombre");
				
				//cliente.put("nombre", resultSet.getString("nombre"));
				//cliente.put("apellido", resultSet.getString("apellido"));
				
				//cliente.put(key, value)
				
//		        Map<String,String> row = new HashMap<String, String>(columns.size());
//		        for(String col : columns) {
//		            row.put(col, rs.getString(col));
//		        }
//		        data.add(row);
				
			}
			//table.setModel(modelo);
			//table.setRowHeight(30);
			conection.close();	
			//return true;
			//return nombre;
		} catch (SQLException l) {
			l.printStackTrace();
		}
		return cliente;	
	}
	
}
