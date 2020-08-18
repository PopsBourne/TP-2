package eventos;

import simulador.Autopista;
import simulador.Carretera;
import simulador.Cruce;

public class EventoNuevaAutopista extends EventoNuevaCarretera {
	
	protected Integer numCarriles;
	protected String type;

	public EventoNuevaAutopista(int tiempo, String id, String origen, String destino,
			int velocidadMaxima, int longitud, String type, int numCarriles){
		super(tiempo, id, origen, destino,
				velocidadMaxima, longitud);
		this.numCarriles = numCarriles;
		this.type = type;
	}

	 protected Carretera creaCarretera(Cruce cruceOrigen, Cruce cruceDestino) {
		return new Autopista(numCarriles, id, longitud, velocidadMaxima, cruceOrigen, cruceDestino);
	}
}