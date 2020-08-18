package simulador;

import java.util.List;

import excepciones.NoExiste;

public class Bicicleta extends Vehiculo  {

	public Bicicleta(String id, int velocidadMaxima, List<Cruce> iti) throws NoExiste {
		super(id, velocidadMaxima, iti);
	}
	public void setTiempoAveria(Integer duracionAveria) {
		// Comprobar que “carretera” no es null.
		if (carretera != null && this.velocidadActual >= this.velocidadMaxima / 2) {
		// Se fija el tiempo de avería de acuerdo con el enunciado.
			this.tiempoAveria = duracionAveria; 
		// Si el tiempo de avería es finalmente positivo, entonces la
		// “velocidadActual” se pone a 0
		if (this.tiempoAveria > 0)
			this.velocidadActual = 0;
		}
	}
}