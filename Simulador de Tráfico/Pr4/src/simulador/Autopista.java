package simulador;

import codigo.es.ucm.fdi.ini.IniSection;

public class Autopista extends Carretera {

	private int numCarriles;
	
	public Autopista(int numCarriles, String id, int length, int maxSpeed, Cruce src, Cruce dest) {
		super(id, length, maxSpeed, src, dest);
		this.numCarriles = numCarriles;
	}

	 @Override  
	 protected int calculaVelocidadBase() {
		 return velocidadBase = Math.min(velocidadMaxima, (velocidadMaxima*numCarriles / Math.max(vehiculos.size(), 1)) + 1);
	 } 
	 @Override 
	 protected int calculaFactorReduccion(int obstacles) { 
		 return obstacles < this.numCarriles ? 1 : 2; 
	}
	 
	 public void completaDetallesSeccion(IniSection is) {
			super.completaDetallesSeccion(is);
			is.setValue("type", "lanes");
			is.setValue("lanes", numCarriles);
	}
}