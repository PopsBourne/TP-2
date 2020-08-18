package eventos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import codigo.es.ucm.fdi.ini.Ini;
import codigo.es.ucm.fdi.ini.IniSection;
import excepciones.ErrorDeSimulacion;
import excepciones.Existe;
import excepciones.NoExiste;
import excepciones.ParseException;

public class Controlador {
	private SimuladorTrafico simulador;
	private OutputStream ficheroSalida;
	private InputStream ficheroEntrada;
	private int pasosSimulacion;

	public Controlador(SimuladorTrafico simulador, Integer limiteTiempo, //limiteTiempo
			InputStream ficheroEntrada, OutputStream ficheroSalida) {
		this.simulador = simulador;
		this.ficheroSalida = ficheroSalida;
		this.ficheroEntrada = ficheroEntrada;
		this.pasosSimulacion = limiteTiempo;
	}
	
	public void ejecuta() throws ErrorDeSimulacion, ParseException, NoExiste, Existe, IOException {//throws
		// lee los eventos y se los manda al simulador
		this.cargaEventos(this.ficheroEntrada);
		this.simulador.ejecuta(pasosSimulacion, this.ficheroSalida);
	}

	private void cargaEventos(InputStream inStream) throws ErrorDeSimulacion {
		Ini ini;
		try {
			// lee el fichero y carga su atributo iniSections
			ini = new Ini(inStream);
		} catch (IOException e) {
			throw new ErrorDeSimulacion("Error en la lectura de eventos: " + e);
		}
		// recorremos todas los elementos de iniSections para generar el evento
		// correspondiente
		for (IniSection sec : ini.getSections()) {
			// parseamos la sección para ver a que evento corresponde
			Evento e = ParserEventos.parseaEvento(sec);
			if (e != null)
				this.simulador.insertaEvento(e);
			else
				throw new ErrorDeSimulacion("Evento desconocido: " + sec.getTag());
		}
	}
}