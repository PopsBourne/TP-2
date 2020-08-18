package eventos.tiposeventos;

//import simulador.Cruce;
import simulador.carreteras.Camino;
import simulador.carreteras.Carretera;
import simulador.cruces.CruceGenerico;
	
public class EventoNuevoCamino extends EventoNuevaCarretera {
	protected String type;
		
	public EventoNuevoCamino(int tiempo, String id, String origen, String destino, int velocidadMaxima, int longitud, String type) {
		super(tiempo, id, origen, destino, velocidadMaxima, longitud);
		this.type = type;
	}

	protected Carretera creaCarretera(CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino) {
		return new Camino(this.id, this.longitud, this.velocidadMaxima, cruceOrigen, cruceDestino);
	}
}