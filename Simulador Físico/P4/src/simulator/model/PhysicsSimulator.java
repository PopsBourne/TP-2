package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator {

	private double time;
	private GravityLaws gravityLaws;
	private List<Body> list;
	private double dt;

	public PhysicsSimulator(double dt, GravityLaws gravityLaws) {
		this.time = 0.0;
		this.gravityLaws = gravityLaws;
		this.dt = dt;
		this.list = new ArrayList<Body>();
	}

	public void advance() throws IllegalArgumentException {
		// aplica las leyes de la gravedad a los cuerpos
		gravityLaws.apply(list);
		for (int i = 0; i < list.size(); i++)
			list.get(i).move(this.dt);
		this.time += this.dt;
	}

	public void addBody(Body b) throws IllegalArgumentException {
		if (!list.contains(b)) list.add(b);
	}

	public String toString() {
		String s = "";
		s += "{ \"time\": " + this.time + ", \"bodies\": [";
		for (int i = 0; i < this.list.size(); i++) {
			s += this.list.get(i).toString() /*+ ", "*/;
			if(i!=this.list.size() - 1)
				s+=", ";
			else s+= "] ";
		}
		s += "}";//,
		return s;
	}

	// public static void main(String[] args) {
	// ArrayList<Body> bodyBuilders = new ArrayList<>();
	// Body b1 = new Body("b1",new Vector(0), new Vector(0), new Vector(0), 4.0);
	// bodyBuilders.add(b1);
	// }

}
