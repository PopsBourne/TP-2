package simulator.model;

import simulator.misc.Vector;

public class MassLossingBody extends Body {

	private double lossFactor;
	private double lossFrequency;
	private double accumulatedTime;

	public MassLossingBody(String id, Vector v, Vector p, double m, double lossFactor, double lossFrequency) {
		super(id, v, p, m);
		this.accumulatedTime = 0.0;
		this.lossFrequency = lossFrequency;
		this.lossFactor = lossFactor;

	}

	@Override
	void move(double t) throws IllegalArgumentException {

		if (this.accumulatedTime <= t) {//while
			super.move(t);//lossFrequency
			this.m = m * (1 - this.lossFactor);
			this.accumulatedTime += t;
		}
		this.accumulatedTime = 0.0;

		// super.move(t);
		// if (this.accumulatedTime >= this.lossFrequency) {
		// this.setM(this.getMass() * (1 - this.lossFactor)); // m = m*(1-lossFactor)
		// this.accumulatedTime = 0.0;
		// } else
		// this.accumulatedTime += t;
	}

	@Override
	public String toString() { 
		return super.toString();
	}

	// //PRUEBA
	// public static void main(String[] args) {
	// MassLossingBody mlb = new MassLossingBody("b1", new Vector(new double[] { 0,
	// 1 }),
	// new Vector(new double[] { 0.5, 1 }), new Vector(new double[] { 0.5, 0.8 }),
	// 1, 0, 2);
	// System.out.println(mlb.toString());
	// }
}
