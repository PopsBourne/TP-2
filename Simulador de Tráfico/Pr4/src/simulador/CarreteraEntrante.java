package simulador;

import java.util.ArrayList;
import java.util.List;

public class CarreteraEntrante {

	protected Carretera carretera;
	protected List<Vehiculo> colaVehiculos;
	protected boolean semaforo; // true=verde, false=rojo

	public CarreteraEntrante(Carretera carretera) {
		semaforo = false;// el semáforo a rojo
		this.carretera = carretera;
		colaVehiculos = new ArrayList<Vehiculo>();
	}
	
	public void setCarretera(Carretera carretera) {
		this.carretera = carretera;
	}
	public Carretera getCarretera() {
		return carretera;
	}
	
	public boolean getSemaforo() {
		return semaforo;
	}
	
	public void addVehiculoCola(Vehiculo vehiculo) {
		colaVehiculos.add(vehiculo);
	}

	void ponSemaforo(boolean color) {
		this.semaforo = color;
	}

	public void avanzaPrimerVehiculo() {
		if(colaVehiculos.size()>0) {
		colaVehiculos.get(0).moverASiguienteCarretera();
		colaVehiculos.remove(0);
		}
		//coge el primer vehiculo de la cola, lo elimina,
		// y le manda que se mueva a su siguiente carretera.
	}

	
	/*public String toString() {
		return null;
	}*/
	//HACER METODO PARA IMPRIMIR LA COLA DE VEHICULOS
}