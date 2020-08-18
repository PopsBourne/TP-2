package eventos.constructores;

import codigo.es.ucm.fdi.ini.IniSection;
import eventos.tiposeventos.Evento;
import eventos.tiposeventos.EventoNuevoCruceCongestionado;

public class ConstructorEventoNuevoCruceCongestionado extends ConstructorEventos {

	public ConstructorEventoNuevoCruceCongestionado() {
		super();
		this.etiqueta = "new_junction";
		this.claves = new String[] { "time", "id" };
		//this.valoresPorDefecto = new String[] { "", "", "cm", "", "" };
	}

	@Override
	public Evento parser(IniSection section) {
		 if (!section.getTag().equals(this.etiqueta) ||
				 !section.getValue("type").equals("mc")) return null;
			 else
				 return new EventoNuevoCruceCongestionado(
				 // extrae el valor del campo “time” en la sección
				 // 0 es el valor por defecto en caso de no especificar el tiempo
				 ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				 // extrae el valor del campo “id” de la sección
				 ConstructorEventos.identificadorValido(section, "id"));
	}
	
	public String toString() { 
		return "Nuevo Cruce Congestionado";
	}
}
