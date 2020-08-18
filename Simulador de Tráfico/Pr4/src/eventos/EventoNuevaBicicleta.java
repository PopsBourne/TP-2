package eventos;

import java.util.List;

import excepciones.NoExiste;
import simulador.Bicicleta;
import simulador.Cruce;
import simulador.Vehiculo;

public class EventoNuevaBicicleta extends EventoNuevoVehiculo {

	public EventoNuevaBicicleta(int tiempo, String id, int velocidadMaxima, String[] itinerario) {
		super(tiempo, id, velocidadMaxima, itinerario);
	}

	
	@Override
	protected Vehiculo creaVehiculo(String id, int velocidadMaxima, List<Cruce> iti) throws NoExiste {
		return new Bicicleta(this.id, this.velocidadMaxima, iti);
	}
}
