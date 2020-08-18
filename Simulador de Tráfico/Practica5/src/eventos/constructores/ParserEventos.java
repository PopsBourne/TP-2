package eventos.constructores;

import codigo.es.ucm.fdi.ini.IniSection;
import eventos.tiposeventos.Evento;

public class ParserEventos {

	private static ConstructorEventos[] constructoresEventos = {
			new ConstructorEventoNuevoCruce(),
			new ConstructorEventoNuevaCarretera(),
			new ConstructorEventoNuevoVehiculo(),
			new ConstructorEventoAveriaVehiculo(),
			new ConstructorEventoNuevoCoche(),
			new ConstructorEventoNuevaBicicleta(),
			new ConstructorEventoNuevaAutopista(),
			new ConstructorEventoNuevoCamino(),
			new ConstructorEventoNuevoCruceCircular(),
			new ConstructorEventoNuevoCruceCongestionado(),
	};

	// bucle de prueba y error
	public static Evento parseaEvento(IniSection sec) {
		int i = 0;
		boolean seguir = true;
		Evento e = null;
		while (i < ParserEventos.constructoresEventos.length && seguir) {
			// ConstructorEventos contiene el método parse(sec)
			e = ParserEventos.constructoresEventos[i].parser(sec);
			if (e != null) seguir = false;
			else i++;
		}
		return e;
	}

	public static ConstructorEventos[] getConstructoresEventos() {
		return constructoresEventos;
	}

}