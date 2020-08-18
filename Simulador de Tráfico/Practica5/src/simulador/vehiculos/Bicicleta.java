package simulador.vehiculos;

import java.util.List;

import simulador.cruces.CruceGenerico;
import codigo.es.ucm.fdi.ini.IniSection;
import excepciones.NoExiste;

public class Bicicleta extends Vehiculo  {

	public Bicicleta(String id, int velocidadMaxima, List<CruceGenerico<?>> iti) throws NoExiste {
		super(id, velocidadMaxima, iti);
	}
	public void setTiempoAveria(Integer duracionAveria) {
		// Comprobar que “carretera” no es null
		if (carretera != null && this.velocidadActual >= this.velocidadMaxima / 2) {
		// Se fija el tiempo de avería de acuerdo con el enunciado
			this.tiempoAveria = duracionAveria; 
		// Si el tiempo de avería es finalmente positivo, entonces la “velocidadActual” se pone a 0
		if (this.tiempoAveria > 0)
			this.velocidadActual = 0;
		}
	}
	
	@Override
	public void completaDetallesSeccion(IniSection is) {
		is.setValue("type", "bike");
		is.setValue("speed", this.velocidadActual);
		is.setValue("kilometrage", this.kilometraje);
		is.setValue("faulty", this.tiempoAveria);
		is.setValue("location", this.haLlegado ? "arrived" : "("+this.carretera + ","
				+ this.getLocalizacion()+")");
	}
}