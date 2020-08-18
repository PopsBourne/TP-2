package simulador.cruces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simulador.CarreteraEntrante;
import simulador.ObjetoSimulacion;
import simulador.carreteras.Carretera;
import simulador.vehiculos.Vehiculo;

public abstract class CruceGenerico<T extends CarreteraEntrante> extends ObjetoSimulacion {

	protected int indiceSemaforoVerde;
	protected List<T> carreterasEntrantes;
	protected Map<String, T> mapaCarreterasEntrantes;
	protected Map<CruceGenerico<?>, Carretera> carreterasSalientes;

	public CruceGenerico(String id) {
		super(id);
		carreterasEntrantes = new ArrayList<T>();
		mapaCarreterasEntrantes = new HashMap<>();
		carreterasSalientes = new HashMap<>();
		indiceSemaforoVerde = -1;
	}

	public Carretera carreteraHaciaCruce(CruceGenerico<?> cruce) {
		 Carretera c = carreterasSalientes.get(cruce);
		 	return c;
	}

	public void addCarreteraEntranteAlCruce(String idCarretera, Carretera carretera) {
		T ri = creaCarreteraEntrante(carretera);
		mapaCarreterasEntrantes.put(idCarretera, ri);
		carreterasEntrantes.add(ri);
	}
	
	abstract protected T creaCarreteraEntrante(Carretera carretera);

	public void addCarreteraSalienteAlCruce(CruceGenerico<?> destino, Carretera carr) {
		carreterasSalientes.put(destino, carr);
	}

	public void entraVehiculoAlCruce(String idCarretera, Vehiculo vehiculo) {
		boolean encontrado = false;
		int i = 0;
		while(!encontrado && i < carreterasEntrantes.size()){
			if(carreterasEntrantes.get(i).getCarretera().getId().equals(idCarretera))
				encontrado = true;
			i++;
		}
		if(encontrado)
			carreterasEntrantes.get(i-1).addVehiculoCola(vehiculo);
	}

	@Override
	public void avanza() {
		if(indiceSemaforoVerde!= -1)
			carreterasEntrantes.get(indiceSemaforoVerde).avanzaPrimerVehiculo();
		this.actualizaSemaforos();
	}

	abstract protected void actualizaSemaforos();
	
	public List<T> getCarreterasEntrantes() {
		return carreterasEntrantes;
	}
	public int getSemaforo() {
		return indiceSemaforoVerde;
	}
}
