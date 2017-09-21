/*Ejercicio en el cual se inserta los botones de Editar y Eliminar en cada uno de los registros del Jtable*/

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Tabla {
	public void ver_tabla(JTable tabla) 
	{
		// Instanciamos el renderizado de la clase Render
		tabla.setDefaultRenderer(Object.class, new Render());
		
		JButton btn1 = new JButton("Modificar");
		btn1.setName("m");
		JButton btn2 = new JButton("Eliminar");
		btn2.setName("e");		
		
		DefaultTableModel d = new DefaultTableModel
		(
				new Object[][] {{"1","Pedro",btn1, btn2},{"2","Rosa",btn1, btn2},{"3","Juan",btn1, btn2}}
				, new Object[] {"Codigo","Nombre","M","E"}
		)
		{
			// Bloquea la edicion del JTable
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		}
		;
		tabla.setModel(d);
		tabla.setRowHeight(30);		//tamaño de los botones
	}

}
