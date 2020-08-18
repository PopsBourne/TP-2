package simulador.modelo;

import java.io.IOException;

import Observadores.VentanaPrincipal;
import eventos.Controlador;
import excepciones.ErrorDeSimulacion;
import excepciones.Existe;
import excepciones.NoExiste;
import excepciones.ParseException;

public class HiloSimulador extends Thread{

	private VentanaPrincipal mainWindow;
	private Controlador controlador;
	int steps1;
	int delay1;

	 public HiloSimulador(VentanaPrincipal mainWindow, Controlador controlador, int steps, int delay) {
	  this.mainWindow = mainWindow;
	  this.controlador = controlador;
	  this.steps1 = steps;
	  this.delay1 = delay;
	 }
	 
	 public void run() {
		      try {  
		    	  mainWindow.bloquearBotones();
		    	  for(int i = 0; i< steps1;i++) {
		        	controlador.ejecuta(1);
		        	Thread.sleep(delay1*1000);
		    	  }
		    	  mainWindow.matarHilo();
		    	  mainWindow.desbloquearBotones();
			  } catch (ParseException | Existe |NoExiste| IOException | ErrorDeSimulacion | InterruptedException e1) {
				  mainWindow.matarHilo();
				} 
	}
}