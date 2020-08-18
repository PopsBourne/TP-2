package eventos;

import excepciones.Existe;
import simulador.Cruce;
import simulador.MapaCarreteras;

public class EventoNuevoCruce extends Evento {
	protected String id;

	public EventoNuevoCruce(int time, String id) {
		super(time);
		this.id = id;
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws Existe { // crea el cruce y se lo añade al mapa
		Cruce cruce = new Cruce(id);
		mapa.addCruce(cruce.getId(), cruce);
	}

	 
	/*public String toString() {
		return null;
	}*/
}