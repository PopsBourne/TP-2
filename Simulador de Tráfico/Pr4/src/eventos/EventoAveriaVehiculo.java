package eventos;

import java.util.ArrayList;
import java.util.List;

import excepciones.NoExiste;
import simulador.MapaCarreteras;
import simulador.Vehiculo;

public class EventoAveriaVehiculo extends Evento {
	protected String[] vehiculos;
	protected Integer duracion;
	
	public EventoAveriaVehiculo(int tiempo, String[] vehiculos, Integer duracion) {
		super(tiempo);
		this.vehiculos = vehiculos;
		this.duracion = duracion;
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws NoExiste {
		String s = "";
		Vehiculo vehiculo;
		List<Vehiculo> listaVehiculos = new ArrayList<Vehiculo>();
		for(int i = 0; i < vehiculos.length; i++){
			s = vehiculos[i];
			vehiculo = mapa.getVehiculo(s);
			listaVehiculos.add(vehiculo);
			vehiculo.setTiempoAveria(duracion);
		}
	}
	
	
 /*	public String toString() {
		return null;
	}*/
}