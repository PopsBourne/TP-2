package eventos.tiposeventos;

import excepciones.Existe;
import simulador.MapaCarreteras;
import simulador.cruces.CruceCircular;
import simulador.cruces.CruceGenerico;

public class EventoNuevoCruceCircular extends EventoNuevoCruce  {

	 protected Integer maxValorIntervalo; 
	 protected Integer minValorIntervalo; 
	 
	 public EventoNuevoCruceCircular(int time, String id, int minValorIntervalo, int maxValorIntervalo) { 
		 super(time, id); 
		 this.maxValorIntervalo = maxValorIntervalo;
		 this.minValorIntervalo = minValorIntervalo;
		 }

	 @Override  
	 protected CruceGenerico<?> creaCruce() {
		 return new CruceCircular(this.id, this.minValorIntervalo, this.maxValorIntervalo);
		 }
	 
	 public void ejecuta(MapaCarreteras mapa) throws Existe { // crea el cruce y se lo añade al mapa
			CruceCircular cruceCir = new CruceCircular(id, minValorIntervalo, maxValorIntervalo);
			mapa.addCruce(cruceCir.getId(), cruceCir);
		}

}
