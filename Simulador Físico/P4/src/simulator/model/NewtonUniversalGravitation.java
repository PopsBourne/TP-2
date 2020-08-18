package simulator.model;

import java.util.List;

import simulator.misc.Vector;

public class NewtonUniversalGravitation implements GravityLaws {

	//private Vector F;
	private Vector Fij;
	private double fij;
	private Vector dij;
//	private boolean ok;

	static private final double G = 6.67E-11;

	public NewtonUniversalGravitation() {
		super();
	}

	@Override
	public void apply(List<Body> bodies) {
		Vector F = new Vector(bodies.get(1).getPosition().dim());
		for (int i = 0; i < bodies.size(); i++) {
			bodies.get(i).setAcceleration(new Vector(bodies.get(i).getPosition().dim()));
			for (int j = 0; j < bodies.size(); j++) {

				if (i != j) {
					F = F.plus(force(bodies.get(i), bodies.get(j)));
					
					if (bodies.get(i).getMass() == 0) {
						int lengthA = bodies.get(i).getAcceleration().dim();
						bodies.get(i).setAcceleration(new Vector(lengthA));

						int lengthV = bodies.get(i).getVelocity().dim();
						bodies.get(i).setVelocity(new Vector(lengthV));

					} else
						bodies.get(i).setAcceleration(bodies.get(i).getAcceleration().plus(F.scale(1 / bodies.get(i).getMass()))); // F*1/m
				}
			}
		}
	}

	private Vector force(Body a, Body b) {
		double m = a.getMass() * b.getMass();
		double dist = Math.pow(a.getPosition().distanceTo(b.getPosition()), 2);

		fij = G * (m / dist);
		dij = b.getPosition().direction().minus(a.getPosition().direction());
		Fij = dij.scale(fij);
		return Fij;

	}
	//
	// //PRUEBA
	// public static void main(String[] args) {
	// List<Body> listB = new ArrayList<Body>();
	// Body b = new Body("b1", new Vector(new double[] {0,1}), new Vector(new
	// double[] {0.5,1}), new Vector(new double[] {0.5,0.8}), 0);
	// Body b1 = new Body("b2", new Vector(new double[] {0,2}), new Vector(new
	// double[] {0.5,1.2}), new Vector(new double[] {0.1,0.8}), 2);
	// listB.add(b);
	// listB.add(b1);
	// System.out.println(listB);
	// NewtonUniversalGravitation g = new NewtonUniversalGravitation();
	// g.apply(listB);
	// b.move(10);
	// b1.move(5);
	// System.out.println("\n");
	// System.out.println(listB);
	// }
}
