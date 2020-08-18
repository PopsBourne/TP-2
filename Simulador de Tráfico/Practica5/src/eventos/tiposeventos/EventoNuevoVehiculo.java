package eventos.tiposeventos;

import java.util.List;

import eventos.ParserCarreteras;
import excepciones.NoExiste;
import excepciones.ParseException;
import excepciones.Existe;
//import simulador.Cruce;
import simulador.MapaCarreteras;
import simulador.cruces.CruceGenerico;
import simulador.vehiculos.Vehiculo;

public class EventoNuevoVehiculo extends Evento {
	protected String id;
	protected Integer velocidadMaxima;
	protected String[] itinerario;

	public EventoNuevoVehiculo(int tiempo, String id, int velocidadMaxima, String[] itinerario) {
		super(tiempo);
		this.id = id;
		this.velocidadMaxima = velocidadMaxima;
		this.itinerario = itinerario;
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ParseException, NoExiste, Existe {
		List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(this.itinerario, mapa);
		if(iti == null || iti.size() < 2)
			throw new ParseException("Este itinerario tiene menos de dos cruces o es null");
		Vehiculo vehiculo = creaVehiculo(id, velocidadMaxima, iti);
		mapa.addVehiculo(vehiculo.getId(), vehiculo);
		// si iti es null o tiene menos de dos cruces lanzar excepción
		// en otro caso crear el vehículo y añadirlo al mapa.
	}

	protected Vehiculo creaVehiculo(String id, int velocidadMaxima, List<CruceGenerico<?>> iti) throws NoExiste {
			 return new Vehiculo(id, velocidadMaxima, iti);
	}
	
	@Override
	public String toString() {
		return "New Vehicle " + id;
	}
	 //this.itinerario = [“j1”,”j2”,”j3”] entonces parseaListaCruces devuelve la
	 //lista de cruces cuyos identifcadores son los de “j1”, “j2” y “j3”. Para ello
	 //utiliza el mapa de carreteras.
}