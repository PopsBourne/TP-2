package eventos;

import simulador.Camino;
import simulador.Carretera;
import simulador.Cruce;
	

	public class EventoNuevoCamino extends EventoNuevaCarretera {
	 protected String type;
		
	public EventoNuevoCamino(int tiempo, String id, String origen, String destino, int velocidadMaxima, int longitud, String type) {
		super(tiempo, id, origen, destino, velocidadMaxima, longitud);
		this.type = type;
	}

	protected Carretera creaCarretera(Cruce cruceOrigen, Cruce cruceDestino) {
		return new Camino(this.id, this.longitud, this.velocidadMaxima, cruceOrigen, cruceDestino);
	}
}