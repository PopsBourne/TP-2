package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLosingBodyBuilder extends Builder<Body> {

	public MassLosingBodyBuilder() {
		super("mlb", "mass losing body");
	}

	public Body createTheInstance(JSONObject data) {
		double[] p = jsonArrayTodoubleArray(data.getJSONArray("pos"));
		double[] v = jsonArrayTodoubleArray(data.getJSONArray("vel")); 
		String id = data.getString("id");
		double m = data.getDouble("mass");
		double lfac = data.getDouble("factor");
		double lfrq = data.getDouble("freq");
		
	//	if(data.similar(super.getBuilderInfo()))
			return new MassLossingBody(id, new Vector(v),new Vector(p), m, lfac,lfrq);
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
