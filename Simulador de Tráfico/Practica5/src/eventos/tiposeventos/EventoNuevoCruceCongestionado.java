package eventos.tiposeventos;

import excepciones.Existe;
//import simulador.Cruce;
import simulador.MapaCarreteras;
import simulador.cruces.CruceCongestionado;
import simulador.cruces.CruceGenerico;

public class EventoNuevoCruceCongestionado extends EventoNuevoCruce  {

	 public EventoNuevoCruceCongestionado(int time, String id) {
		super(time, id);
	}

	@Override 
	 protected CruceGenerico<?> creaCruce() {
		 return new CruceCongestionado(this.id);
	 }
	
	public void ejecuta(MapaCarreteras mapa) throws Existe { // crea el cruce y se lo añade al mapa
		CruceCongestionado cruceCongestionado = new CruceCongestionado(id);
		mapa.addCruce(cruceCongestionado.getId(), cruceCongestionado);
	}
}
