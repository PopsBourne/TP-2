package simulador;

import java.util.List;

import codigo.es.ucm.fdi.ini.IniSection;
import excepciones.NoExiste;

public class Vehiculo extends ObjetoSimulacion {
	//private static final String NEWLINE = System.getProperty("line.separator");

	protected Carretera carretera; // carretera en la que est� el veh�culo
	protected int velocidadMaxima; // velocidad m�xima
	protected int velocidadActual; // velocidad actual
	protected int kilometraje; // distancia recorrida

	protected int localizacion;// (..., 30)
	protected int tiempoAveria; // tiempo que estar� averiado
	protected List<Cruce> itinerario; // itinerario a recorrer (m�nimo 2)
	
	protected int cont;

	protected boolean haLlegado;
	protected boolean estEnCruce;
	

	// comprobar que la velocidadMaxima es mayor o igual que 0, y  que el itinerario tiene al menos dos cruces.
	// En caso de no cumplirse lo anterior, lanzar una excepci�n.
	//inicializar los atributos teniendo en cuenta los par�metros.
	// al crear un veh�culo su �carretera� ser� inicalmene �null�.
	public Vehiculo(String id, int velocidadMaxima, List<Cruce> iti) throws NoExiste {
		super(id);
		if(velocidadMaxima < 0 || iti.size() < 2)// En caso de no cumplirse lo anterior, lanzar una excepci�n.
			throw new NoExiste("No se cumple la condicion");
		
		this.itinerario = iti;
		this.velocidadMaxima = velocidadMaxima;
		carretera=null;
		this.cont=0;
		velocidadActual=0;
		kilometraje=0;
		tiempoAveria=0;
		haLlegado=false;
		estEnCruce=false;
	}

	public void avanza() {
		if(!haLlegado) {
			if (getTiempoAveria() > 0)
				tiempoAveria--;// si el coche est� averiado, decrementar tiempoAveria
			else if (getTiempoAveria() == 0){
						this.localizacion += velocidadActual;					
				if (this.localizacion >= carretera.getLongitud()) {
					this.localizacion -= velocidadActual;	
				    if(!estEnCruce)
				    	this.cont++;	
					kilometraje += carretera.getLongitud() - localizacion;// Actualizar su �kilometraje�
					this.localizacion = carretera.getLongitud();
					if(!estEnCruce)
						carretera.entraVehiculoAlCruce(this);//Indicar a la carretera que el veh�culo entra al cruce.
					this.estEnCruce=true;
					velocidadActual = 0 ;
				}else {
					kilometraje += velocidadActual;// Actualizar su �kilometraje�
					this.estEnCruce=false;
				}	
			}
		}
	}
	
	public void moverASiguienteCarretera() {
		//cont++;
		//Iterator <Cruces> it = itinerario.iterator();
		// Si la carretera no es null, sacar el veh�culo de la carretera.
		if(this.carretera != null) {
			carretera.saleVehiculo(this);
			Cruce c1 = carretera.getCruceDestino();
			// Si hemos llegado al �ltimo cruce del itinerario, entonces:
			if (cont == itinerario.size()-1) {
				this.haLlegado=true;
				this.carretera=null;
				this.velocidadActual=0;
				this.localizacion=0;
				//this.estEnCruce = true;
			}else {
				Cruce c2 = itinerario.get(cont+1);
				Carretera ct = c1.carreteraHaciaCruce(c2);
				this.localizacion=0;
				this.carretera = ct;
				ct.entraVehiculo(this);
				this.estEnCruce=false;
				
			}	
			
		}
		else {
			Cruce c1 = itinerario.get(0);
			Cruce c2 = itinerario.get(1);
			Carretera c = c1.carreteraHaciaCruce(c2);
			setCarretera(c);
			this.localizacion=0;
			c.entraVehiculo(this);
		}
	}
	
	public String getNombreSeccion() {
	return "vehicle_report";
	}
	
	@Override
	public void completaDetallesSeccion(IniSection is) {
		// ...
		is.setValue("id", this.id);
		//is.setValue("time", this.tiempoAveria);
		is.setValue("speed", this.velocidadActual);
		is.setValue("kilometrage", this.kilometraje);
		is.setValue("faulty", this.tiempoAveria);
		is.setValue("location", this.haLlegado ? "arrived" : "("+this.carretera + ","
				+ this.getLocalizacion()+")");
	}

	public void setTiempoAveria(Integer duracionAveria) {
		// Comprobar que �carretera� no es null.
		if (carretera != null) {
		// Se fija el tiempo de aver�a de acuerdo con el enunciado.
			this.tiempoAveria = duracionAveria; 
		// Si el tiempo de aver�a es finalmente positivo, entonces la
		// �velocidadActual� se pone a 0
		if (this.tiempoAveria > 0)
			this.velocidadActual = 0;
		}
	}

	public int getTiempoAveria() {
		return tiempoAveria;
	}

	public int getVelocidadActual() {
		return velocidadActual;
	}
	
	public void setCarretera(Carretera c) {
		carretera = c;
	}

	public void setVelocidadActual(int velocidad) {
		// Si �velocidad� es negativa, entonces la �velocidadActual� es 0.
		if (velocidad < 0)
			this.velocidadActual = 0;
		// Si �velocidad� excede a �velocidadMaxima�, entonces la
		// �velocidadActual� es �velocidadMaxima�
		else if (velocidad > velocidadMaxima)
			this.velocidadActual = velocidadMaxima;
		// En otro caso, �velocidadActual� es �velocidad�
		else
			this.velocidadActual = velocidad;
	}

	public int getLocalizacion() {
		return localizacion;
	}

	public int getTiempoDeInfraccion() {
		return 0;// getTiempoDeInfraccion??
	}	
	
	public int getKilometraje() {
		return kilometraje;
	}
}