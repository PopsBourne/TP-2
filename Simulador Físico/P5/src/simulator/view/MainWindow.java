package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	Controller _ctrl;
	private Viewer viewer;
	private BodiesTable bodiesTable;
	private ControlPanel ctrl_Panel;

	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		this.setLocation(350, 30);
		this.setSize(new Dimension(700, 700));
		return mainPanel;
	}

	private void addControlPanel(JPanel mainPanel) {
		this.ctrl_Panel = new ControlPanel(_ctrl);
		ctrl_Panel.setLayout(new FlowLayout());
		ctrl_Panel.setBorder(BorderFactory.createEmptyBorder());
		mainPanel.add(this.ctrl_Panel, BorderLayout.PAGE_START);
	}

	private void initGUI() {

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {

			public void windowActivated(WindowEvent arg0) {
			}

			public void windowClosed(WindowEvent arg0) {
			}

			public void windowClosing(WindowEvent arg0) {
				ctrl_Panel.quit();
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

		JPanel mainPanel = this.createMainPanel();
		setContentPane(mainPanel);

		this.addControlPanel(mainPanel);

		// *********************CENTER PANEL*********************
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 2));
		mainPanel.add(centerPanel, BorderLayout.CENTER);

		// BODIES TABLE
		this.bodiesTable = new BodiesTable(this._ctrl);
		JPanel superiorPanel = new JPanel();
		superiorPanel.setLayout(new GridLayout(1, 1));
		superiorPanel.setBackground(Color.white);
		superiorPanel.add(bodiesTable);

		centerPanel.add(superiorPanel);

		// GRAPHYC PANEL
		this.viewer = new Viewer(this._ctrl);
		JPanel inferiorPanel = new JPanel();
		inferiorPanel.setLayout(new GridLayout(1, 1));
		inferiorPanel.setBackground(Color.white);
		inferiorPanel.add(viewer);
		centerPanel.add(inferiorPanel);

		mainPanel.add(centerPanel, BorderLayout.CENTER);
		// ******************************************************

		StatusBar statusBar = new StatusBar(_ctrl);
		statusBar.setBackground(Color.LIGHT_GRAY);
		mainPanel.add(statusBar, BorderLayout.PAGE_END);

		this.setVisible(true);
	}

}
