package Paneles;

import java.awt.Component;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class JscrollPane extends Component {

	JTextArea areaTexto;
	int posHorizontal;
	int posVertical;

	public JscrollPane(JTextArea areaTexto, int posHorizontal, int posVertical) {
		super();
		this.areaTexto = areaTexto;
		this.posHorizontal = posHorizontal;
		this.posVertical = posVertical;
	}
}