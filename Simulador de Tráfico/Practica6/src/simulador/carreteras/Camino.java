package simulador.carreteras;

import simulador.cruces.CruceGenerico;
import codigo.es.ucm.fdi.ini.IniSection;

public class Camino extends Carretera {

	public Camino(String id, int longitud, int velocidadMaxima, CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino) {
		super(id, longitud, velocidadMaxima, cruceOrigen, cruceDestino);
	}
	
	 @Override  
	 protected int calculaVelocidadBase() {
		 return this.velocidadMaxima;
	}  
	 
	 @Override  
	 protected int calculaFactorReduccion(int obstacles) { 
		 return obstacles + 1; 
	}
	 
	 @Override
	 //protected
	 public void completaDetallesSeccion(IniSection is) {
		 is.setValue("type", "dirt");
		 super.completaDetallesSeccion(is);
	}
}
