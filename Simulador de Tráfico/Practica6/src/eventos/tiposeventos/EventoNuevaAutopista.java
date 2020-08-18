package eventos.tiposeventos;

//import simulador.Cruce;
import simulador.carreteras.Autopista;
import simulador.carreteras.Carretera;
import simulador.cruces.CruceGenerico;

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

	 protected Carretera creaCarretera(CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino) {
		return new Autopista(numCarriles, id, longitud, velocidadMaxima, cruceOrigen, cruceDestino);
	}
}