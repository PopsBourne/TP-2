package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NoGravity;

public class NoGravityBuilder extends Builder<GravityLaws> {

	public NoGravityBuilder() {
		super("ng", "No gravity");
	}

	public GravityLaws createTheInstance(JSONObject data) {
	//	if (data.similar(super.getBuilderInfo()))
			return new NoGravity();
	//	return null;
	}
}
