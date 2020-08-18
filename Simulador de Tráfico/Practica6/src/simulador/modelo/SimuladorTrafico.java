package simulador.modelo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Observadores.Observador;
import Observadores.ObservadorSimuladorTrafico;
import eventos.SortedArrayList;
import eventos.tiposeventos.Evento;
import excepciones.ErrorDeSimulacion;
import excepciones.Existe;
import excepciones.NoExiste;
import excepciones.ParseException;
import simulador.MapaCarreteras;
import simulador.carreteras.Carretera;
import simulador.cruces.CruceGenerico;

public class SimuladorTrafico implements Observador<ObservadorSimuladorTrafico> {

	private MapaCarreteras mapa; // tiene carreteras cruces y vehiculos
	private List<Evento> eventos;
	private int contadorTiempo; // contabilizar los pasos de la simulacion numero de pasos
	String s ;

	private List<ObservadorSimuladorTrafico> observadores;

	public SimuladorTrafico() {
		this.s = "";
		this.mapa = new MapaCarreteras();
		this.contadorTiempo = 0;
		Comparator<Evento> cmp = new Comparator<Evento>() {
			public int compare(Evento ev1, Evento ev2) {
				if (ev1.getTiempo() < ev2.getTiempo())
					return -1;
				else if (ev1.getTiempo() == ev2.getTiempo())
					return 0;
				else return 1;
			}
		};
		this.observadores = new ArrayList<>();
		this.eventos = new SortedArrayList<>(cmp); // estructura ordenada por “tiempo”
	}

	@Override
	public void addObservador(ObservadorSimuladorTrafico o) {
		if (o != null && !this.observadores.contains(o)) {
			this.observadores.add(o);
		}
	}

	@Override
	public void removeObservador(ObservadorSimuladorTrafico o) {
		if (o != null && this.observadores.contains(o)) {
			this.observadores.remove(o);
		}
	}

	// EJECUTA
	public void ejecuta(int pasosSimulacion, OutputStream ficheroSalida)
			throws ParseException, NoExiste, Existe, IOException {
		
		int limiteTiempo = this.contadorTiempo + pasosSimulacion - 1;
		while (this.contadorTiempo <= limiteTiempo) {
			// 1. ejecutar los eventos correspondientes a “this.contadorTiempo”
			for (int i = 0; i < eventos.size(); i++) {
				if (eventos.get(i).getTiempo() == contadorTiempo) 
					eventos.get(i).ejecuta(mapa);
			}
			// 2. invocar al metodo avanzar de las carreteras
			List<Carretera> carreteras = mapa.getCarreteras();
			for (int i = 0; i < carreteras.size(); i++) {
				carreteras.get(i).avanza();
			}
			// 3. invocar al metodo avanzar de los cruces
			List<CruceGenerico<?>> cruces = mapa.getCruces();
			for (int i = 0; i < cruces.size(); i++) {
				cruces.get(i).avanza();
			}
			// 4. this.contadorTiempo++;
			contadorTiempo++;
			if(ficheroSalida!=null){
				String s1 ="";
				 s1 += mapa.generateReport(contadorTiempo);
				 ficheroSalida.write(s1.getBytes());
			}
			s += mapa.generateReport(contadorTiempo);
			notificaAvanza();
		}
	}

	// REINICIA
	public void reiniciarSimulador() throws ErrorDeSimulacion {
		this.s = "";
		this.mapa = new MapaCarreteras(); 
		this.contadorTiempo = 0;
		Comparator<Evento> cmp = new Comparator<Evento>() {
			public int compare(Evento ev1, Evento ev2) { 
				if (ev1.getTiempo() < ev2.getTiempo())
					return -1;
				else if (ev1.getTiempo() == ev2.getTiempo())
					return 0;
				else return 1;
			}
		};
		this.eventos = new SortedArrayList<>(cmp); // estructura ordenada por “tiempo”
		this.notificaReinicia();
	}

	// INSERTA EVENTO
	public void insertaEvento(Evento e) throws ErrorDeSimulacion {
		if (e != null) {
			if (e.getTiempo() < this.contadorTiempo) {
				ErrorDeSimulacion err = new ErrorDeSimulacion("Error tiempo incorrecto");
				this.notificaError(err);
				throw err;
			} else {
				this.eventos.add(e);
				this.notificaNuevoEvento();
				// se notifica a los observadores
			}
		} else {
			ErrorDeSimulacion err = new ErrorDeSimulacion("Error evento nulo");
			this.notificaError(err); // se notifica a los observadores
			throw err;
		}
	}

	// GUARDAR FICHERO
	public void guardarF(String n, String s) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(n + "_saved");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			os.write(s.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// GENERAR REPORT
	public String generaR() {
		return s;
	}

	// GUARDAR REPORT
	public void guardarR(String n, String s) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(n + "_saved_report");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			os.write(s.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getContador() {
		return contadorTiempo;
	}
	
	// NOTIFICACIONES
	private void notificaAvanza() {
		for (ObservadorSimuladorTrafico o : this.observadores) {
			o.avanza(this.contadorTiempo, this.mapa, this.eventos);
		}
	}

	private void notificaReinicia() {
		for (ObservadorSimuladorTrafico o : this.observadores) {
			o.reinicia(this.contadorTiempo, this.mapa, this.eventos);
		}
	}

	private void notificaError(ErrorDeSimulacion err) {
		for (ObservadorSimuladorTrafico o : this.observadores) {
			o.errorSimulador(this.contadorTiempo, this.mapa, this.eventos, err);
		}
	}

	private void notificaNuevoEvento() {
		for (ObservadorSimuladorTrafico o : this.observadores) {
			o.addEvento(this.contadorTiempo, this.mapa, this.eventos);
		}
	}
}