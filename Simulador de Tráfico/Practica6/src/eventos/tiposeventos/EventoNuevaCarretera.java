package eventos.tiposeventos;

import excepciones.Existe;
import excepciones.NoExiste;
//import simulador.Cruce;
import simulador.MapaCarreteras;
import simulador.carreteras.Carretera;
import simulador.cruces.CruceGenerico;

public class EventoNuevaCarretera extends Evento {
	protected String id;
	protected Integer velocidadMaxima;
	protected Integer longitud;
	protected String cruceOrigenId;
	protected String cruceDestinoId;

	public EventoNuevaCarretera(int tiempo, String id, String origen, String destino,
								int velocidadMaxima, int longitud) {
		super(tiempo);
		this.id = id;
		this.velocidadMaxima = velocidadMaxima;
		this.longitud = longitud;
		this.cruceOrigenId = origen;
		this.cruceDestinoId = destino;
	}

	public void ejecuta(MapaCarreteras mapa) throws NoExiste, Existe {
		// obten cruce origen y cruce destino utilizando el mapa
		CruceGenerico<?> cruceO = mapa.getCruce(cruceOrigenId);
		CruceGenerico<?> cruceD = mapa.getCruce(cruceDestinoId);
		// crea la carretera
		Carretera carretera =  creaCarretera(cruceO, cruceD);
		//new Carretera(id, longitud, velocidadMaxima, cruceO, cruceD);
		// añade al mapa la carretera
		mapa.addCarretera(this.id, cruceO, carretera, cruceD);
	}
	
	protected Carretera creaCarretera(CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino) {
			 return new Carretera(this.id, this.longitud, this.velocidadMaxima, cruceOrigen, cruceDestino);
	}

	
	public String toString() {
		return "New Road";
	}
}