package eventos;

import java.util.List;

import excepciones.NoExiste;
import excepciones.ParseException;
import excepciones.Existe;
import simulador.Cruce;
import simulador.MapaCarreteras;
import simulador.Vehiculo;

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
		List<Cruce> iti = ParserCarreteras.parseaListaCruces(this.itinerario, mapa);
		if(iti == null || iti.size() < 2)
			throw new ParseException("Este itinerario tiene menos de dos cruces o es null");
		Vehiculo vehiculo = creaVehiculo(id, velocidadMaxima, iti);
		/*Cruce c1 = iti.get(0);
		Cruce c2 = iti.get(1);
		Carretera c = c1.carreteraHaciaCruce(c2);
		vehiculo.setCarretera(c);*/
		mapa.addVehiculo(vehiculo.getId(), vehiculo);
		// si iti es null o tiene menos de dos cruces lanzar excepción
		// en otro caso crear el vehículo y añadirlo al mapa.
	}

	protected Vehiculo creaVehiculo(String id, int velocidadMaxima, List<Cruce> iti) throws NoExiste {
			 return new Vehiculo(id, velocidadMaxima, iti);
	}
	
	@Override
	public String toString() {
		return id;
	}
	 //this.itinerario = [“j1”,”j2”,”j3”] entonces parseaListaCruces devuelve la
	 //lista de cruces cuyos identifcadores son los de “j1”, “j2” y “j3”. Para ello
	 //utiliza el mapa de carreteras.
}