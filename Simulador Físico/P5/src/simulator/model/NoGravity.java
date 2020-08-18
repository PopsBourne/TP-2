package simulator.model;

import java.util.List;

public class NoGravity implements GravityLaws{


	public NoGravity() {
		super();
	}

	@Override
	public void apply(List<Body> bodies) {}

	@Override
	public String toString() {
		return "NoGravity";
	}

	
}
