package Paneles;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

import Observadores.ObservadorSimuladorTrafico;
import Observadores.VentanaPrincipal;
import eventos.Controlador;
import eventos.tiposeventos.Evento;
import excepciones.ErrorDeSimulacion;
import excepciones.Existe;
import excepciones.NoExiste;
import excepciones.ParseException;
import simulador.MapaCarreteras;

@SuppressWarnings("serial")
public class ToolBar extends JToolBar implements ObservadorSimuladorTrafico {

	private JSpinner steps;
	private JTextField time;
	private VentanaPrincipal mainWindow;

	public ToolBar(VentanaPrincipal mainWindow, Controlador controlador) {
		super();
		this.mainWindow = mainWindow;
		controlador.addObserver(this);
		cargarEventos(mainWindow);
		guardar(mainWindow);
		limpiar(mainWindow);
		checkIn(controlador);
		ejecuta(controlador);
		reset(controlador);
		spinner();
		tiempo();
		informes(mainWindow);
		limpiarInforme(mainWindow);
		guardarInforme(mainWindow);
		exit(mainWindow);
	}

	// Cargar Fichero *
	public void cargarEventos(VentanaPrincipal mainWindow) {
		JButton botonCargar = new JButton();
		botonCargar.setToolTipText("Carga un fichero de eventos");
		botonCargar.setIcon(new ImageIcon(Utils.loadImage("src/codigo/resources/iconos/open.png")));
		botonCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainWindow.cargaFichero();
			}
		});
		this.add(botonCargar);
	}

	// Guardar * (cuando se ejecuta)
	public void guardar(VentanaPrincipal mainWindow) {
		JButton botonSave = new JButton();
		botonSave.setToolTipText("Guarda un fichero de eventos");
		botonSave.setIcon(new ImageIcon(Utils.loadImage("src/codigo/resources/iconos/save.png")));
		botonSave.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					mainWindow.salvarFichero();
				} catch (Exception err) {
					err.printStackTrace();
				}
			}
		});
		this.add(botonSave);
	}

	// Guardar report
	public void guardarInforme(VentanaPrincipal mainWindow) {
		JButton botonSaveRe = new JButton();
		botonSaveRe.setToolTipText("Guarda informes");
		botonSaveRe.setIcon(new ImageIcon(Utils.loadImage("src/codigo/resources/iconos/save_report.png")));
		botonSaveRe.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					mainWindow.salvarInforme();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		this.add(botonSaveRe);
	}

	// Limpiar
	public void limpiar(VentanaPrincipal mainWindow) {
		JButton botonClear = new JButton();
		botonClear.setToolTipText("Limpia");
		botonClear.setIcon(new ImageIcon(Utils.loadImage("src/codigo/resources/iconos/clear.png")));
		botonClear.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					mainWindow.limpiarF();
				} catch (Exception err) {

				}
			}
		});
		this.add(botonClear);
	}

	// Ejecuta
	public void ejecuta(Controlador controlador) {
		JButton botonPlay = new JButton();
		botonPlay.setToolTipText("Ejecuta");
		botonPlay.setIcon(new ImageIcon(Utils.loadImage("src/codigo/resources/iconos/play.png")));
		botonPlay.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					controlador.ejecuta(getSteps());
				} catch (ParseException | NoExiste | Existe | IOException | ErrorDeSimulacion e1) {
					e1.printStackTrace();
				}
			}
		});
		this.add(botonPlay);
	}

	// Limpiar Report
	public void limpiarInforme(VentanaPrincipal mainWindow) {
		JButton botonClean = new JButton();
		botonClean.setToolTipText("Limpia informes");
		botonClean.setIcon(new ImageIcon(Utils.loadImage("src/codigo/resources/iconos/delete_report.png")));
		botonClean.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainWindow.limpiarI();
			}
		});
		this.add(botonClean);
	}

	// Check-In (calendario)
	public void checkIn(Controlador controlador) {
		JButton botonCheckIn = new JButton();
		botonCheckIn.setToolTipText("Carga los eventos al simulador");
		botonCheckIn.setIcon(new ImageIcon(Utils.loadImage("src/codigo/resources/iconos/events.png")));
		botonCheckIn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					controlador.reinicia();
					byte[] contenido = mainWindow.getTextoEditorEventos().getBytes();
					controlador.cargaEventos(new ByteArrayInputStream(contenido));
				} 
				catch (ErrorDeSimulacion err) {
					err.printStackTrace();
				}
				mainWindow.checkin();
			}
		});
		this.add(botonCheckIn);
	}

	// Spinner *
	public void spinner() {
		this.add(new JLabel(" Pasos: "));
		this.steps = new JSpinner(new SpinnerNumberModel(5, 1, 1000, 1));
		this.steps.setToolTipText("pasos a ejecutar: 1-1000");
		this.steps.setMaximumSize(new Dimension(70, 70));
		this.steps.setMinimumSize(new Dimension(70, 70));
		this.steps.setValue(1);
		this.add(steps);
	}

	// Tiempo *
	public void tiempo() {
		this.add(new JLabel(" Tiempo: "));
		this.time = new JTextField("0", 5);
		this.time.setToolTipText("Tiempo actual");
		this.time.setMaximumSize(new Dimension(70, 70));
		this.time.setMinimumSize(new Dimension(70, 70));
		this.time.setEditable(false);
		this.add(this.time);
	}

	// Informes
	public void informes(VentanaPrincipal mainWindow) {
		// OPCIONAL
		JButton botonGeneraReports = new JButton();
		botonGeneraReports.setToolTipText("Genera informes");
		botonGeneraReports.setIcon(new ImageIcon(Utils.loadImage("src/codigo/resources/iconos/report.png")));
		botonGeneraReports.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.generaInformes();
			}
		});
		this.add(botonGeneraReports);
	}

	// Reset *
	public void reset(Controlador controlador) {
		JButton botonReset = new JButton();
		botonReset.setToolTipText("Reset");
		botonReset.setIcon(new ImageIcon(Utils.loadImage("src/codigo/resources/iconos/reset.png")));
		botonReset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.reset();
			}
		});
		this.add(botonReset);
	}

	// Exit *
	public void exit(VentanaPrincipal mainWindow) {
		JButton botonExit = new JButton();
		botonExit.setToolTipText("Exit");
		botonExit.setIcon(new ImageIcon(Utils.loadImage("src/codigo/resources/iconos/exit.png")));
		botonExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.quit();
			}
		});
		this.add(botonExit);
	}

	public int getSteps() {
		return (int) steps.getValue();
	}
	
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		if(!eventos.isEmpty())
		this.time.setText("" + tiempo);
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.steps.setValue(1);
		this.time.setText("0");
	}
}