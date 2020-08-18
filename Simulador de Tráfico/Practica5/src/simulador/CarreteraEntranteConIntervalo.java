package simulador;

import simulador.carreteras.Carretera;
import simulador.vehiculos.Vehiculo;

public class CarreteraEntranteConIntervalo  extends CarreteraEntrante {

	private int intervaloDeTiempo; // Tiempo que ha de transcurrir para poner el semáforo de la carretera en rojo
	private int unidadesDeTiempoUsadas; // Se incrementa cada vez que avanza un vehículo
	private boolean usoCompleto; // Controla que en cada paso con el semáforo en verde, ha pasado un vehículo
	private boolean usadaPorUnVehiculo; // Controla que al menos ha pasado un vehículo mientras el semáforo estaba en verde.

	public CarreteraEntranteConIntervalo(Carretera carretera, int intervalTiempo) {
		super(carretera);
		this.intervaloDeTiempo = intervalTiempo;
		this.unidadesDeTiempoUsadas = 0;
		this.usoCompleto = false;
		this.usadaPorUnVehiculo = false;
	}

	@Override
	public void avanzaPrimerVehiculo() {
		// Incrementa unidadesDeTiempoUsadas
		unidadesDeTiempoUsadas++;
		// Actualiza usoCompleto:
		//usoCompleto = true;
		// - Si “colaVehiculos” es vacía, entonces “usoCompleto=false”
		if (colaVehiculos.isEmpty())
			usoCompleto = false;
		// - En otro caso saca el primer vehículo “v” de la “colaVehiculos”,
		// y le mueve a la siguiente carretera (“v.moverASiguienteCarretera()”)
		// Pone “usadaPorUnVehiculo” a true.
		else {
			Vehiculo v = colaVehiculos.get(0);
			colaVehiculos.remove(0);
			v.moverASiguienteCarretera();
			usadaPorUnVehiculo = true;
		}
	}

	public boolean tiempoConsumido() {
		// comprueba si se ha agotado el intervalo de tiempo, “unidadesDeTiempoUsadas >= “intervaloDeTiempo”
		if (unidadesDeTiempoUsadas >= intervaloDeTiempo) 
			return true;			
		else
			return false;
	}

	public int getIntervaloDeTiempo() {
		return intervaloDeTiempo;
	}

	public void setIntervaloDeTiempo(int intervaloDeTiempo) {
		this.intervaloDeTiempo = intervaloDeTiempo;
	}
	
	public int getUnidadesDeTiempoUsadas() {
		return unidadesDeTiempoUsadas;
	}

	public void setUnidadesDeTiempoUsadas(int unidadesDeTiempoUsadas) {
		this.unidadesDeTiempoUsadas = unidadesDeTiempoUsadas;
	}

	public boolean isUsoCompleto() {
		return usoCompleto;
	}

	public void setUsoCompleto(boolean usoCompleto) {
		this.usoCompleto = usoCompleto;
	}

	public boolean isUsadaPorUnVehiculo() {
		return usadaPorUnVehiculo;
	}

	public void setUsadaPorUnVehiculo(boolean usadaPorUnVehiculo) {
		this.usadaPorUnVehiculo = usadaPorUnVehiculo;
	}

	public int numVehiculosEnCola() {
		return colaVehiculos.size();
	}
	
	public String toString() {
		String s ="[";
		String x = "";
		for(int i = 0 ; i< colaVehiculos.size();i++)
			x += colaVehiculos.get(i).getId() + ",";
		if(!x.equals(""))
			x = x.substring(0, x.length()-1);					
		s += "(" + getCarretera().getId()+ "," + (getSemaforo()? "green" : "red") +(intervaloDeTiempo>0 ? ":"+intervaloDeTiempo : "") + "," + "[" + x + "]" + "),";
		if(!s.equals(""))
			s = s.substring(0, s.length()-1);
		s += "]";
		return s;
	}
}
