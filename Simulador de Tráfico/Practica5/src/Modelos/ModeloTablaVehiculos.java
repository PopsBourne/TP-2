package Modelos;

import java.util.List;

import Observadores.ModeloTabla;
import eventos.Controlador;
import eventos.tiposeventos.Evento;
import excepciones.ErrorDeSimulacion;
import simulador.MapaCarreteras;
import simulador.vehiculos.Vehiculo;

@SuppressWarnings("serial")
public class ModeloTablaVehiculos extends ModeloTabla<Vehiculo> {

	public ModeloTablaVehiculos(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
	}

	@Override // necesario para que se visualicen los datos
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
			case 0: s = this.lista.get(indiceFil).getId(); break;
			case 1: s = this.lista.get(indiceFil).getCarretera(); break;
			case 2: s = this.lista.get(indiceFil).getLocalizacion(); break;
			case 3: s = this.lista.get(indiceFil).getVelocidadActual(); break;
			case 4: s = this.lista.get(indiceFil).getKilometraje(); break;
			case 5: s = this.lista.get(indiceFil).getTiempoAveria(); break;
			case 6: s = this.lista.get(indiceFil).getItinerario(); break;
			default: assert (false);
		}
		return s;
	}
	
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getVehiculos();
		this.fireTableStructureChanged();
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getVehiculos();
		this.fireTableStructureChanged();
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getVehiculos();
		this.fireTableStructureChanged();
	}
}