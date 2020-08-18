package simulador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import codigo.es.ucm.fdi.ini.IniSection;

public class Cruce extends ObjetoSimulacion {
	
	protected int indiceSemaforoVerde; // lleva el índice de la carretera
										// entrante con el semáforo en verde
	protected List<CarreteraEntrante> carreterasEntrantes;
	// para optimizar las búsquedas de las carreterasEntrantes
	// (IdCarretera,CarreteraEntrante)
	protected Map<String, CarreteraEntrante> mapaCarreterasEntrantes;
	protected Map<Cruce, Carretera> carreterasSalientes;

	public Cruce(String id) {
		super(id);
		carreterasEntrantes = new ArrayList<CarreteraEntrante>();
		mapaCarreterasEntrantes = new HashMap<>();
		carreterasSalientes = new HashMap<>();
		indiceSemaforoVerde = -1;
	}

	public Carretera carreteraHaciaCruce(Cruce cruce) {
		  Carretera c = carreterasSalientes.get(cruce);
		 	return c; 
		 
		/*
		boolean encontrado = false;
		int i = 0;
		while(!encontrado && i < carreterasEntrantes.size()){
			if (carreterasEntrantes.get(i).getCarretera().getCruceOrigen().getId().equals(id))
					encontrado = true;
			i++;
		}
		return carreterasEntrantes.get(i-1).getCarretera();*/
	// devuelve la carretera que llega a ese cruce desde “this”
	}

	public void addCarreteraEntranteAlCruce(String idCarretera, Carretera carretera) {/////////?????
		CarreteraEntrante carreteraEnt = new CarreteraEntrante(carretera);
		mapaCarreterasEntrantes.put(idCarretera, carreteraEnt);
		carreterasEntrantes.add(carreteraEnt);
		// añade una carretera entrante al
		// “mapaCarreterasEntrantes” y a las “carreterasEntrantes”
	}

	public void addCarreteraSalienteAlCruce(Cruce destino, Carretera road) {
		carreterasSalientes.put(destino, road);// añade una carretera saliente
	}

	public void entraVehiculoAlCruce(String idCarretera, Vehiculo vehiculo) {
		//buscar la carretera entrante que tenga esa idCarretera
		//añadir el vehiculo a la cola de vehiculos de esa carrtera entrante
		
		/*
		CarreteraEntrante ce = mapaCarreterasEntrantes.get(idCarretera);
		ce.addVehiculoCola(vehiculo);
		*/
	
		boolean encontrado = false;
		int i = 0;
		while(!encontrado && i < carreterasEntrantes.size()){
			if(carreterasEntrantes.get(i).getCarretera().getId().equals(idCarretera))
				encontrado = true;
			i++;
		}
		if(encontrado)
			carreterasEntrantes.get(i-1).addVehiculoCola(vehiculo);
		// añade el “vehiculo” a la carretera entrante “idCarretera”
	}

	protected void actualizaSemaforos() {
		// pone el semáforo de la carretera actual a “rojo”,
	
			carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(false);
			indiceSemaforoVerde = (indiceSemaforoVerde + 1) % carreterasEntrantes.size();
			// y busca la siguiente carretera entrante para ponerlo a “verde”
			carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
		
	}

	@Override
	public void avanza() {
		// Si “carreterasEntrantes” es vacío, no hace nada.	
		// en otro caso “avanzaPrimerVehiculo” de la carretera
		//con el semáforo verde.
		if (!carreterasEntrantes.isEmpty()) {
			
			if (indiceSemaforoVerde == -1) {
				carreterasEntrantes.get(0).ponSemaforo(true);
				indiceSemaforoVerde = (indiceSemaforoVerde + 1) % carreterasEntrantes.size();
			} else {
			carreterasEntrantes.get(indiceSemaforoVerde).avanzaPrimerVehiculo();
			actualizaSemaforos();// Posteriormente actualiza los semáforos.
			}
		}
	}

	public String getNombreSeccion() {
		return "junction_report";
	}

	@Override
	public void completaDetallesSeccion(IniSection is) {
		//is.setValue(id, carreterasEntrantes.get().semaforo,vehiculos);
		
		String s ="";
		Iterator<CarreteraEntrante> it = carreterasEntrantes.iterator();
		while(it.hasNext()) {
			CarreteraEntrante ce = it.next();
			String x = "";
			for(int i = 0 ; i< ce.colaVehiculos.size();i++)
				x += ce.colaVehiculos.get(i).getId() + ",";
			if(!x.equals(""))
				x = x.substring(0, x.length()-1);					
			s += "(" + ce.getCarretera().getId()+ "," + (ce.getSemaforo()? "green" : "red") + "," + "[" + x + "]" + "),";
		}
		if(!s.equals(""))
			s = s.substring(0, s.length()-1);
		is.setValue("queues",s);
	}
	
	/*public String generaInforme(int tiempo) {
		IniSection is = new IniSection(this.getNombreSeccion());
		is.setValue("id", this.id);
		is.setValue("time", tiempo);
		this.completaDetallesSeccion(is);
		return is.toString();
		
		//String a = "\n";
		//String s = "";
		//s += getNombreSeccion() + a + "id = " + this.id + a + "time = " + tiempo + a + "queues = " + completaDetallesSeccion() + a;
				//+ completaDetallesSeccion() + a;
		
		  //[road_report] id = r3 time = 4 state = (v2,80),(v3,67)
		 
		//return s;
	}*/
	
	public  List<CarreteraEntrante> getCarreterasEntrantes() {
		return carreterasEntrantes;
	}
}