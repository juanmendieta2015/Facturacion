/*
 * Clase para realizar validaciones de todos los objetos
 * Autor:  Juan Mendieta
 * Fecha de Modificacion: 11/09/2017 
 * */

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class Validar {


	/* Valida los campos para agregar nuevo cliente
	 * IN: Array asociativo con los datos del ciente
	 * OUT: True si todo los campos son correctos, False en caso contrario
	 */	
	public static boolean ClienteCrear(Map args) {
	
		if (existenCamposVacios(args) == true) {
			JOptionPane.showMessageDialog(null, Mensaje.EMPTY_FIELD, "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("campos vacios");
			return false;
		} 
		
		if (soloLetras(args.get("nombre").toString()) == false) {
			JOptionPane.showMessageDialog(null, Mensaje.INVALID_FIRSTNAME, "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("nombre invalido");
			return false;
		} 	
		
		if (soloLetras(args.get("apellido").toString()) == false) {
			JOptionPane.showMessageDialog(null, Mensaje.INVALID_LASTNAME, "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("apellido invalido");
			return false;
		} 	
		
		if (soloNumeros(args.get("cedula").toString()) == false) {
			JOptionPane.showMessageDialog(null, Mensaje.INVALID_CEDULA, "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("cedula invalida");
			return false;
		}	
		
		if (sexo(args.get("sexo").toString()) == false) {
			JOptionPane.showMessageDialog(null, Mensaje.INVALID_SEX, "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("sexo invalido");
			return false;
		} 			
		return true;
	}
	
	public static boolean ClienteBuscar(String clienteId) {
		if (soloNumeros(clienteId) == false) {
			JOptionPane.showMessageDialog(null, Mensaje.INVALID_ID, "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}	
		return true;
	}

	public static boolean ClienteActualizar(Map args) {
		
		if (existenCamposVacios(args) == true) {
			JOptionPane.showMessageDialog(null, Mensaje.EMPTY_FIELD, "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} 

		if (soloNumeros(args.get("clienteId").toString()) == false) {
			JOptionPane.showMessageDialog(null, Mensaje.INVALID_ID, "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} 	
		
		if (soloLetras(args.get("nombre").toString()) == false) {
			JOptionPane.showMessageDialog(null, Mensaje.INVALID_FIRSTNAME, "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} 	
		
		if (soloLetras(args.get("apellido").toString()) == false) {
			JOptionPane.showMessageDialog(null, Mensaje.INVALID_LASTNAME, "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} 	
		
		if (soloNumeros(args.get("cedula").toString()) == false) {
			JOptionPane.showMessageDialog(null, Mensaje.INVALID_CEDULA, "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}	
		
		if (sexo(args.get("sexo").toString()) == false) {
			JOptionPane.showMessageDialog(null, Mensaje.INVALID_SEX, "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} 	
		
		
		return true;
	}

	
	public static boolean ClienteEliminar(Map args) {

		if (soloNumeros(args.get("clienteId").toString()) == false) {
			JOptionPane.showMessageDialog(null, Mensaje.INVALID_ID, "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} 	
		
		
		return true;
	}
	
	
	
	/*
	 * Valida si existen campos vacios al ingresar un nuevo cliente
	 * IN: Array asociativo (Objeto Map) con los datos del cliente
	 * OUT: True si existen campos vacios, False si no existen campos vacios
	 * */	
	public static boolean existenCamposVacios(Map args) {
		Set set = args.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry)iterator.next();
			if(mentry.getValue().equals("")) {
				return true;
			}
		}
		return false;
	}
	
	
	
	/* Valida que un texto solo contenga letras o espacio en blanco.
	 * Retorna True si solo contiene letras o espacio en blanco y false en caso contrario
	 */
	public static boolean soloLetras(String value) {
		Pattern patron = Pattern.compile("[^A-Za-z ]");
		Matcher encaja = patron.matcher(value);
		if(!encaja.find())
			return true;		// Solo contiene letras o espacio en blanco
		else
			return false;		// Contiene otros caracteres
	}
	
	
	
	/* Valida que un texto solo contenga numeros.
	 * Retorna True si solo contiene numeros y false en caso contrario
	 */
	public static boolean soloNumeros(String value) {
		if (value.matches("[0-9]*"))
			return true;
		 else 
			return false;
	}
	
	

	/* Valida que un texto solo contenga numeros.
	 * Retorna True si solo contiene numeros y false en caso contrario
	 */	
	public static boolean sexo(String value) {
		if (value.matches("[m|M]asculino|[f|F]emenino")) {
			return true;
		}
		 else { 
			return false;
		 }
	}
	
	
}
