package eventos;

import codigo.es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCamino extends ConstructorEventos {

	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) ||
				 !section.getValue("type").equals("dirt")) return null;
				 else
				 return new EventoNuevoCamino(
				 // extrae el valor del campo “time” en la sección
				 // 0 es el valor por defecto en caso de no especificar el tiempo
				 ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				 // extrae el valor del campo “id” de la sección
				 ConstructorEventos.identificadorValido(section, "id"),
				 ConstructorEventos.identificadorValido(section, "src"),
				 ConstructorEventos.identificadorValido(section, "dest"),
				 ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0),
				 ConstructorEventos.parseaIntNoNegativo(section, "length", 0),
				 "dirt");
	}

}
