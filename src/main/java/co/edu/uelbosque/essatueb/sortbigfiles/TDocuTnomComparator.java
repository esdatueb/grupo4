/*
 * Apache 2.0
 * Universidad El Bosque  * 
 */
package co.edu.uelbosque.essatueb.sortbigfiles;

import java.util.Comparator;

/**
 *
 * @author fernemod
 */
public class TDocuTnomComparator {
    
    public static Comparator<Object> PersonaTipoDocu
		    = new Comparator<Object>() {
    	@Override
		public int compare(Object p1, Object p2) {

		String tipo1 =  ((Persona) p1).getDocumento();
		String tipo2 =  ((Persona) p2).getTipoDoc();
                int i = tipo1.compareTo(tipo2);
                if(i != 0){
                    return i;
                }
		String nombre1 =  ((Persona) p1).getNombre();
		String nombre2 =  ((Persona) p2).getNombre();
		
		return nombre1.compareTo(nombre2);	
		}	
		};
    
    
}
