package simulador;

import java.util.List;
import java.util.Random;

import codigo.es.ucm.fdi.ini.IniSection;
import excepciones.NoExiste;

public class Coche extends Vehiculo {

	protected int kmUltimaAveria;
	protected int resistenciaKm;
	protected int duracionMaximaAveria;
	protected double probabilidadDeAveria;
	protected Random numAleatorio;
	protected long semilla;

	public Coche(String id, int velocidadMaxima, int resistencia, double probabilidad, long semilla,
					int duracionMaximaInfraccion, List<Cruce> itinerario) throws NoExiste {
		super(id, velocidadMaxima,itinerario);
		this.numAleatorio = new Random(semilla);
		this.kmUltimaAveria = 0; // ?????
		this.resistenciaKm = resistencia ;
		this.duracionMaximaAveria = duracionMaximaInfraccion;
		this.probabilidadDeAveria = probabilidad;
		this.semilla = semilla;
	}

	@Override
	public void avanza() {
		// - Si el coche está averiado poner “kmUltimaAveria” a “kilometraje”.
		if(this.getTiempoAveria() > 0)
			kmUltimaAveria = getKilometraje();
		else {
			int recorrido = kilometraje - kmUltimaAveria;
			if(recorrido >= resistenciaKm) {
				double prob = numAleatorio.nextDouble();
				if(prob < probabilidadDeAveria) 
					this.tiempoAveria = this.numAleatorio.nextInt(this.duracionMaximaAveria) + 1;
			}
			// - Sino el coche no está averiado y ha recorrido “resistenciakm”, y además
			// “numAleatorio”.nextDouble() < “probabilidadDeAveria”, entonces
			// incrementar “tiempoAveria” con “numAleatorio.nextInt(“duracionMaximaAveria”)+1
			// - Llamar a super.avanza();
			super.avanza();
		}
	}

	@Override
	// protected
	public void completaDetallesSeccion(IniSection is) {
		is.setValue("type", "car");
		is.setValue("resistance", this.resistenciaKm);
		is.setValue("faulty_probability", probabilidadDeAveria);
		is.setValue("max_fault_duration", duracionMaximaAveria);
		is.setValue("seed",semilla );
	}

}