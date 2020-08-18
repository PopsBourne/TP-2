package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<GravityLaws> {

	public NewtonUniversalGravitationBuilder() {
		super("nlug", " ");
	}

	public GravityLaws createTheInstance(JSONObject data) {
	//	if (data.similar(super.getBuilderInfo()))
			return new NewtonUniversalGravitation();
	//	return null;
	}
}
