package simulador.cruces;

import java.util.Iterator;

import simulador.CarreteraEntranteConIntervalo;
import simulador.carreteras.Carretera;
import codigo.es.ucm.fdi.ini.IniSection;

public class CruceCongestionado extends CruceGenerico<CarreteraEntranteConIntervalo> {
	// no tiene atributos
	
	public CruceCongestionado(String id) {
		super(id);
	}

	@Override
	protected void actualizaSemaforos() {
		if (indiceSemaforoVerde == -1) {
			/*
			 * - Si no hay carretera con semáforo en verde (indiceSemaforoVerde ==
			 * -1) entonces se selecciona la carretera que tenga más vehículos en su
			 * cola de vehículos.
			 */
			CarreteraEntranteConIntervalo caEnt= carreterasEntrantes.get(maxColaVehiculos());
			caEnt.setIntervaloDeTiempo(Math.max(caEnt.numVehiculosEnCola()/2,1));
			caEnt.setUnidadesDeTiempoUsadas(0);
			caEnt.setSemaforo(true);
			indiceSemaforoVerde = maxColaVehiculos();
		
		}else {
			CarreteraEntranteConIntervalo ri = carreterasEntrantes.get(indiceSemaforoVerde);
			
				//1. Si ha consumido su intervalo de tiempo en verde
				//("ri.tiempoConsumido()"): 1.1. Se pone el semáforo de "ri" a
				//rojo. 1.2. Se inicializan los atributos de "ri".
		 
			if (ri.tiempoConsumido()) {
				//- Si hay carretera entrante "ri" con su semáforo en verde,
				//(indiceSemaforoVerde != -1) entonces:
				ri.setSemaforo(false);
				ri.setUnidadesDeTiempoUsadas(0);
				ri.setUsoCompleto(false);
				ri.setUsadaPorUnVehiculo(false);
				ri.setIntervaloDeTiempo(0);
				
				  //1.3. Se busca la posición "max" que ocupa la primera carretera
				  //entrante distinta de "ri" con el mayor número de vehículos en su
				  //cola.
				int max = maxColaVehiculosNoRepetido(indiceSemaforoVerde);
				
				 //1.4. "indiceSemaforoVerde" se pone a "max".
				indiceSemaforoVerde = max;
				
				 //1.5. Se pone el semáforo de la carretera entrante en la posición
				 //"max" ("rj") a verde y se inicializan los atributos de "rj", entre
				 //ellos el "intervaloTiempo" a Math.max(rj.numVehiculosEnCola()/2,1).
				CarreteraEntranteConIntervalo rj = carreterasEntrantes.get(indiceSemaforoVerde);
				rj.setSemaforo(true);
				rj.setIntervaloDeTiempo(Math.max(rj.numVehiculosEnCola()/2,1));
				rj.setUnidadesDeTiempoUsadas(0);
				rj.setUsoCompleto(false);
				rj.setUsadaPorUnVehiculo(false);
			}
		}
	}

	protected int maxColaVehiculosNoRepetido(int pos) {
		int max;
		int posicion = 0 ;
		if(carreterasEntrantes.size() == 1) {
			return 0;
		}else {
			max = -1;
			for (int i = 0; i < carreterasEntrantes.size(); i++) {
				if(max == -1 && pos != i) {
					max = carreterasEntrantes.get(i).numVehiculosEnCola();
					posicion = i ;
				}
				
				if (max < carreterasEntrantes.get(i).numVehiculosEnCola() && pos != i) {
					max = carreterasEntrantes.get(i).numVehiculosEnCola();
					posicion = i;
				}
			}
			return posicion;
		}	
	}

	protected int maxColaVehiculos() {
		int pos = 0;
		int max;
		max = carreterasEntrantes.get(0).numVehiculosEnCola();
		for (int i = 1; i < carreterasEntrantes.size(); i++) {
			if (max < carreterasEntrantes.get(i).numVehiculosEnCola()) {
				max = carreterasEntrantes.get(i).numVehiculosEnCola();
				pos = i;
			}
		}
		return pos;
	}

	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(
			Carretera carretera) {
		CarreteraEntranteConIntervalo ci = new CarreteraEntranteConIntervalo(
				carretera, 0);
		return ci;
	}

	@Override
	public String getNombreSeccion() {
		return "junction_report";
	}

	@Override
	public void completaDetallesSeccion(IniSection is) {
		String s ="";
		Iterator<CarreteraEntranteConIntervalo> it = carreterasEntrantes.iterator();
		while(it.hasNext()) {
			CarreteraEntranteConIntervalo ce = it.next();
			String x = "";
			for(int i = 0 ; i< ce.numVehiculosEnCola();i++)
				x += ce.getColaVehiculos().get(i).getId() + ",";
			if(!x.equals(""))
				x = x.substring(0, x.length()-1);					
			s += "(" + ce.getCarretera().getId()+ "," + (ce.getSemaforo()? "green:"+ce.getIntervaloDeTiempo() : "red") + "," + "[" + x + "]" + "),";
		}
		if(!s.equals(""))
			s = s.substring(0, s.length()-1);
		is.setValue("queues",s);
		is.setValue("type", "mc");
	}
}
