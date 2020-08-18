package simulador.carreteras;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import simulador.ObjetoSimulacion;
import simulador.cruces.CruceGenerico;
import simulador.vehiculos.Vehiculo;
import codigo.es.ucm.fdi.ini.IniSection;
import eventos.SortedArrayList;

public class Carretera extends ObjetoSimulacion {

	protected int longitud; // longitud de la carretera
	protected int velocidadMaxima; // velocidad m�xima
	protected CruceGenerico<?> cruceOrigen; // cruce del que parte la carretera
	protected CruceGenerico<?> cruceDestino; // cruce al que llega la carretera
	protected List<Vehiculo> vehiculos; // lista ordenada de veh�culos en la carretera (ordenada por localizaci�n)
	
	protected Comparator<Vehiculo> comparadorVehiculo; // orden entre veh�culos
	
	protected int velocidadBase;
	protected int factorReduccion;

	public int getVelocidadMaxima() {
		return velocidadMaxima;
	}

	public void setVelocidadMaxima(int velocidadMaxima) {
		this.velocidadMaxima = velocidadMaxima;
	}

	public Carretera(String id, int length, int maxSpeed, CruceGenerico<?> src, CruceGenerico<?> dest) {
		super(id);// time
		this.longitud = length;
		this.velocidadMaxima = maxSpeed;
		this.cruceDestino = dest;
		this.cruceOrigen = src;
		//vehiculos = new SortedArrayList<Vehiculo>();
		// se fija el orden entre los veh�culos: (inicia comparadorVehiculo) - la
		// localizaci�n 0 es la menor
		comparadorVehiculo = new Comparator<Vehiculo>() {
			public int compare(Vehiculo v1, Vehiculo v2) {
			if(v1.getLocalizacion() < v2.getLocalizacion()) return 1;
			else if(v1.getLocalizacion() == v2.getLocalizacion()) return 0;
			else return -1;
			}
		};
		vehiculos = new SortedArrayList<Vehiculo>(comparadorVehiculo);
		velocidadBase = 0;
		factorReduccion = 1;
}

	// Si el veh�culo no existe en la carretera, se a�ade a la lista de veh�culos y se ordena la lista
	public void entraVehiculo(Vehiculo v) {// ORDENAR
		boolean encontrado = false;
		int i = 0;
		while(!encontrado && i < vehiculos.size()){
			if(vehiculos.get(i).getId() == v.getId())
			encontrado = true;
			i++;
		}
		if(!encontrado){
			vehiculos.add(v);
		}
	}
	
	public List<Vehiculo> getVehiculos(){
		return vehiculos;
	}

	// elimina el veh�culo de la lista de veh�culos
	public void saleVehiculo(Vehiculo v) {
		vehiculos.remove(v);
	}
	
	public CruceGenerico<?> getCruceDestino() {
		return cruceDestino;
	}
	
	public CruceGenerico<?> getCruceOrigen() {
		return cruceOrigen;
	}

	// a�ade el veh�culo al �cruceDestino� de la carretera�
	public void entraVehiculoAlCruce(Vehiculo v) {
		cruceDestino.entraVehiculoAlCruce(id, v);
	}

	protected int calculaVelocidadBase() {
		velocidadBase = Math.min(velocidadMaxima, (velocidadMaxima / Math.max(vehiculos.size(), 1)) + 1);
		return velocidadBase;
	}

	protected int calculaFactorReduccion(int obstaculos) {
		if (obstaculos == 0) factorReduccion = 1;
		else factorReduccion = 2;
		return factorReduccion;
	}

	public String getNombreSeccion() {
		return "road_report";
	}

	@Override
	public void completaDetallesSeccion(IniSection is) {
		// crea �vehicles = (v1,10),(v2,10) �
		String s = "";
		for(int i = 0; i < vehiculos.size(); i++){
			s += "(" + vehiculos.get(i).getId() + "," + vehiculos.get(i).getLocalizacion() + ")" + ",";
		}
		if(!s.equals(""))
			s = s.substring(0, s.length()-1);
		is.setValue("state", s);
	}

	public void avanza() {
		velocidadBase = calculaVelocidadBase();// calcular velocidad base de la carretera
		int obstaculos = 0;// inicializar obst�culos a 0
		// Para cada veh�culo de la lista �vehiculos�:
		// 1. Si el veh�culo est� averiado se incrementa el n�mero de obstaculos.
		Iterator<Vehiculo> it = vehiculos.iterator();
		while (it.hasNext()) {
			Vehiculo v = it.next();
			if (v.getTiempoAveria() > 0) {
				obstaculos++;
				//v.tiempoAveria--;
				v.setVelocidadActual(0);
				v.avanza();
			}else {
				// 2. Se fija la velocidad actual del veh�culo velocidad = velocidadbase/factorreduccion
				factorReduccion =  calculaFactorReduccion(obstaculos);
				v.setVelocidadActual(velocidadBase/factorReduccion);
				//v.tiempoAveria--;
				v.avanza(); // 3. Se pide al veh�culo que avance.			
			}
			// 2. Se fija la velocidad actual del veh�culo velocidad = velocidadbase/factorreduccion
			//factorReduccion =  calculaFactorReduccion(obstaculos);
		//	v.setVelocidadActual(velocidadBase/factorReduccion);
			//v.tiempoAveria--;
			//v.avanza(); // 3. Se pide al veh�culo que avance.
		}
		// ordenar la lista de veh�culos 
		Collections.sort(vehiculos, comparadorVehiculo);
	}

	public int getLongitud() {
		return longitud;
	}

	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}

	public int compare(Vehiculo v1, Vehiculo v2) {//Ordena el array
		return comparadorVehiculo.compare(v1, v2);
	}
}
