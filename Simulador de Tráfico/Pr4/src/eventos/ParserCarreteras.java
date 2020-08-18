package eventos;

import java.util.ArrayList;
import java.util.List;

import excepciones.NoExiste;
import simulador.Cruce;
import simulador.MapaCarreteras;

public class ParserCarreteras {

	public static List<Cruce> parseaListaCruces(String[] itinerario, MapaCarreteras mapa) throws NoExiste {
		String s = "";
		Cruce cruce;
		List<Cruce> listaCruces = new ArrayList<Cruce>();
		for(int i = 0; i < itinerario.length; i++){
			s = itinerario[i];
			cruce = mapa.getCruce(s);
			listaCruces.add(cruce);
		}
		return listaCruces;
	}
}