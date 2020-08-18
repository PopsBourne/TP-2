package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.PhysicsSimulator;

public class Controller {

	private PhysicsSimulator phySim;
	private Factory<Body> bodiesFactory;

	public Controller(PhysicsSimulator phySim, Factory<Body> bodyBuilder) {
		this.phySim = phySim;
		this.bodiesFactory = bodyBuilder;
	}

	public void loadBodies(InputStream in) {
		JSONObject jsonInupt = new JSONObject(new JSONTokener(in));
		JSONArray bodies = jsonInupt.getJSONArray("bodies");
		for (int i = 0; i < bodies.length(); i++)
			phySim.addBody(bodiesFactory.createInstance(bodies.getJSONObject(i)));
	}

	public void run(int steps, OutputStream out) throws IOException {
		//PrintStream p;
		out.write("{\"states\": [".getBytes());
		out.write(phySim.toString().getBytes());
		out.write(",".getBytes());
		for (int i = 0; i < steps; i++) {
				phySim.advance();
			//	p = (out == null) ? null : new PrintStream(out);
				out.write(phySim.toString().getBytes());
				if(i!=steps-1) out.write(",".getBytes());
		}
		
		out.write("]}".getBytes());
		out.close();
	}
}
