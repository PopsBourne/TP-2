package eventos;

import java.util.ArrayList;
import java.util.List;

import excepciones.NoExiste;
//import simulador.Cruce;
import simulador.MapaCarreteras;
import simulador.cruces.CruceGenerico;

public class ParserCarreteras {

	public static List<CruceGenerico<?>> parseaListaCruces(String[] itinerario, MapaCarreteras mapa) throws NoExiste {
		String s = "";
		CruceGenerico<?> cruce;
		List<CruceGenerico<?>> listaCruces = new ArrayList<CruceGenerico<?>>();
		for(int i = 0; i < itinerario.length; i++){
			s = itinerario[i];
			cruce = mapa.getCruce(s);
			listaCruces.add(cruce);
		}
		return listaCruces;
	}
}