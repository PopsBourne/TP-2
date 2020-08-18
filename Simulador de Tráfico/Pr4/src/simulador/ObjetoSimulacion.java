package simulador;

import codigo.es.ucm.fdi.ini.IniSection;

public abstract class ObjetoSimulacion {
	protected String id;

	public ObjetoSimulacion(String id) {
			this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public String toString() {
		return id;
	}

	public String generaInforme(int tiempo) {
		IniSection is = new IniSection(this.getNombreSeccion());
		is.setValue("id", this.id);
		is.setValue("time", tiempo);
		this.completaDetallesSeccion(is);
		return is.toString();
	}

	// los métodos getNombreSeccion y completaDetallesSeccion
	// tendrán que implementarlos cada subclase de ObjetoSimulacion
	public abstract String getNombreSeccion();
	public abstract void avanza();
	public abstract void completaDetallesSeccion(IniSection is) ;
}