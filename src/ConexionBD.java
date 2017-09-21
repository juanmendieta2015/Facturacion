/*
 * Clase para setear los valores de conexion con la base de datos
 * Autor: Juan Mendieta
 * Fecha de modificacion: 11/09/2017
 * */

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {

	public static Connection Conectar() {
		Connection conexion = null;
		String driver = "oracle.jdbc.OracleDriver";
	    String url = "jdbc:oracle:thin:@localhost:1521/XE";
	    String usuario = "facturacion";
	    String clave = "Sara2017!";
	    
	    try {
	    	Class.forName(driver);
	    	conexion = DriverManager.getConnection(url, usuario, clave);
	    	if(conexion == null) {
	    		System.out.println("Conexion no establecida");
	    		
	    	}
	    	return conexion;
	    	
	    } catch (Exception e) {
	    	System.out.println(e);
	    }
	    return null;
	 }

}

