package eventos;

import codigo.es.ucm.fdi.ini.IniSection;

public class ConstructorEventoAveriaVehiculo extends ConstructorEventos {
	String[] vehicles;
	protected int time;
	protected int duracion;
	
	
	
	public ConstructorEventoAveriaVehiculo() {
		super();
		this.etiqueta = "make_vehicle_faulty";
		this.claves = new String[] {"vehicles", "time", "duracion" };
	}



	@Override
	public Evento parser(IniSection section) {
		 if (!section.getTag().equals(this.etiqueta) ||
				 section.getValue("type") != null) return null;
				 else
				 return new EventoAveriaVehiculo(
				 // extrae el valor del campo “time” en la sección
				 // 0 es el valor por defecto en caso de no especificar el tiempo
				 ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
				 ConstructorEventos.idsValido(section, "vehicles"),
				 ConstructorEventos.parseaIntNoNegativo(section, "duration", 0));
	}
	
	public String toString(){
		return "Make vehicle faulty";
	}
}
