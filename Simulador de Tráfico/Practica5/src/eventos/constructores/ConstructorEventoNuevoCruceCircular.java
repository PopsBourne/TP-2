package eventos.constructores;

import codigo.es.ucm.fdi.ini.IniSection;
import eventos.tiposeventos.Evento;
import eventos.tiposeventos.EventoNuevoCruceCircular;

public class ConstructorEventoNuevoCruceCircular extends ConstructorEventos {

	public ConstructorEventoNuevoCruceCircular() {
		super();
		this.etiqueta = "new_junction";
		this.claves = new String[] { "time", "id" };
		//this.valoresPorDefecto = new String[] { "", "", "rr", "", "" };
	}

	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("rr"))
			return null;
		else
			return new EventoNuevoCruceCircular(
					// extrae el valor del campo “time” en la sección
					// 0 es el valor por defecto en caso de no especificar el tiempo
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					// extrae el valor del campo “id” de la sección
					ConstructorEventos.identificadorValido(section, "id"),
					ConstructorEventos.parseaIntNoNegativo(section, "min_time_slice", 0),
					ConstructorEventos.parseaIntNoNegativo(section, "max_time_slice", 0));
	}

	@Override       
    public String toString() {   
		return "Nuevo Cruce Circular";                         
	}
}
