package codigo.es.ucm.fdi.main;

public enum ModoEjecucion {
	BATCH("batch"), GUI("gui");
	 private String descModo;
	 private ModoEjecucion(String modeDesc) {
	 descModo = modeDesc;
	 }
	 
	 public String getModelDesc() {
		 return descModo;
	 }
}
