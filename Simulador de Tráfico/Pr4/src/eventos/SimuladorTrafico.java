package eventos;

import java.io.IOException;
import java.io.OutputStream;
//import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import excepciones.Existe;
import excepciones.NoExiste;
import excepciones.ParseException;
import simulador.Carretera;
import simulador.Cruce;
import simulador.MapaCarreteras;

public class SimuladorTrafico {

	private MapaCarreteras mapa; // tiene carreteras cruces y vehiculos
	private List<Evento> eventos;
	private int contadorTiempo; // contabilizar los pasos de la simulacion numero de pasos
	
	public SimuladorTrafico() {
		 this.mapa = new MapaCarreteras();
		 this.contadorTiempo = 0;
		 Comparator<Evento> cmp = new Comparator<Evento>() {
			 	public int compare(Evento ev1, Evento ev2) {
				if(ev1.getTiempo() < ev2.getTiempo()) return -1;
				else if(ev1.getTiempo() == ev2.getTiempo()) return 0;
				else return 1;
				}
		 };
		 this.eventos = new SortedArrayList<>(cmp); // estructura ordenada por “tiempo”
	}

	public void ejecuta(int pasosSimulacion, OutputStream ficheroSalida) throws ParseException, NoExiste, Existe, IOException {
		 int limiteTiempo = this.contadorTiempo + pasosSimulacion - 1;
		 while (this.contadorTiempo <= limiteTiempo) {
			//1. ejecutar los eventos correspondientes a “this.contadorTiempo” 
			 for (int i = 0 ; i < eventos.size(); i++) {
				 if(eventos.get(i).getTiempo() == contadorTiempo)
					 eventos.get(i).ejecuta(mapa);
			 }
			
 
		//2. invocar al metodo avanzar de las carreteras
			 List<Carretera> carreteras = mapa.getCarreteras();
			 for (int i = 0 ; i < carreteras.size(); i++) {
				 carreteras.get(i).avanza();
			 }
		//3. invocar al metodo avanzar de los cruces
			 List<Cruce> cruces = mapa.getCruces();
			 for (int i = 0 ; i < cruces.size(); i++) {
				 cruces.get(i).avanza();
			 }
		//4. this.contadorTiempo++;
			 contadorTiempo++;
		//5. escribir un informe en outputstream
			 //hacer
			 String s ="";
			 s += mapa.generateReport(contadorTiempo);
			 ficheroSalida.write(s.getBytes());
		// en caso de que no sea null
		 }
		 ficheroSalida.close();
	}
	
	 public void insertaEvento(Evento e) {
		 if(e.getTiempo() >= contadorTiempo)
		 eventos.add(e);// inserta un evento en “eventos”, controlando que el tiempo
		 				// de ejecución del evento sea mayor que “contadorTiempo”
	}
}