package eventos;

import excepciones.NoExiste;
import excepciones.ParseException;
import excepciones.Existe;
import simulador.MapaCarreteras;

public abstract class Evento {

	protected Integer tiempo;
	
	public Evento(int tiempo) {
		 this.tiempo = tiempo;
	 }
	 
	public int getTiempo() { // no devolveria integer?
		return tiempo; 
	 }
	
	// cada clase que hereda implementa su m�todo ejecuta, que crear�
	// el correspondiente objeto de la simulaci�n.
	public abstract void ejecuta(MapaCarreteras mapa) throws ParseException, NoExiste, Existe;
}