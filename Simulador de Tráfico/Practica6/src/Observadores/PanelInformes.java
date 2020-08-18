package Observadores;

import java.util.List;

import Paneles.PanelAreaTexto;
import eventos.Controlador;
import eventos.tiposeventos.Evento;
import excepciones.ErrorDeSimulacion;
import simulador.MapaCarreteras;

@SuppressWarnings("serial")
public class PanelInformes extends PanelAreaTexto implements ObservadorSimuladorTrafico {

	public PanelInformes(String titulo, boolean editable, Controlador ctrl) { 
		super(titulo, editable); 
		ctrl.addObserver(this); // se añade como observador 
		}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.setTexto("");
	} 
}
