package simulator.model;

import java.util.List;

public class FallingToCenterGravity implements GravityLaws {

	static private final double g = 9.81;

	public FallingToCenterGravity() {
		super();
	}

	@Override
	public void apply(List<Body> bodies) {
		for (int i = 0; i < bodies.size(); i++) {
			bodies.get(i).setAcceleration(bodies.get(i).getAcceleration().direction().scale(-g));// a = -g*di
		}
	}
}
