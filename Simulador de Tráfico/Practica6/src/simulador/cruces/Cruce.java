package simulador.cruces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import simulador.CarreteraEntrante;
import simulador.carreteras.Carretera;
import simulador.vehiculos.Vehiculo;
import codigo.es.ucm.fdi.ini.IniSection;

public class Cruce extends CruceGenerico<CarreteraEntrante>  {
	
	protected int indiceSemaforoVerde; // lleva el �ndice de la carretera
										
	// entrante con el sem�foro en verde
	protected List<CarreteraEntrante> carreterasEntrantes;
	// para optimizar las b�squedas de las carreterasEntrantes
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
	}

	public void addCarreteraEntranteAlCruce(String idCarretera, Carretera carretera) {/////////????
		CarreteraEntrante carreteraEnt = new CarreteraEntrante(carretera);
		mapaCarreterasEntrantes.put(idCarretera, carreteraEnt);
		carreterasEntrantes.add(carreteraEnt);
		// a�ade una carretera entrante al �mapaCarreterasEntrantes� y a las �carreterasEntrantes�
	}

	public void addCarreteraSalienteAlCruce(Cruce destino, Carretera road) {
		carreterasSalientes.put(destino, road);// a�ade una carretera saliente
	}

	public void entraVehiculoAlCruce(String idCarretera, Vehiculo vehiculo) {
		//buscar la carretera entrante que tenga esa idCarretera
		//a�adir el vehiculo a la cola de vehiculos de esa carrtera entrante
		boolean encontrado = false;
		int i = 0;
		while(!encontrado && i < carreterasEntrantes.size()){
			if(carreterasEntrantes.get(i).getCarretera().getId().equals(idCarretera))
				encontrado = true;
			i++;
		}
		if(encontrado)
			carreterasEntrantes.get(i-1).addVehiculoCola(vehiculo);
		// a�ade el �vehiculo� a la carretera entrante �idCarretera�
	}

	protected void actualizaSemaforos() {
		// pone el sem�foro de la carretera actual a �rojo�,
			carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(false);
			indiceSemaforoVerde = (indiceSemaforoVerde + 1) % carreterasEntrantes.size();
			// y busca la siguiente carretera entrante para ponerlo a �verde�
			carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
	}

	@Override
	public void avanza() {
		// Si �carreterasEntrantes� es vac�o, no hace nada. en otro caso
		// �avanzaPrimerVehiculo� de la carretera con el sem�foro verde
		if (!carreterasEntrantes.isEmpty()) {
			
			if (indiceSemaforoVerde == -1) {
				carreterasEntrantes.get(0).ponSemaforo(true);
				indiceSemaforoVerde = (indiceSemaforoVerde + 1) % carreterasEntrantes.size();
			} else {
			carreterasEntrantes.get(indiceSemaforoVerde).avanzaPrimerVehiculo();
			actualizaSemaforos();// Posteriormente actualiza los sem�foros.
			}
		}
	}

	public String getNombreSeccion() {
		return "junction_report";
	}

	@Override
	public void completaDetallesSeccion(IniSection is) {
		String s ="";
		Iterator<CarreteraEntrante> it = carreterasEntrantes.iterator();
		while(it.hasNext()) {
			CarreteraEntrante ce = it.next();
			String x = "";
			for(int i = 0 ; i< ce.getColaVehiculos().size();i++)
				x += ce.getColaVehiculos().get(i).getId() + ",";
			if(!x.equals(""))
				x = x.substring(0, x.length()-1);					
			s += "(" + ce.getCarretera().getId()+ "," + (ce.getSemaforo()? "green" : "red") + "," + "[" + x + "]" + "),";
		}
		if(!s.equals(""))
			s = s.substring(0, s.length()-1);
		is.setValue("queues",s);
	}

	//*****************extends CruceGenerico<CarreteraEntrante>****************
	public  List<CarreteraEntrante> getCarreterasEntrantes() {
		return carreterasEntrantes;
	}

	@Override
	protected CarreteraEntrante creaCarreteraEntrante(Carretera carretera) {
		return null;
	}
	
	public int getIndiceSemaforoVerde() {
		return indiceSemaforoVerde;
	}

	public void setIndiceSemaforoVerde(int indiceSemaforoVerde) {
		this.indiceSemaforoVerde = indiceSemaforoVerde;
	}
}