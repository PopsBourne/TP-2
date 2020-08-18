package Paneles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import Observadores.VentanaPrincipal;
import eventos.Controlador;
import excepciones.ErrorDeSimulacion;
import excepciones.Existe;
import excepciones.NoExiste;
import excepciones.ParseException;

public class BarraMenu extends JMenuBar {
	private static final long serialVersionUID = 1L;

	public BarraMenu(VentanaPrincipal mainWindow, Controlador controlador) {
		// MANEJO DE FICHEROS
		JMenu menuFicheros = new JMenu("Archivos");
		this.add(menuFicheros);
		this.creaMenuFicheros(menuFicheros, mainWindow);
		// SIMULADOR
		JMenu menuSimulador = new JMenu("Simulador");
		this.add(menuSimulador);
		this.creaMenuSimulador(menuSimulador, controlador, mainWindow);
		// INFORMES
		JMenu menuReport = new JMenu("Informes");
		this.add(menuReport);
		this.creaMenuInformes(menuReport, mainWindow);
	}

	// *************************************************FILE*******************************************************
	private void creaMenuFicheros(JMenu menu, VentanaPrincipal mainWindow) {
		JMenuItem cargar = new JMenuItem("Cargar Eventos");
		JMenuItem salvar = new JMenuItem("Guardar Eventos");
		JMenuItem salvarInformes = new JMenuItem("Guardar Informes");
		JMenuItem salir = new JMenuItem("Exit");

		// CARGAR
		cargar.setMnemonic(KeyEvent.VK_L);
		cargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		cargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					mainWindow.cargaFichero();
					mainWindow.actualizar();
				} catch (Exception o) {
					mainWindow.muestraDialogoError("Error durante la lectura del fichero: " + o.getMessage());
				}
			}
		});

		// SALVAR FICHERO
		salvar.setMnemonic(KeyEvent.VK_L);
		salvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		salvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					mainWindow.salvarFichero();
				} catch (Exception e1) {
					mainWindow.muestraDialogoError("Error en el proceso de guardado del fichero: " + e1.getMessage());
				}
			}
		});

		// SALVAR INFORME
		salvarInformes.setMnemonic(KeyEvent.VK_L);
		salvarInformes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		salvarInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					 mainWindow.salvarInforme();
				} catch ( Exception e1) {
					mainWindow.muestraDialogoError("Error en el proceso de guardado del informe: " + e1.getMessage());
				}
			}
		});

		// SALIR
		salir.setMnemonic(KeyEvent.VK_L);
		salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		salir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					mainWindow.quit();
				} catch ( Exception e1) {
					mainWindow.muestraDialogoError("Se ha producido un error al intentar salir ");
				}
			}
		});
		menu.add(cargar);
		menu.add(salvar);
		menu.addSeparator();
		menu.add(salvarInformes);
		menu.addSeparator();
		menu.add(salir);
	}
	// ***************************************************SIMULATOR************************************************

	private void creaMenuSimulador(JMenu menuSimulador, Controlador controlador, VentanaPrincipal mainWindow) {

		JMenuItem ejecuta = new JMenuItem("Run");
		JMenuItem reinicia = new JMenuItem("Reset");
		JMenuItem redirige = new JMenuItem("Redirect Output"); 

		// RUN 
		ejecuta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int pasos = mainWindow.getSteps();
				try {
//					byte[] contenido = mainWindow.getTextoEditorEventos().getBytes();
//						controlador.cargaEventos(new ByteArrayInputStream(contenido));
					controlador.ejecuta(pasos);
				} catch ( ParseException | NoExiste | Existe | IOException e1) {
					e1.printStackTrace();
				} catch (ErrorDeSimulacion e1) {
					e1.printStackTrace();
				}
			}
		});

		// RESET
		reinicia.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					controlador.reinicia();
				} catch (ErrorDeSimulacion e) {
					e.printStackTrace();
				}
			}
		});

		// REDIRECT
		redirige.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		menuSimulador.add(ejecuta);
		menuSimulador.add(reinicia);
		menuSimulador.add(redirige);
	}
	// ***********************************************REPORTS******************************************************

	private void creaMenuInformes(JMenu menuReport, VentanaPrincipal mainWindow) {
		JMenuItem generaInformes = new JMenuItem("Generar");
		JMenuItem limpiaAreaInformes = new JMenuItem("Limpiar");

		// GENERATE
		generaInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.generaInformes();
			}
		});
		// CLEAR
		limpiaAreaInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.limpiarI();
			}
		});
		menuReport.add(generaInformes);
		menuReport.add(limpiaAreaInformes);
	}
}