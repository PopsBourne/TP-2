package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.view.SimulatorObserver;

public class Controller {

	private PhysicsSimulator phySim;
	private Factory<Body> bodiesFactory;
	private Factory<GravityLaws> gravityLaws;
	
	public Controller(PhysicsSimulator phySim, Factory<Body> bodyBuilder, Factory<GravityLaws> gravityLaws) {
		this.phySim = phySim;
		this.bodiesFactory = bodyBuilder;
		this.gravityLaws = gravityLaws;
	}

	public void loadBodies(InputStream in) {
		JSONObject jsonInupt = new JSONObject(new JSONTokener(in));
		JSONArray bodies = jsonInupt.getJSONArray("bodies");

		for (int i = 0; i < bodies.length(); i++)
			phySim.addBody(bodiesFactory.createInstance(bodies.getJSONObject(i)));
	}

	public void exit() {
		System.exit(0);
	}

	public void reset() {
		phySim.reset();
	}

	public void setDeltaTime(double dt) {
		phySim.setDeltaTime(dt);
	}

	public double getDeltaTime() {
		return phySim.getDt();
	}

	public void addObserver(SimulatorObserver o) {
		phySim.addObserver(o);
	}

	public void run(int n) {
		for (int i = 0; i < n; i++)
			phySim.advance();
	}

	public void run(int n, OutputStream out) throws IOException {
		out.write("{\"states\": [".getBytes());
		out.write(phySim.toString().getBytes());
		out.write(",".getBytes());

		for (int i = 0; i < n; i++) {
			phySim.advance();

			out.write(phySim.toString().getBytes());
			if (i != n - 1)
				out.write(",".getBytes());
		}

		out.write("]}".getBytes());
		out.close();
	}

	public Factory<GravityLaws> getGravityLaws() {//****
		return gravityLaws;
	}

	public void setGravityLaws(JSONObject info) {
		GravityLaws grLaws =  this.gravityLaws.createInstance(info);
		this.phySim.setGravityLaws(grLaws);
	}

}
