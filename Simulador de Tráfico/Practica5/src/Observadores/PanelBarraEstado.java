package Observadores;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import eventos.Controlador;
import eventos.tiposeventos.Evento;
import excepciones.ErrorDeSimulacion;
import simulador.MapaCarreteras;

@SuppressWarnings("serial")
public class PanelBarraEstado extends JPanel implements ObservadorSimuladorTrafico {

	private JLabel infoEjecucion;
	
	  public  PanelBarraEstado(String mensaje,  Controlador controlador) { 
		  this.setLayout(new FlowLayout(FlowLayout.LEFT));  
		  this.infoEjecucion = new JLabel(mensaje);   
		  this.add(this.infoEjecucion);   
		  this.setBorder(BorderFactory.createBevelBorder(1)); 
		  controlador.addObserver(this); 
	  }  
	  
	  public void setMensaje(String mensaje) {
		  this.infoEjecucion.setText(mensaje);
	  } 
	  // la ventana principal se comunica con el panel 
	  
	  @Override 
	  public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) { 
		  if(!eventos.isEmpty())
		  this.infoEjecucion.setText("Paso: " + tiempo + " del Simulador"); 
	 } 
	  
	  @Override  
	  public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) { 
		  this.infoEjecucion.setText("Evento añadido al simulador"); 
	  }  
	  
	  @Override  
	  public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
	  }

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
	}
}
