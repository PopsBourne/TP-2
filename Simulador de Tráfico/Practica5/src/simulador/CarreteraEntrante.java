package simulador;

import java.util.ArrayList;
import java.util.List;

import simulador.carreteras.Carretera;
import simulador.vehiculos.Vehiculo;

public class CarreteraEntrante {

	protected Carretera carretera;
	protected List<Vehiculo> colaVehiculos;
	private boolean semaforo; // true=verde, false=rojo

	public CarreteraEntrante(Carretera carretera) {
		setSemaforo(false);
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
		return isSemaforo();
	}
	
	public List<Vehiculo> getColaVehiculos() {
		return colaVehiculos;
	}
	
	public void addVehiculoCola(Vehiculo vehiculo) {
		colaVehiculos.add(vehiculo);
	}

	public void ponSemaforo(boolean color) {
		this.setSemaforo(color);
	}

	public void avanzaPrimerVehiculo() {
		if(colaVehiculos.size()>0) {
		colaVehiculos.get(0).moverASiguienteCarretera();
		colaVehiculos.remove(0);
		}
		//coge el primer vehiculo de la cola, lo elimina,
		// y le manda que se mueva a su siguiente carretera.
	}

	public String toString() {
		String s ="";
		String x = "";
		for(int i = 0 ; i< colaVehiculos.size();i++)
			x += colaVehiculos.get(i).getId() + ",";
		if(!x.equals(""))
			x = x.substring(0, x.length()-1);					
		s += "(" + getCarretera().getId()+ "," + (getSemaforo()? "green" : "red")+ "," + "[" + x + "]" + "),";
		if(!s.equals(""))
			s = s.substring(0, s.length()-1);
		return s;
	}
	//HACER METODO PARA IMPRIMIR LA COLA DE VEHICULOS

	public boolean isSemaforo() {
		return semaforo;
	}

	public void setSemaforo(boolean semaforo) {
		this.semaforo = semaforo;
	}
}