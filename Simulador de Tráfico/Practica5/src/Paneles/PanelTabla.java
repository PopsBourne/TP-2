package Paneles;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import Observadores.ModeloTabla;

@SuppressWarnings("serial")
public class PanelTabla<T> extends JPanel {
	
	private ModeloTabla<T> modelo;
	private JTable tabla;
	Border b = BorderFactory.createLineBorder(Color.BLACK, 3);

	public PanelTabla(String bordeId, ModeloTabla<T> modelo){  
		this.setLayout(new GridLayout(1,1)); 
		this.setBorder(BorderFactory.createTitledBorder(b, bordeId));  
		this.modelo = modelo;   
		tabla = new JTable(this.modelo);  
		this.add(new JScrollPane(tabla));	
	}
}
