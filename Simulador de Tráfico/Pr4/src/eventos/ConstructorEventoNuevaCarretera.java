package eventos;

import codigo.es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevaCarretera extends ConstructorEventos {

	protected String id;
	protected Integer velocidadMaxima;
	protected Integer longitud;
	protected String cruceOrigenId;
	protected String cruceDestinoId;

	public ConstructorEventoNuevaCarretera() {
		super();
		this.etiqueta="new_road";
		this.claves= new String [] {"time","id","src","dest","max_speed","length"};
	}

	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) ||
				 section.getValue("type") != null) return null;
				 else
				 return new EventoNuevaCarretera(
				 // extrae el valor del campo “time” en la sección
				 // 0 es el valor por defecto en caso de no especificar el tiempo
				 ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				 // extrae el valor del campo “id” de la sección
				 ConstructorEventos.identificadorValido(section, "id"),
				 ConstructorEventos.identificadorValido(section, "src"),
				 ConstructorEventos.identificadorValido(section, "dest"),
				 ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0),
				 ConstructorEventos.parseaIntNoNegativo(section, "length", 0));
	}

	public String toString(){
		return "New road";
	}
}
