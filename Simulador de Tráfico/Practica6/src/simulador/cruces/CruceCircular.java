package simulador.cruces;

import java.util.Iterator;

import simulador.CarreteraEntranteConIntervalo;
import simulador.carreteras.Carretera;
import codigo.es.ucm.fdi.ini.IniSection;

public class CruceCircular extends CruceGenerico<CarreteraEntranteConIntervalo> {
	protected int minValorIntervalo;
	protected int maxValorIntervalo;

	public CruceCircular(String id, int minValorIntervalo, int maxValorIntervalo) {
		super(id);
		this.minValorIntervalo = minValorIntervalo;
		this.maxValorIntervalo = maxValorIntervalo;
	}

	@Override
	protected void actualizaSemaforos() {
		// Si no hay carretera con semáforo en verde (indiceSemaforoVerde == -1)
		if (indiceSemaforoVerde == -1) {
			// entonces se selecciona la primera carretera entrante (la de la posición 0) y
			// se pone su semáforo en verde.
			carreterasEntrantes.get(0).setSemaforo(true);
			carreterasEntrantes.get(0).setIntervaloDeTiempo(maxValorIntervalo);
			carreterasEntrantes.get(0).setUnidadesDeTiempoUsadas(0);
			indiceSemaforoVerde = 0;
		}
		// Si hay carretera entrante "ri" con su semáforo en verde,(indiceSemaforoVerde
		// != -1) entonces:
		else {
			// 1. Si ha consumido su intervalo de tiempo en verde ("ri.tiempoConsumido()"):
			// 1.1. Se pone el semáforo de "ri" a rojo.
			// 1.2. Si ha sido usada en todos los pasos (“ri.usoCompleto()”), se fija el
			// intervalo de tiempo a ...
			// Sino, si no ha sido usada (“!ri.usada()”) se fija el intervalo de tiempo a ...
			// 1.3. Se coge como nueva carretera con semáforo a verde la inmediatamente
			// Posterior a “ri”.
			CarreteraEntranteConIntervalo ri = carreterasEntrantes.get(indiceSemaforoVerde);
			if (ri.tiempoConsumido()) {
				ri.setSemaforo(false);
				if (ri.isUsoCompleto())
					ri.setIntervaloDeTiempo(Math.min(ri.getIntervaloDeTiempo() + 1, this.maxValorIntervalo));
				else if (!ri.isUsadaPorUnVehiculo())
					ri.setIntervaloDeTiempo(Math.max(ri.getIntervaloDeTiempo() - 1, this.minValorIntervalo));

				ri.setUsadaPorUnVehiculo(false);
				ri.setUsoCompleto(true);
				ri.setUnidadesDeTiempoUsadas(0);
				//ri = carreterasEntrantes.get((indiceSemaforoVerde + 1)%carreterasEntrantes.size());
				indiceSemaforoVerde =(indiceSemaforoVerde + 1)%carreterasEntrantes.size();
				carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
			}else {
				//ri.setUnidadesDeTiempoUsadas(ri.getUnidadesDeTiempoUsadas()+1);
			}
		}
	}

	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) {
		CarreteraEntranteConIntervalo ci = new CarreteraEntranteConIntervalo(carretera, 0);
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
			s += "(" + ce.getCarretera().getId()+ "," + (ce.getSemaforo()?"green:" + (ce.getIntervaloDeTiempo()-ce.getUnidadesDeTiempoUsadas()) : "red") + "," + "[" + x + "]" + "),";
		}
		if(!s.equals(""))
			s = s.substring(0, s.length()-1);
		is.setValue("queues",s);
		is.setValue("type", "rr");
	}
}
