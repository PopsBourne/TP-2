package eventos;

import java.util.List;

import excepciones.NoExiste;
import simulador.Coche;
import simulador.Cruce;
import simulador.Vehiculo;

public class EventoNuevoCoche extends EventoNuevoVehiculo {
	private int duracionMaximaInfraccion;
	private long semilla;
	private int resistencia;
	private double probabilidad;


	public EventoNuevoCoche(int tiempo, String id, int velocidadMaxima, String[] itinerario, int resistencia,
			int duracionMaximaInfraccion, long semilla, double probabilidad) {
		super(tiempo, id, velocidadMaxima, itinerario);
		this.duracionMaximaInfraccion = duracionMaximaInfraccion;
		this.semilla = semilla;
		this.resistencia = resistencia;
		this.probabilidad = probabilidad;
		}
	
	@Override
	protected Vehiculo creaVehiculo(String id, int velocidadMaxima, List<Cruce> iti) throws NoExiste {
		return new Coche( id, velocidadMaxima, resistencia, probabilidad , semilla,
				duracionMaximaInfraccion,  iti);
	}
}