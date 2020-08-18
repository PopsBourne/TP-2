package Paneles;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import Observadores.VentanaPrincipal;

@SuppressWarnings("serial")
public class PanelEditorEventos extends PanelAreaTexto {

	public PanelEditorEventos(String titulo, String texto, boolean editable, VentanaPrincipal mainWindow) {
		super(titulo, editable);
		this.setTexto(texto);
		// OPCIONAL
		PopUpMenu popUp = new PopUpMenu(mainWindow);
		this.areatexto.add(popUp);
		this.areatexto.addMouseListener(new MouseListener() {

			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e) && areatexto.isEnabled())
					popUp.show(e.getComponent(), e.getX(), e.getY());
			}

			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}
		});
	}
}
