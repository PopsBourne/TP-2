package simulator.launcher;

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
