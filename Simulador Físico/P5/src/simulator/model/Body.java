package simulator.model;

import simulator.misc.Vector;

public class Body {

	protected String id;
	protected Vector v;
	protected Vector a;
	protected Vector p;
	protected double m;

	public Body(String id, Vector v, Vector p, double m) {
		this.id = id;
		
		this.v = v;
		this.a = new Vector(this.v.dim());
		this.p = p;
		this.m = m;
	}

	public String getId() {
		return this.id;
	}

	public Vector getVelocity() {
		return new Vector(v);
	}

	public Vector getAcceleration() {
		return new Vector(a);
	}

	public Vector getPosition() {
		return new Vector(p);
	}

	public double getMass() {
		return this.m;
	}
	
	public void setM(double m) {
		this.m = m;
	}

	void setVelocity(Vector v) {
		// hace una copia de v y se la asigna al vector de velocidad.
		this.v = new Vector(v);
	}

	void setAcceleration(Vector a) {
		// hace una copia de a y se la asigna al vector de aceleración.
		this.a = new Vector(a);
	}

	void setPosition(Vector p) {
		// hace una copia de p y se la asigna al vector de posición.
		this.p = new Vector(p);
	}

	
	void move(double t) throws IllegalArgumentException {// mueve el cuerpo durante t segundos utilizando los atributos del mismo.
//		Vector t2 = this.a.scale(t*t);//t*t
//		Vector v2 = t2.scale(0.5);//0.5+a+t2
//		Vector v3 = this.v.scale(t);//v*t
//		Vector suma = this.p.plus(v3.plus(v2));		
//		setPosition(suma);
		setPosition(this.p.plus(this.v.scale(t).plus(this.a.scale(Math.pow(t, 2)/2)))); //p + v*t + 1/2*a*t*t
		setVelocity(this.v.plus(this.a.scale(t)));
	}
	
	@Override
	public String toString() { // devuelve un string con la información del cuerpo en formato JSON:
	return " { " + "\"id\": " +"\""+ this.id + "\"" + ", \"mass\": " + this.m  + ", \"pos\": "  + this.p + ", \"vel\": "  + this.v + ", \"acc\": "  + this.a + " }"; 
	 }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Body other = (Body) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

	
//	//PRUEBA
//	public static void main(String[] args) {
//		Body b = new Body("b1", new Vector(new double[] {0,1}),  new Vector(new double[] {0.5,1}),  new Vector(new double[] {0.5,0.8}), 5.97E24);
//		System.out.println(b);
//		b.move(10);
//		System.out.println(b);
//	}
}
