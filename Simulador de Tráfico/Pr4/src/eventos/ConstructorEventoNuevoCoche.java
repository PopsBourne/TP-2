package eventos;

import codigo.es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCoche extends ConstructorEventoNuevoVehiculo {

	@Override
	public Evento parser(IniSection section) {
		 if (!section.getTag().equals(this.etiqueta) ||
				 !section.getValue("type").equals("car")) return null;
				 else
				 return new EventoNuevoCoche(
				 // extrae el valor del campo “time” en la sección
				 // 0 es el valor por defecto en caso de no especificar el tiempo
				 ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				 // extrae el valor del campo “id” de la sección
				 ConstructorEventos.identificadorValido(section, "id"),
				 ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0),
				 ConstructorEventos.idsValido(section, "itinerary"),
				 ConstructorEventos.parseaIntNoNegativo(section, "resistance", 0),
				 ConstructorEventos.parseaIntNoNegativo(section, "max_fault_duration", 0),
				 ConstructorEventos.parseaLong(section, "fault_probability", 0),
				 ConstructorEventos.parseaDouble(section, "seed", System.currentTimeMillis()));
	}

}
