package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Builder<T> {

	protected String typeTag;
	protected String desc;

	public Builder() {
	}

	public Builder(String typeTag, String desc) {
		this.typeTag = typeTag;
		this.desc = desc;
	}

	protected double[] jsonArrayTodoubleArray(JSONArray ja) {
		double[] da = new double[ja.length()];
		for (int i = 0; i < da.length; i++)
			da[i] = ja.getDouble(i);
		return da;
	}

	public T createInstance(JSONObject info) {
		T b = null;
		if (typeTag != null && typeTag.equals(info.getString("type")))
			b = createTheInstance(info.getJSONObject("data"));
		return b;
	}

	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		info.put("type", typeTag);
		info.put("data", createData());
		info.put("desc", desc);
		return info;
	}

	protected JSONObject createData() {
		return new JSONObject();
	}

	public abstract T createTheInstance(JSONObject object);
}
