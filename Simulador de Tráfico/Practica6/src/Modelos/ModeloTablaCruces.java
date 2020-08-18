package Modelos;

import java.util.List;

import Observadores.ModeloTabla;
import eventos.Controlador;
import eventos.tiposeventos.Evento;
import excepciones.ErrorDeSimulacion;
import simulador.MapaCarreteras;
import simulador.cruces.CruceGenerico;

@SuppressWarnings("serial")
public class ModeloTablaCruces extends ModeloTabla<CruceGenerico<?>>{

	public ModeloTablaCruces(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
	}

	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
			case 0: s = this.lista.get(indiceFil).getId(); break;
			case 1: s = getVerdes(this.lista.get(indiceFil)); break;
			case 2: s = getRojos(this.lista.get(indiceFil));break;
			default: assert (false);
		}
		return s;
	}
	
	public String getVerdes(CruceGenerico<?> cruce) {
		String s = "";
		for (int i = 0 ; i< cruce.getCarreterasEntrantes().size();i++) 
			if(cruce.getCarreterasEntrantes().get(i).getSemaforo() ) {
				s = cruce.getCarreterasEntrantes().get(i).toString();
				return  s;
			}		
		return "[]";
	}
	
	public String getRojos(CruceGenerico<?> cruce) {
		String s = "";
		for (int i = 0 ; i< cruce.getCarreterasEntrantes().size();i++) 
			if(!cruce.getCarreterasEntrantes().get(i).getSemaforo() ) {
				s = cruce.getCarreterasEntrantes().get(i).toString();
				return  s;
			}		
		return "[]";
	}
	
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getCruces();
		this.fireTableStructureChanged();
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getCruces();
		this.fireTableStructureChanged();
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getCruces();
		this.fireTableStructureChanged();
	}
}
