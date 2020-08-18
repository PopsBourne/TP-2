package simulator.factories;

import org.json.JSONObject;

import simulator.model.FallingToCenterGravity;
import simulator.model.GravityLaws;

public class FallingToCenterGravityBuilder extends Builder<GravityLaws> {

	public FallingToCenterGravityBuilder() {
		super("ftcg", " ");
	}

	public GravityLaws createTheInstance(JSONObject data) {
	//	if (data.similar(super.getBuilderInfo()))
			return new FallingToCenterGravity();
	//	return null;
	}
}
