package Modelos;

import java.util.List;

import Observadores.ModeloTabla;
import eventos.Controlador;
import eventos.tiposeventos.Evento;
import excepciones.ErrorDeSimulacion;
import simulador.MapaCarreteras;
import simulador.carreteras.Carretera;

@SuppressWarnings("serial")
public class ModeloTablaCarreteras extends ModeloTabla<Carretera>{

	public ModeloTablaCarreteras(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
	}

	@Override // necesario para que se visualicen los datos
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
			case 0: s = this.lista.get(indiceFil).getId(); break;
			case 1: s = this.lista.get(indiceFil).getCruceOrigen(); break;
			case 2: s = this.lista.get(indiceFil).getCruceDestino();break;
			case 3: s = this.lista.get(indiceFil).getLongitud(); break;
			case 4: s = this.lista.get(indiceFil).getVelocidadMaxima();break;
			case 5: s = this.lista.get(indiceFil).getVehiculos();break;
			default: assert (false);
		}
		return s;
	}
	
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getCarreteras(); 
		this.fireTableStructureChanged();
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getCarreteras(); 
		this.fireTableStructureChanged();
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getCarreteras(); 
		this.fireTableStructureChanged();
	}
}
