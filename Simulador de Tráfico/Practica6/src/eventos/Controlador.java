package eventos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import Observadores.ObservadorSimuladorTrafico;
import codigo.es.ucm.fdi.ini.Ini;
import codigo.es.ucm.fdi.ini.IniSection;
import eventos.constructores.ParserEventos;
import eventos.tiposeventos.Evento;
import excepciones.ErrorDeSimulacion;
import excepciones.Existe;
import excepciones.NoExiste;
import excepciones.ParseException;
import simulador.modelo.SimuladorTrafico;

public class Controlador {
	private SimuladorTrafico simulador;
	private OutputStream ficheroSalida;
	private InputStream ficheroEntrada;
	private int pasosSimulacion;

	public Controlador(SimuladorTrafico simulador, Integer limiteTiempo, // limiteTiempo
			InputStream ficheroEntrada, OutputStream ficheroSalida) {
		this.simulador = simulador;
		this.ficheroSalida = ficheroSalida;
		this.ficheroEntrada = ficheroEntrada;
		this.pasosSimulacion = limiteTiempo;
	}

//	public void ejecuta() throws ErrorDeSimulacion, ParseException, NoExiste, Existe, IOException {// parametro
//		// lee los eventos y se los manda al simulador
//		this.cargaEventos(this.ficheroEntrada);
//		this.simulador.ejecuta(pasosSimulacion, this.ficheroSalida);
//	}

	public void ejecuta(int pasos) throws ParseException, NoExiste, Existe, IOException, ErrorDeSimulacion {
		//this.cargaEventos(this.ficheroEntrada);
		this.simulador.ejecuta(pasos, this.ficheroSalida);
	}

	public void reinicia() throws ErrorDeSimulacion {
		this.simulador.reiniciarSimulador();
	}

	public void addObserver(ObservadorSimuladorTrafico o) {
		this.simulador.addObservador(o);
	}

	public void removeObservador(ObservadorSimuladorTrafico o) {
		this.simulador.removeObservador(o);
	}

	public void cargaEventos(InputStream inStream) throws ErrorDeSimulacion {
		Ini ini;
		try {
			// lee el fichero y carga su atributo iniSections
			ini = new Ini(inStream);
		} catch (IOException e) {
			throw new ErrorDeSimulacion("Error en la lectura de eventos: " + e);
		}
		// recorremos todas los elementos de iniSections para generar el evento correspondiente
		for (IniSection sec : ini.getSections()) {
			// parseamos la sección para ver a que evento corresponde
			Evento e = ParserEventos.parseaEvento(sec);
			if (e != null)
				this.simulador.insertaEvento(e);
			else
				throw new ErrorDeSimulacion("Evento desconocido: " + sec.getTag());
		}
	}

	public void guardarF(String n, String s) {
		simulador.guardarF(n, s);
	}

	public void guardarR(String n, String s) {
		simulador.guardarR(n, s);
	}

	public void Salir() {
		System.exit(0);
	}

	public String generaR() {
		String s = "";
		s = simulador.generaR();
		return s;
	}

	public int getPasosSimulacion() {
		return pasosSimulacion;
	}
	
	public int getPasosSimulacionSimulador() {
		return simulador.getContador();
	}
}