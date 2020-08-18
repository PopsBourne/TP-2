package Observadores;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Modelos.ModeloTablaCarreteras;
import Modelos.ModeloTablaCruces;
import Modelos.ModeloTablaEventos;
import Modelos.ModeloTablaVehiculos;
import Paneles.BarraMenu;
import Paneles.ComponenteMapa;
import Paneles.Jpanel;
import Paneles.PanelAreaTexto;
import Paneles.PanelEditorEventos;
import Paneles.PanelTabla;
import Paneles.ToolBar;
import eventos.Controlador;
import eventos.tiposeventos.Evento;
import excepciones.ErrorDeSimulacion;
import simulador.carreteras.Carretera;
import simulador.cruces.CruceGenerico;
import simulador.modelo.HiloSimulador;
import simulador.vehiculos.Vehiculo;

@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame  {

	private Controlador controlador;
	public static Border bordePorDefecto = BorderFactory.createLineBorder(Color.black, 2);

	// SUPERIOR PANEL
	static private final String[] columnIdEventos = { "#", "Tiempo", "Tipo" };

	private PanelAreaTexto panelEditorEventos;
	private PanelAreaTexto panelInformes;
	private PanelTabla<Evento> panelColaEventos;
	
	private HiloSimulador t = null;
	BarraMenu menubar ;

	// MENU AND TOOL BAR
	private JFileChooser fc;
	private ToolBar toolbar;

	// GRAPHIC PANEL
	private ComponenteMapa componenteMapa;

	// STATUS BAR (INFO AT THE BOTTOM OF THE WINDOW)
	private PanelBarraEstado panelBarraEstado;

	// INFERIOR PANEL
	static private final String[] columnIdVehiculo = { "ID", "Carretera", "Localizacion", "Vel.", "Km", "Tiempo. Ave.",
			"Itinerario" };
	static private final String[] columnIdCarretera = { "ID", "Origen", "Destino", "Longitud", "Vel. Max",
			"Vehiculos" };
	static private final String[] columnIdCruce = { "ID", "Verde", "Rojo" };

	private PanelTabla<Vehiculo> panelVehiculos;
	private PanelTabla<Carretera> panelCarreteras;
	private PanelTabla<CruceGenerico<?>> panelCruces;

	// REPORT DIALOG
	private DialogoInformes dialogoInformes; // opcional

	// MODEL PART - VIEW CONTROLLER MODEL
	private File ficheroActual;

	public VentanaPrincipal(String ficheroEntrada, Controlador ctrl) throws FileNotFoundException, ErrorDeSimulacion {
		super("Simulador de Trafico");
		this.controlador = ctrl;
		this.ficheroActual = ficheroEntrada != null ? new File(ficheroEntrada) : null;
		// ctrl.cargaEventos(new FileInputStream(ficheroActual));
		this.initGUI();
		// añadimos la ventana principal como observadora
		//ctrl.addObserver(this);
	}

	// *****************CREATE PANEL**********************************

	private JPanel createPanelCentral() {
		JPanel panelCentral = new Jpanel("");
		panelCentral.setLayout(new GridLayout(2, 1));// para colocar el panel superior e inferior
		return panelCentral;
	}

	private JPanel createPanelSuperior(JPanel panelCentral) {
		JPanel panelSuperior = new Jpanel("");
		panelSuperior.setLayout(new GridLayout(1, 3));

		this.panelEditorEventos = new PanelEditorEventos("", "", true, this);
		panelEditorEventos.setBorde("Eventos: " + (ficheroActual == null ? "" :ficheroActual.getName()));
		panelSuperior.add(panelEditorEventos);
		// leer fichero inicial
		if(ficheroActual != null)
			panelEditorEventos.setTexto(leeFichero(ficheroActual));

		this.panelColaEventos = new PanelTabla<Evento>("Cola Eventos: ",
				new ModeloTablaEventos(VentanaPrincipal.columnIdEventos, this.controlador));
		panelSuperior.add(panelColaEventos);

		this.panelInformes = new PanelInformes("", false, this.controlador);
		panelInformes.setBorde("Informes: ");
		panelSuperior.add(panelInformes);

		panelCentral.add(panelSuperior, BorderLayout.NORTH);

		return panelSuperior;
	}

	private JPanel creaPanelPrincipal() {
		JPanel panelPrincipal = new Jpanel("");
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.PAGE_AXIS));
		return panelPrincipal;
	}

	private JPanel createPanelInferior(JPanel panelCentral) {
		JPanel panelInferior = new Jpanel("");
		panelInferior.setLayout(new GridLayout(1, 2));
		panelInferior.setBackground(Color.white);

		JPanel panelInferiorIzquierdo = new Jpanel("");
		panelInferiorIzquierdo.setLayout(new GridLayout(3, 1));
		this.panelVehiculos = new PanelTabla<Vehiculo>("Vehiculos",
				new ModeloTablaVehiculos(VentanaPrincipal.columnIdVehiculo, this.controlador));
		this.panelCarreteras = new PanelTabla<Carretera>("Carreteras",
				new ModeloTablaCarreteras(VentanaPrincipal.columnIdCarretera, this.controlador));
		this.panelCruces = new PanelTabla<CruceGenerico<?>>("Cruces",
				new ModeloTablaCruces(VentanaPrincipal.columnIdCruce, this.controlador));
		panelInferiorIzquierdo.add(this.panelVehiculos);
		panelInferiorIzquierdo.add(this.panelCarreteras);
		panelInferiorIzquierdo.add(this.panelCruces);

		JPanel panelInferiorDerecho = new Jpanel("");
		panelInferiorDerecho.setLayout(new GridLayout(1, 1));
		panelInferiorDerecho.setBackground(Color.white);

		this.componenteMapa = new ComponenteMapa(this.controlador); // añadir un ScrollPane al panel
																	// inferior donde se coloca la componente
		panelInferiorDerecho.add(componenteMapa);

		panelInferior.add(panelInferiorIzquierdo);
		panelInferior.add(panelInferiorDerecho);
		panelCentral.add(panelInferior, BorderLayout.SOUTH);

		return panelInferior;
	}

	// **********************************************************

	private void addBarraEstado(JPanel panelPrincipal) {
		this.panelBarraEstado = new PanelBarraEstado("Bienvenido al simulador!", this.controlador);
		// se añade al panel principal (el que contiene al panel superior y al inferior)
		panelPrincipal.add(this.panelBarraEstado, BorderLayout.PAGE_START);
	}

	// ****************WINDOW LISTENER*********************
	public void quit() {
		int n = JOptionPane.showOptionDialog(new JFrame(), "Are sure you want to quit?", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (n == 0) {
			controlador.Salir();
		}
	}

	// *****************************************************

	private void addToolBar(JPanel panelPrincipal) {
		this.toolbar = new ToolBar(this, this.controlador);
		toolbar.setFloatable(false);
		panelPrincipal.add(this.toolbar, BorderLayout.PAGE_START);
	}

	private void initGUI() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {

			public void windowActivated(WindowEvent arg0) {
			}

			public void windowClosed(WindowEvent arg0) {
			}

			public void windowClosing(WindowEvent arg0) {
				quit();
			}

			public void windowDeactivated(WindowEvent arg0) {
			}

			public void windowDeiconified(WindowEvent arg0) {
			}

			public void windowIconified(WindowEvent arg0) {
			}

			public void windowOpened(WindowEvent arg0) {
			}
		});

		JPanel panelPrincipal = this.creaPanelPrincipal();
		this.setContentPane(panelPrincipal);

		// MENU
		menubar = new BarraMenu(this, this.controlador);
		this.setJMenuBar(menubar);

		// BARRA DE HERRAMIENTAS
		this.addToolBar(panelPrincipal);

		// PANEL QUE CONTIENE EL RESTO DE COMPONENTES
		// (Lo dividimos en dos paneles (superior e inferior)
		JPanel panelCentral = this.createPanelCentral();
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);

		// PANEL SUPERIOR
		this.createPanelSuperior(panelCentral);
		// PANEL INFERIOR
		this.createPanelInferior(panelCentral);

		// FILE CHOOSER
		this.fc = new JFileChooser();
		// REPORT DIALOG (OPCIONAL)
		this.dialogoInformes = new DialogoInformes(this, this.controlador);
		this.pack();
		this.setVisible(true);
		// BARRA DE ESTADO INFERIOR
		// (contiene una JLabel para mostrar el estado del simulador)
		this.addBarraEstado(panelPrincipal);
	}

	// BOTONES
	public void salvarFichero() throws IOException {
		String n = "";
		String s = "";
		try {
			if (this.fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				n = this.fc.getSelectedFile().getAbsolutePath();
				s = this.panelEditorEventos.getTexto();
				controlador.guardarF(n, s);
				this.muestraDialogo("Fichero guardado correctamente!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void salvarInforme() throws IOException {
		String n = "";
		String s = "";
		try {
			if (this.fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				n = this.fc.getSelectedFile().getAbsolutePath();
				s = this.panelInformes.getTexto();
				controlador.guardarR(n, s);
				this.muestraDialogo("Informe guardado correctamente!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void limpiarF() {
		this.panelEditorEventos.setTexto("");
		this.panelBarraEstado.setMensaje("Se ha limpiado los eventos!");
	}

	public void limpiarI() {
		this.panelInformes.setTexto("");
		this.panelBarraEstado.setMensaje("Se ha limpiado los informes!");
	}

	public void checkin(){
		this.panelBarraEstado.setMensaje("Eventos cargados al simulador!");
	}
	
	public void reset(){
		try {
			controlador.reinicia();
		} catch (ErrorDeSimulacion e) {
			e.printStackTrace();
		}
		this.panelBarraEstado.setMensaje("Simulador reseteado correctamente");
	}
	
	public void cargaFichero() {
		int returnVal = this.fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File fichero = this.fc.getSelectedFile();
			try {
				String s = leeFichero(fichero);
				this.controlador.reinicia();
				this.ficheroActual = fichero;
				this.panelEditorEventos.setTexto(s);
				this.panelEditorEventos.setBorde("Eventos: " + this.ficheroActual.getName());
				this.panelBarraEstado
						.setMensaje("Fichero " + fichero.getName() + " de eventos cargado en el editor");
			} catch (Exception e) {
				this.muestraDialogoError("Error durante la lectura del fichero: " + e.getMessage());
			}
		}
	}

	public void generaInformes() {
		this.panelInformes.setTexto(this.controlador.generaR());
		this.panelBarraEstado.setMensaje("Se han generado los informes!");
	}

	private String leeFichero(File fichero) {
		String total = "";
		String linea = "";
		try {
			FileReader fileReader = new FileReader(fichero);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((linea = bufferedReader.readLine()) != null) {
				total += linea + ("\n");
			}
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			this.ficheroActual = null;
			this.muestraDialogoError("Error durante la lectura del fichero: " + ex.getMessage());
		} catch (IOException ex) {
			this.ficheroActual = null;
			this.muestraDialogoError("Error durante la lectura del fichero: " + ex.getMessage());
		}
		return total;
	}

	public void muestraDialogoError(String string) {
		JOptionPane.showMessageDialog(null, string);
	}

	public void muestraDialogo(String string) {
		JOptionPane.showMessageDialog(null, string);
	}

	public int getSteps() {
		return controlador.getPasosSimulacion();
	}
	
	public int getDelay() {
		return this.toolbar.getDelay();
	}
	
	public void inserta(String string) {
		panelEditorEventos.inserta(string);
	}

	public String getTextoEditorEventos() {
		return panelEditorEventos.getTexto();
	}
	
	public void actualizar() throws Exception, ErrorDeSimulacion {
		// controlador.cargaEventos(new FileInputStream(ficheroActual));
		//panelColaEventos.getModelo().avanza(controlador.getPasosSimulacion(), controlador.getMapa(),
			//	controlador.getEventos());
		// panelColaEventos.addEvento(controlador.getPasosSimulacion(),
		// controlador.getMapa(), controlador.getEventos());
	
	}
	
	public Thread getThread() {
		return t;
	}
	
	public void desbloquearBotones() {
		Component[] c = toolbar.getComponents();
        for (int i=0; i < c.length; i++) {
        	if(c[i].getName()==null)
        		c[i].setEnabled(true);
        }
		menubar.desbloquearMenu();
	}
	
	public void bloquearBotones() {
		Component[] c = toolbar.getComponents();
        for (int i=0; i < c.length; i++) {
        	if(c[i].getName()==null)
        		c[i].setEnabled(false);
        }
		menubar.bloquearMenu();
	}
	
	public void ejecutarHilo() {
		if (t==null) {
			t = new HiloSimulador(this, controlador,toolbar.getSteps(), getDelay());	
			t.start();
		}
	}
	
	public void matarHilo() {
		t = null;
	}
}
