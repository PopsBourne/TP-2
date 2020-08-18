package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;

@SuppressWarnings("serial")
public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	private List<Body> _bodies;
	static private final String[] columnNames = { "Id", "Mass", "Position", "Velocity", "Acceleration" };
	private String[] columnData;

	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
		this.columnData = new String[columnNames.length];
	}

	@Override
	public int getRowCount() {
		return _bodies.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column].toString();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		this.columnData[0] = _bodies.get(rowIndex).getId().toString();
		this.columnData[1] = String.valueOf(_bodies.get(rowIndex).getMass());
		this.columnData[2] = String.valueOf(_bodies.get(rowIndex).getPosition());
		this.columnData[3] = String.valueOf(_bodies.get(rowIndex).getVelocity());
		this.columnData[4] = String.valueOf(_bodies.get(rowIndex).getAcceleration());

		return this.columnData[columnIndex];
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_bodies = bodies;
				fireTableStructureChanged();
			}
		});
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_bodies = bodies;
				fireTableStructureChanged();
			}
		});
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_bodies = bodies;
				fireTableStructureChanged();
			}
		});
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_bodies = bodies;
				fireTableStructureChanged();
			}
		});
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		// ******************************************
	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		// TODO Auto-generated method stub
		// ******************************************
	}

}
