package Paneles;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public abstract class PanelAreaTexto extends JPanel {
	
	protected JTextArea areatexto;
	Border b = BorderFactory.createLineBorder(Color.BLACK, 3);
	String texto;  

	public PanelAreaTexto(String titulo, boolean editable) { 
		this.setLayout(new GridLayout(1,1)); 
		this.areatexto = new JTextArea();
		this.areatexto.setEditable(editable);
		this.add(new JScrollPane(areatexto,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				 JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));    
		this.setBorde(titulo);
		}

	public void setBorde(String titulo) {
		this.setBorder(BorderFactory.createTitledBorder(b, titulo));
	}

	public String getTexto() {
		return areatexto.getText();
	}
	
	public void setTexto(String texto) {
		this.areatexto.setText(texto);
	}

	public void inserta(String valor) {
		this.areatexto.insert(valor, this.areatexto.getCaretPosition());
	}
}
