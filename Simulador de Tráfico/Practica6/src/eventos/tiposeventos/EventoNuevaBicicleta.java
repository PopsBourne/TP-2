package eventos.tiposeventos;

import java.util.List;

import excepciones.NoExiste;
//import simulador.Cruce;
import simulador.cruces.CruceGenerico;
import simulador.vehiculos.Bicicleta;
import simulador.vehiculos.Vehiculo;

public class EventoNuevaBicicleta extends EventoNuevoVehiculo {

	public EventoNuevaBicicleta(int tiempo, String id, int velocidadMaxima, String[] itinerario) {
		super(tiempo, id, velocidadMaxima, itinerario);
	}

	
	@Override
	protected Vehiculo creaVehiculo(String id, int velocidadMaxima, List<CruceGenerico<?>> iti) throws NoExiste {
		return new Bicicleta(this.id, this.velocidadMaxima, iti);
	}
}
