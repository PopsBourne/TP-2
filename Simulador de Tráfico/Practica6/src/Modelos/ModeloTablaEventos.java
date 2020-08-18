package Modelos;

import java.util.ArrayList;
import java.util.List;

import Observadores.ModeloTabla;
import eventos.Controlador;
import eventos.tiposeventos.Evento;
import excepciones.ErrorDeSimulacion;
import simulador.MapaCarreteras;

@SuppressWarnings("serial")
public class ModeloTablaEventos extends ModeloTabla<Evento> {

	public ModeloTablaEventos(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
	}

	@Override // necesario para que se visualicen los datos
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
			case 0: s = indiceFil; break;
			case 1: s = this.lista.get(indiceFil).getTiempo(); break;
			case 2: s = this.lista.get(indiceFil).toString(); break;
			default: assert (false);
		}
		return s;
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		List<Evento> copia = new ArrayList<Evento>();
		for(int i = 0 ; i< eventos.size();i++) {
			if(eventos.get(i).getTiempo() >= tiempo)
				copia.add(eventos.get(i));
		}
		this.lista = copia;
		this.fireTableStructureChanged();
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = eventos;
		this.fireTableStructureChanged();
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		List<Evento> copia = new ArrayList<Evento>();
		this.lista = copia;
		this.fireTableStructureChanged();
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		this.lista = eventos;
		this.fireTableStructureChanged();
	}
}
