package simulator.factories;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;


public class BuilderBasedFactory<T> implements Factory<T> {

	List<Builder<T>> builders;
	List<JSONObject> factoryElements;

	public BuilderBasedFactory(List<Builder<T>> builders) {
		this.builders = new ArrayList<Builder<T>>(builders);
		this.factoryElements = new ArrayList<JSONObject>();

		for (int i = 0; i < builders.size(); i++) {
			this.factoryElements.add(i, builders.get(i).getBuilderInfo());
		}
	}

	@Override
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		for (int i = 0; i < builders.size(); i++) {
			T b = builders.get(i).createInstance(info);
			if (b != null) return b;
		}
		return null;
	}

	@Override
	public List<JSONObject> getInfo() {
		List<JSONObject> jsList = new ArrayList <JSONObject>();
		for (int i = 0; i < this.builders.size(); i++) {
			JSONObject jsObj = builders.get(i).getBuilderInfo();
			jsList.add(jsObj);
		}
		return jsList;
		
		
	
	}
	
//PRUEBA	
/*
	public static void main(String[] args) {
		ArrayList<Builder<Body>> bodyBuilders = new ArrayList<>();
		bodyBuilders.add(new BasicBodyBuilder());
		bodyBuilders.add(new MassLosingBodyBuilder());
		Factory<Body> bodyFactory = new BuilderBasedFactory<Body>(bodyBuilders);
	}
*/
}
