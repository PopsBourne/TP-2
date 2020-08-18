package simulator.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import simulator.view.SimulatorObserver;

public class PhysicsSimulator {

	private double time;
	private GravityLaws gravityLaws;
	private List<Body> list;
	private double dt;

	private List<SimulatorObserver> observer;

	public PhysicsSimulator(double dt, GravityLaws gravityLaws) {
		this.time = 0.0;
		this.gravityLaws = gravityLaws;
		this.dt = dt;
		this.list = new ArrayList<Body>();
		this.observer = new ArrayList<SimulatorObserver>();
	}

	public void advance() throws IllegalArgumentException {
		// aplica las leyes de la gravedad a los cuerpos
		gravityLaws.apply(list);

		for (int i = 0; i < list.size(); i++)
			list.get(i).move(this.dt);
		this.time += this.dt;

		for (int i = 0; i < this.observer.size(); i++) {
			this.observer.get(i).onAdvance(list, this.time);// i/time
		}
	}

	public void addObserver(SimulatorObserver o) {// añade o a la lista de observadores, si es que no está ya en ella
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (!observer.contains(o)) {
					observer.add(o);
					o.onRegister(list, time, dt, gravityLaws.toString());
				}
			}
		});
	}

	public void addBody(Body b) throws IllegalArgumentException {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				list.add(b);
				for (SimulatorObserver o : observer)
					o.onBodyAdded(list, b);
			}
		});
	}

	public void reset() {// vacía la lista de cuerpos y pone el tiempo a 0,0.
		list.clear();
		this.time = 0.0;

		for (int i = 0; i < this.observer.size(); i++) {
			this.observer.get(i).onReset(list, time, dt, gravityLaws.toString());
		}
	}

	public void setDeltaTime(double dt) throws IllegalArgumentException {
		this.dt = dt;

		for (int i = 0; i < this.observer.size(); i++) {
			this.observer.get(i).onDeltaTimeChanged(dt);
		}
	}

	public void setGravityLaws(GravityLaws gravityLaws) throws IllegalArgumentException {
		this.gravityLaws = gravityLaws;

		for (SimulatorObserver o : this.observer)
			o.onGravityLawChanged(gravityLaws.toString());
	}

	public double getDt() {
		return dt;
	}

	public String toString() {
		String s = "";
		s += "[{ time: " + this.time + ", bodies: ";
		for (int i = 0; i < this.list.size(); i++)
			s += this.list.get(i).toString() + ", ";
		s += "}]";
		return s;
	}
}
