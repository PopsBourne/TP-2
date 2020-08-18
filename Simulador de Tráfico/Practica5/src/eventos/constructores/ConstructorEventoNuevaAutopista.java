package eventos.constructores;

import codigo.es.ucm.fdi.ini.IniSection;
import eventos.tiposeventos.Evento;
import eventos.tiposeventos.EventoNuevaAutopista;

public class ConstructorEventoNuevaAutopista extends ConstructorEventoNuevaCarretera {

	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) ||
				 !section.getValue("type").equals("lanes")) return null;
				 else
				 return new EventoNuevaAutopista(
				 // extrae el valor del campo “time” en la sección
				 // 0 es el valor por defecto en caso de no especificar el tiempo
				 ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				 // extrae el valor del campo “id” de la sección
				 ConstructorEventos.identificadorValido(section, "id"),
				 ConstructorEventos.identificadorValido(section, "src"),
				 ConstructorEventos.identificadorValido(section, "dest"),
				 ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0),
				 ConstructorEventos.parseaIntNoNegativo(section, "length", 0),
				 "lanes",
				 ConstructorEventos.parseaIntNoNegativo(section, "lanes", 0));
	}

	@Override       
    public String toString() {   
		return "Nueva Autopista";                         
	}
}
