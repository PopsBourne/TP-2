package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;

@SuppressWarnings("serial")
public class StatusBar extends JPanel implements SimulatorObserver {

	private JLabel _currTime; // for current time
	private JLabel _currLaws; // for gravity laws
	private JLabel _numOfBodies; // for number of bodies

	StatusBar(Controller ctrl) {
		this._currTime = new JLabel("Time: " + 0);
		this._numOfBodies = new JLabel("Bodies: " + 0);
		this._currLaws = new JLabel("Laws: " + " ");
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		this.add(_currTime);
		this.add(Box.createHorizontalStrut(40));
		this.add(_numOfBodies);
		this.add(Box.createHorizontalStrut(40));
		this.add(_currLaws);
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_numOfBodies.setText("Bodies: " + String.valueOf(bodies.size()));
				_currTime.setText("Time: " + time);
			}
		});
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_numOfBodies.setText(String.valueOf(0));
				_currTime.setText(String.valueOf("Time: " + time));
				_currLaws.setText("Laws: " + " ");
			}
		});
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_numOfBodies.setText("Bodies: " + String.valueOf(bodies.size()));
			}
		});
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_currTime.setText("Time: " + String.valueOf(time));
			}
		});
	}

	@Override
	public void onDeltaTimeChanged(double dt) {

	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_currLaws.setText("Laws: " + gLawsDesc);
			}
		});
	}

}
