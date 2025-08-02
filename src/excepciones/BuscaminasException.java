package excepciones;

//Excepción base para errores del Buscaminas
public class BuscaminasException extends Exception {
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public BuscaminasException(String mensaje) {
     super(mensaje);
 
 }
 public BuscaminasException(String mensaje, Throwable causa) {
     super(mensaje, causa);
 }
}
