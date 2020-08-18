package simulator.factories;

import simulator.misc.Vector;
import simulator.model.Body;
import org.json.JSONObject;

public class BasicBodyBuilder extends Builder<Body> {

	public BasicBodyBuilder() {
		super("basic", "default body");
	}

	@Override
	public Body createTheInstance(JSONObject data) throws IllegalArgumentException {
		String id = data.getString("id");
		double[] p = jsonArrayTodoubleArray(data.getJSONArray("pos"));
		double[] v = jsonArrayTodoubleArray(data.getJSONArray("vel"));
		double m = data.getDouble("mass");

	//	if (data.similar(super.getBuilderInfo().get("data")))
			return new Body(id, new Vector(v), new Vector(p), m);
	//	return null;
	}

	@Override
	public JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("pos", "the position");
		data.put("mass", "the mass");
		data.put("id", "the identifier");
		data.put("vel", "the velocity");
		return data;
	}

}
