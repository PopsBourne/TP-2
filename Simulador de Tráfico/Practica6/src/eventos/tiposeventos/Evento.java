package eventos.tiposeventos;

import excepciones.NoExiste;
import excepciones.ParseException;
import excepciones.Existe;
import simulador.MapaCarreteras;

public abstract class Evento {

	protected Integer tiempo;
	
	public Evento(int tiempo) {
		 this.tiempo = tiempo;
	 }
	 
	public int getTiempo() {
		return tiempo; 
	 }
	
	// cada clase que hereda implementa su método ejecuta, que creará
	// el correspondiente objeto de la simulación.
	public abstract void ejecuta(MapaCarreteras mapa) throws ParseException, NoExiste, Existe;
}