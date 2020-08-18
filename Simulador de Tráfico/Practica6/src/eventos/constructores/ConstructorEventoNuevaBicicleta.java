package eventos.constructores;

import codigo.es.ucm.fdi.ini.IniSection;
import eventos.tiposeventos.Evento;
import eventos.tiposeventos.EventoNuevaBicicleta;

public class ConstructorEventoNuevaBicicleta extends ConstructorEventoNuevoVehiculo {

	@Override
	public Evento parser(IniSection section) {
		 if (!section.getTag().equals(this.etiqueta) ||
				 !section.getValue("type").equals("bike")) return null;
				 else
				 return new EventoNuevaBicicleta(
				 // extrae el valor del campo “time” en la sección
				 // 0 es el valor por defecto en caso de no especificar el tiempo
				 ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				 // extrae el valor del campo “id” de la sección
				 ConstructorEventos.identificadorValido(section, "id"),
				 ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0),
				 ConstructorEventos.idsValido(section, "itinerary")
				 );
	
	}
	@Override       
    public String toString() {   
		return "Nueva Bicicleta";                         
	}
}
