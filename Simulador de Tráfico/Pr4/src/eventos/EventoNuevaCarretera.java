package eventos;

import excepciones.Existe;
import excepciones.NoExiste;
import simulador.Carretera;
import simulador.Cruce;
import simulador.MapaCarreteras;

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
		Cruce cruceO = mapa.getCruce(cruceOrigenId);
		Cruce cruceD = mapa.getCruce(cruceDestinoId);
		// crea la carretera
		Carretera carretera =  creaCarretera(cruceO, cruceD);
		//new Carretera(id, longitud, velocidadMaxima, cruceO, cruceD);
		// añade al mapa la carretera
		mapa.addCarretera(id, cruceO, carretera, cruceD);
	}
	
	protected Carretera creaCarretera(Cruce cruceOrigen, Cruce cruceDestino) {
			 return new Carretera(this.id, this.longitud, this.velocidadMaxima, cruceOrigen, cruceDestino);
	}

	
	/*public String toString() {
		//return this;
	}*/
}