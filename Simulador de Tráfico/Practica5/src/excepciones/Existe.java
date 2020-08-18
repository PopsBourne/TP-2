package excepciones;

public class Existe extends Exception {
	private static final long serialVersionUID = 1L;

	public Existe (String message){
		super(message);
	}
}