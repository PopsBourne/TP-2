package eventos.tiposeventos;

import excepciones.Existe;
import simulador.MapaCarreteras;
import simulador.cruces.Cruce;
import simulador.cruces.CruceGenerico;

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

	 protected CruceGenerico<?> creaCruce() { 
		 return new Cruce(this.id); 
		 }
	 
	public String toString() {
		return "New Junction";
	}
}