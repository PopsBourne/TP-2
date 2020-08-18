package simulador;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import simulador.carreteras.Carretera;
import simulador.cruces.CruceGenerico;
import simulador.vehiculos.Vehiculo;
import excepciones.NoExiste;
import excepciones.Existe;

public class MapaCarreteras {

	private List<Carretera> carreteras;
	private List<CruceGenerico<?>> cruces;
	private List<Vehiculo> vehiculos;
	// estructuras para agilizar la búsqueda (id,valor)
	private Map<String, Carretera> mapaDeCarreteras;
	private Map<String, CruceGenerico<?>> mapaDeCruces;
	private Map<String, Vehiculo> mapaDeVehiculos;

	public MapaCarreteras() {
		carreteras = new ArrayList<Carretera>();
		cruces = new ArrayList<CruceGenerico<?>>();
		vehiculos = new ArrayList<Vehiculo>();
		// inicializa los atributos a sus constructoras por defecto.
		// Para carreteras, cruces y vehículos puede usarse ArrayList.
		// Para los mapas puede usarse HashMap
		mapaDeCarreteras = new HashMap<String, Carretera>();
		mapaDeCruces = new HashMap<String, CruceGenerico<?>>();
		mapaDeVehiculos = new HashMap<String, Vehiculo>();
		
	}

	public void addCruce(String idCruce, CruceGenerico<?> cruce) throws Existe {
		if (!mapaDeCruces.containsKey(idCruce)) { // comprueba que “idCruce” no existe en el mapa.
			cruces.add(cruce);
			mapaDeCruces.put(idCruce, cruce);// Si no existe, lo añade a “cruces” y a “mapaDeCruces”.
		} else
			throw new Existe("Ese cruce ya existe");// Si existe lanza una excepción
	}
	// “Solo se ejecuta una vez por Cruce. Cuando se procesa su evento”.

	public void addVehiculo(String idVehiculo, Vehiculo vehiculo) throws Existe {
		// comprueba que “idVehiculo” no existe en el mapa.
		if (!mapaDeVehiculos.containsKey(idVehiculo)) {
			// Si no existe, lo añade a “vehiculos” y a “mapaDeVehiculos”,
			vehiculos.add(vehiculo);
			mapaDeVehiculos.put(idVehiculo, vehiculo);// Si no existe, lo añade a “vehiculos” y a “mapaDeVehiculos”,
			vehiculo.moverASiguienteCarretera();// y posteriormente solicita al
												// vehiculo que se mueva a la
												// siguiente carretera de su itinerario(moverASiguienteCarretera).
		}else
			throw new Existe("Ese vehiculo ya existe");// Si existe lanza una excepción.
	}

	// “Solo se ejecuta una vez por vehículo. Cuando se procesa el evento”.

	public void addCarretera(String idCarretera, CruceGenerico<?> origen, Carretera carretera, CruceGenerico<?> destino) throws Existe {
		// comprueba que “idCarretera” no existe en el mapa.
		// Si no existe, lo añade a “carreteras” y a “mapaDeCarreteras”,
		// y posteriormente actualiza los cruces origen y destino como sigue:
		if (!mapaDeCarreteras.containsKey(idCarretera)) {
			carreteras.add(carretera);
			mapaDeCarreteras.put(idCarretera, carretera);
			// - Añade al cruce origen la carretera,como “carretera saliente”
			origen.addCarreteraSalienteAlCruce(destino, carretera);
			// -Añade al cruce destino la carretera,como “carretera entrante”
			destino.addCarreteraEntranteAlCruce(idCarretera, carretera);//															
		}else 
			throw new Existe("Esa carretera ya existe");// Si existe lanza una excepción.
	}

	public String generateReport(int time) {
		String report = "";
		Iterator<CruceGenerico<?>> it = cruces.iterator();
		while(it.hasNext()){
			report += it.next().generaInforme(time);
			report += '\n';
		}
		Iterator<Carretera> it2 = carreteras.iterator();
		while(it2.hasNext()){
			report += it2.next().generaInforme(time);
			report += '\n';
		}
		Iterator<Vehiculo> it3 = vehiculos.iterator();
		while(it3.hasNext()){
			report += it3.next().generaInforme(time);
			report += '\n';
		}
		return report;
	}

	public void actualizar() {
		Iterator <CruceGenerico<?>> it = cruces.iterator();
		Iterator <Carretera> it2 = carreteras.iterator();
		while(it.hasNext()){
			it.next().avanza();
		}
		while(it2.hasNext()){
			it.next().avanza();
		}
		// llama al método avanza de cada cruce
		// llama al método avanza de cada carretera
	}

	public CruceGenerico<?> getCruce(String id) throws NoExiste {
		if (mapaDeCruces.containsKey(id))
			return mapaDeCruces.get(id); // devuelve el cruce con ese “id” utilizando el mapaDeCruces.
		else throw new NoExiste("Ese cruce no existe");// sino existe el cruce lanza excepción.
	}

	public Vehiculo getVehiculo(String id) throws NoExiste {
		if(mapaDeVehiculos.containsKey(id))
			return mapaDeVehiculos.get(id);// devuelve el vehículo con ese “id” utilizando el mapaDeVehiculos.
		else throw new NoExiste("Ese vehiculo no existe");// sino existe el vehículo lanza excepción.
	}

	public Carretera getCarretera(String id) throws NoExiste {
		if(mapaDeCarreteras.containsKey(id))
			return mapaDeCarreteras.get(id);// devuelve la carretera con ese “id”
										// utilizando el mapaDeCarreteras.
		else throw new NoExiste("Esa carretera no existe");// sino existe la carretra lanza excepción.
	}
	
	public List<Carretera> getCarreteras(){
		return carreteras;
	}
	public List<CruceGenerico<?>> getCruces(){
		return cruces;
	}
	public List<Vehiculo> getVehiculos() {
		return vehiculos;
	}
	public void sercarreteras(List<Carretera> carreteras){
		this.carreteras = carreteras;
	}
	public void setcruces(List<CruceGenerico<?>> cruces){
		this.cruces = cruces;
	}
}