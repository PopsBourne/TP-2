package excepciones;

public class NoExiste extends Exception {
	private static final long serialVersionUID = 1L;

	public NoExiste (String message){
		super(message);
	}
}