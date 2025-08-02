package excepciones;

public class CoordenadaInvalidaException extends BuscaminasException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CoordenadaInvalidaException(String mensaje) {
        super(mensaje);
    }
    public CoordenadaInvalidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}