package Observadores;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;

import Paneles.InformationPanel;
import Paneles.PanelBotones;
import Paneles.PanelObjSim;
import eventos.Controlador;
import eventos.tiposeventos.Evento;
import excepciones.ErrorDeSimulacion;
import simulador.MapaCarreteras;
import simulador.carreteras.Carretera;
import simulador.cruces.CruceGenerico;
import simulador.vehiculos.Vehiculo;
import Observadores.VentanaPrincipal;

//TRANSPARENCIAS JDIALOG

public class DialogoInformes extends JDialog implements ObservadorSimuladorTrafico {

	private PanelBotones panelBotones;
	private PanelObjSim<Vehiculo> panelVehiculos;
	private PanelObjSim<Carretera> panelCarreteras;
	private PanelObjSim<CruceGenerico<?>> panelCruces;
	private JPanel panelPrincipal;//////////////////////////////?????????????????
	private VentanaPrincipal ventanaPrincipal;
	private Controlador controlador;
	public static char TECLALIMPIAR = 'c';
	
	public DialogoInformes(VentanaPrincipal ventanaPrincipal, Controlador controlador) {
		this.ventanaPrincipal=ventanaPrincipal;
		this.controlador=controlador;
	}

	private void initGUI() {
		// ...
		this.panelVehiculos = new PanelObjSim<Vehiculo>("Vehiculos");
		this.panelCarreteras = new PanelObjSim<Carretera>("Carreteras");
		this.panelCruces = new PanelObjSim<CruceGenerico<?>>("Cruces");
		this.panelBotones = new PanelBotones(this);
		InformationPanel panelInfo = new InformationPanel();
		panelPrincipal.add(panelInfo, BorderLayout.PAGE_START);
		// ...
	}

	public void mostrar() {
		this.setVisible(true);
	}

	private void setMapa(MapaCarreteras mapa) {
		this.panelVehiculos.setList(mapa.getVehiculos());
		this.panelCarreteras.setList(mapa.getCarreteras());
		this.panelCruces.setList(mapa.getCruces());
	}

	public List<Vehiculo> getVehiculosSeleccionados() {
		return this.panelVehiculos.getSelectedItems();
	}

	public List<Carretera> getCarreterasSeleccionadas() {
		return this.panelCarreteras.getSelectedItems();
	}

	public List<CruceGenerico<?>> getCrucesSeleccionados() {
		return this.panelCruces.getSelectedItems();
	}

	// ...
	
	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.setMapa(mapa);
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.setMapa(mapa);
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.setMapa(mapa);
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub

	}

}