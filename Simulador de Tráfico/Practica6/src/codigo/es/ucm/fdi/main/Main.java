package codigo.es.ucm.fdi.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import simulador.modelo.SimuladorTrafico;
import Observadores.VentanaPrincipal;
import eventos.Controlador;
import excepciones.ErrorDeSimulacion;
import excepciones.Existe;
import excepciones.NoExiste;
import excepciones.ParseException;

public class Main {

	private final static Integer limiteTiempoPorDefecto = 10;
	private static Integer limiteTiempo = null;
	private static String ficheroEntrada = "";
	private static String ficheroSalida = "";

	private static void ParseaArgumentos(String[] args) throws org.apache.commons.cli.ParseException,
			InvocationTargetException, InterruptedException, IOException, ErrorDeSimulacion, NoExiste, Existe {
		// define the valid command line options
		Options opcionesLineaComandos = Main.construyeOpciones();
		// parse the command line as provided in args
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine linea = parser.parse(opcionesLineaComandos, args);
			parseaOpcionHELP(linea, opcionesLineaComandos);
			parseaOpcionFicheroIN(linea);
			parseaOpcionFicheroOUT(linea);
			parseaOpcionSTEPS(linea);
			parseaOpcionModo(linea);
			// if there are some remaining arguments, then something wrong is provided in the command line!
			String[] resto = linea.getArgs();
			if (resto.length > 0) {
				String error = "Illegal arguments:";
				for (String o : resto)
					error += (" " + o);
				throw new ParseException(error);
			}
		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
	}

	private static void parseaOpcionModo(CommandLine linea) throws InvocationTargetException, InterruptedException,
			IOException, ErrorDeSimulacion, ParseException, NoExiste, Existe {
		
		ModoEjecucion batch = ModoEjecucion.BATCH;
		ModoEjecucion gui = ModoEjecucion.GUI;
		if(linea.getOptionValue("m")==null)
			iniciaModoEstandar();
		else {
			if (linea.getOptionValue("m").equals(batch.getModelDesc())) {
				iniciaModoEstandar();
			} else if (linea.getOptionValue("m").equals(gui.getModelDesc())) {
				iniciaModoGrafico();
			}	
			if (!linea.getOptionValue("m").equals(batch.getModelDesc()) &&  !linea.getOptionValue("m").equals(gui.getModelDesc())) 
				throw new ParseException("Error modo incorrecto");
		}
	}

	// Grafico
	private static void iniciaModoGrafico()
			throws FileNotFoundException, InvocationTargetException, InterruptedException {
		SimuladorTrafico sim = new SimuladorTrafico();
		OutputStream os = Main.ficheroSalida == null ? System.out : new FileOutputStream(new File(Main.ficheroSalida));
		Controlador ctrl = new Controlador(sim, Main.limiteTiempo, null, os);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new VentanaPrincipal(Main.ficheroEntrada, ctrl);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (ErrorDeSimulacion e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static Options construyeOpciones() {
		Options opcionesLineacomandos = new Options();
		
		opcionesLineacomandos.addOption(Option.builder("h").longOpt("help").desc("Muestra la ayuda.").build());
		opcionesLineacomandos.addOption(
				Option.builder("i").longOpt("input").hasArg().desc("Fichero de entrada de eventos.").build());
		opcionesLineacomandos.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Muestra el modo.").build());
		opcionesLineacomandos.addOption(Option.builder("o").longOpt("output").hasArg()
				.desc("Fichero de salida, donde se escriben los informes.").build());
		opcionesLineacomandos.addOption(Option.builder("t").longOpt("ticks").hasArg()
				.desc("Pasos que ejecuta el simulador en su bucle principal (el valor por defecto es "
						+ Main.limiteTiempoPorDefecto + ").")
				.build());
		
		return opcionesLineacomandos;
	}

	private static void parseaOpcionHELP(CommandLine linea, Options opcionesLineaComandos) {
		if (linea.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), opcionesLineaComandos, true);
			System.exit(0);
		}
	}

	private static void parseaOpcionFicheroIN(CommandLine linea) throws ParseException {
		Main.ficheroEntrada = linea.getOptionValue("i");
		if ((Main.ficheroEntrada == null && linea.getOptionValue("m")==null) || 
			(Main.ficheroEntrada == null && linea.getOptionValue("m")!=null && linea.getOptionValue("m").equals("batch"))) {
			throw new ParseException("El fichero de eventos no existe");
		}
	}

	private static void parseaOpcionFicheroOUT(CommandLine linea) throws ParseException {
		Main.ficheroSalida = linea.getOptionValue("o");
	}

	private static void parseaOpcionSTEPS(CommandLine linea) throws ParseException {
		String t = linea.getOptionValue("t", Main.limiteTiempoPorDefecto.toString());
		try {
			Main.limiteTiempo = Integer.parseInt(t);
			assert (Main.limiteTiempo < 0);
		} catch (Exception e) {
			throw new ParseException("Valor invalido para el limite de tiempo: " + t);
		}
	}


	private static void iniciaModoEstandar() throws IOException, ErrorDeSimulacion, ParseException, NoExiste, Existe {
		InputStream is = new FileInputStream(new File(Main.ficheroEntrada));
		OutputStream os = Main.ficheroSalida == null ? System.out : new FileOutputStream(new File(Main.ficheroSalida));
		SimuladorTrafico sim = new SimuladorTrafico();
		Controlador ctrl = new Controlador(sim, Main.limiteTiempo, is, os);
		ctrl.cargaEventos(is);
		ctrl.ejecuta(ctrl.getPasosSimulacion());
		is.close();
		System.out.println("Done!");
		os.close();
	}

	public static void main(String[] args) throws IOException, ErrorDeSimulacion, org.apache.commons.cli.ParseException,
			ParseException, NoExiste, Existe, InvocationTargetException, InterruptedException {
		// example command lines:
		// -i resources/examples/events/basic/ex1.ini
		// -i resources/examples/events/advanced/ex1.ini
		// --help
		Main.ParseaArgumentos(args);
		//Main.iniciaModoEstandar();

		 //Main.ejecutaFicheros(".");
	}

//	private static void ejecutaFicheros(String path)
//			throws IOException, ErrorDeSimulacion, ParseException, NoExiste, Existe {
//		File dir = new File(path);
//
//		if ( !dir.exists() ) {
//			throw new FileNotFoundException(path);
//		}
//		
//		File[] files = dir.listFiles(new FilenameFilter() {
//			@Override
//			public boolean accept(File dir, String name) {
//				return name.endsWith(".ini");
//			}
//		});
//
//		for (File file : files) {
//			Main.ficheroEntrada = file.getAbsolutePath();
//			Main.ficheroSalida = file.getAbsolutePath() + ".out";
//			Main.limiteTiempo = 10;
//			Main.iniciaModoEstandar();
//		}
//	}
}