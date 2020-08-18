package simulator.view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements SimulatorObserver {

	private Controller _ctrl;
	private JSpinner steps;
	private JTextField time;
	private JSpinner delay;
	private String info;
	private volatile Thread _thread;

	JButton openButton;
	JButton gravityButton;
	JButton exitButton;
	JButton stopButton;
	JButton runButton;

	private JFileChooser fc;

	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_ctrl.addObserver(this);

		this.openButton = new JButton();
		this.gravityButton = new JButton();
		this.exitButton = new JButton();
		this.stopButton = new JButton();
		this.runButton = new JButton();
		this.info = null;
		this._thread = null;
		this.fc = null;

		initGUI();
	}

	private void initGUI() {

		JToolBar toolBar = new JToolBar();
		// toolBar.setSize(new Dimension(950, 500));

		// Cargar fichero
		openButton.setToolTipText("Carga un fichero de eventos");
		openButton.setIcon(new ImageIcon(loadImage("resources/icons/open.png")));
		// openButton.setBounds(0, 0, 5, 5);
		// openButton.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
		openButton.setPreferredSize(new Dimension(36, 36));
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargaFichero();
			}
		});

		// Seleccionar Ley de Gravedad
		gravityButton.setToolTipText("Selecciona la ley de gravedad");
		gravityButton.setIcon(new ImageIcon(loadImage("resources/icons/physics.png")));
		gravityButton.setPreferredSize(new Dimension(36, 36));
		gravityButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				popUpMenu();
			}
		});

		// Salir
		exitButton.setToolTipText("Exit");
		exitButton.setIcon(new ImageIcon(loadImage("resources/icons/exit.png")));
		exitButton.setPreferredSize(new Dimension(36, 36));
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				quit();
			}
		});

		// Detener ejecucion
		stopButton.setToolTipText("Detiene la ejecución");
		stopButton.setIcon(new ImageIcon(loadImage("resources/icons/stop.png")));
		stopButton.setPreferredSize(new Dimension(36, 36));
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (_thread != null) {
					_thread.interrupt();
					_thread = null;
				}
			}
		});

		// Steps
		this.steps = new JSpinner(new SpinnerNumberModel(5, 0, 10000, 100));
		this.steps.setToolTipText("pasos a ejecutar: 1-10000");
		this.steps.setMaximumSize(new Dimension(70, 70));
		this.steps.setMinimumSize(new Dimension(70, 70));
		this.steps.setValue(0);

		// Time
		this.time = new JTextField("0", 5);
		this.time.setToolTipText("Tiempo actual");
		this.time.setMaximumSize(new Dimension(70, 70));
		this.time.setMinimumSize(new Dimension(70, 70));
		this.time.setEditable(true);

		// Delay
		this.delay = new JSpinner(new SpinnerNumberModel(5, 0, 1000, 1));
		this.delay.setToolTipText("delay: 0-1000");
		this.delay.setMaximumSize(new Dimension(70, 70));
		this.delay.setMinimumSize(new Dimension(70, 70));
		this.delay.setValue(0);

		// Desactivar botones
		runButton.setToolTipText("Desactiva todos los botones excepto STOP");
		runButton.setIcon(new ImageIcon(loadImage("resources/icons/run.png")));
		runButton.setPreferredSize(new Dimension(36, 36));
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					if (info != null) {
						if (Double.parseDouble(time.getText()) > 0) {

							_ctrl.setDeltaTime(Double.parseDouble(time.getText()));
							int n = Integer.parseInt(steps.getValue().toString());
							long d = Long.parseLong(delay.getValue().toString());

							if (_thread == null) {
								_thread = new Thread(new Runnable() {
									@Override
									public void run() {
										desactivarBotones();
										run_sim(n, d);
										activarBotones();
										_thread = null;
									}
								});
								_thread.start();
							}
						} else
							muestraDialogoError("El valor 'time' debe ser > 0");
					} else
						muestraDialogoError("Debes seleccionar una ley de la gravedad");

				} catch (Exception ex) {
					muestraDialogoError(ex.getMessage());
				}

			}

		});

		// ADD
		toolBar.add(openButton);
		toolBar.add(gravityButton);
		toolBar.add(runButton);
		toolBar.add(stopButton);
		toolBar.add(new JLabel(" Delay: "));
		toolBar.add(delay);
		toolBar.add(new JLabel(" Steps: "));
		toolBar.add(steps);
		toolBar.add(new JLabel(" Time: "));
		toolBar.add(this.time);
		toolBar.add(Box.createHorizontalStrut(150));
		toolBar.add(exitButton);
		this.add(toolBar);
	}

	private void run_sim(int n, long d) {
		while (n > 0 && !this._thread.isInterrupted()) {
			try {
				_ctrl.run(1);
				Thread.sleep(d * 1000);
			} catch (Exception e) {
				this.muestraDialogoError(e.getMessage());
				desactivarBotones();
				return;
			}
			n--;
		}
		stopButton.setEnabled(true);
	}

	// LÓGICA BOTONES

	public void desactivarBotones() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gravityButton.setEnabled(false);
				openButton.setEnabled(false);
				exitButton.setEnabled(false);
			}
		});
	}

	public void activarBotones() {
		// INVOCARLO EN LA HEBRA DE SWING: invokeLater
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gravityButton.setEnabled(true);
				openButton.setEnabled(true);
				exitButton.setEnabled(true);
			}
		});
	}

	private void cargaFichero() {
		this.fc = new JFileChooser();
		int returnVal = this.fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.info = null;
			File file = this.fc.getSelectedFile();
			try {
				InputStream is = new FileInputStream(file);
				this._ctrl.reset();
				this._ctrl.loadBodies(is);
			} catch (Exception e) {
				this.muestraDialogoError("Error durante la lectura del fichero: " + e.getMessage());
			}
		}

	}

	private void popUpMenu() {
		List<JSONObject> l = this._ctrl.getGravityLaws().getInfo();
		String[] gravityLaws = new String[l.size()];
		JSONObject[] opt = new JSONObject[l.size()];
		int pos = 0;

		for (int i = 0; i < l.size(); i++) {
			gravityLaws[i] = l.get(i).getString("desc") + " (" + l.get(i).getString("type") + ")";
			opt[i] = l.get(i);
		}

		JFrame frame = new JFrame();
		info = (String) JOptionPane.showInputDialog(frame, "Select gravity laws to be used", "Gravity Law Selector",
				JOptionPane.QUESTION_MESSAGE, null, gravityLaws, gravityLaws[0]);

		if (info != null) {
			if (info.charAt(0) == 'N') {
				if (info.charAt(1) == 'o')
					pos = 2;
			} else
				pos = 1;

			this._ctrl.setGravityLaws(opt[pos]);
		}
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_ctrl.setDeltaTime(dt);
			}
		});
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// modificar el valor del campo de texto
	//	this.time.setText(String.valueOf(dt));
	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {

	}

	public Image loadImage(String path) {
		return Toolkit.getDefaultToolkit().createImage(path);
	}

	public void muestraDialogoError(String string) {
		JOptionPane.showMessageDialog(null, string);
	}

	public void quit() {
		int n = JOptionPane.showOptionDialog(new JFrame(), "Are sure you want to quit?", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (n == 0) {
			this._ctrl.exit();
		}
	}
}
