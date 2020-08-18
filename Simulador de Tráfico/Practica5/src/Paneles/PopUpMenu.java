package Paneles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import Observadores.VentanaPrincipal;
import eventos.constructores.ConstructorEventos;
import eventos.constructores.ParserEventos;

@SuppressWarnings("serial")
public class PopUpMenu extends JPopupMenu {

	private VentanaPrincipal mainWindow;

	public PopUpMenu(VentanaPrincipal mainWindow) {
		JMenu plantillas = new JMenu("Nueva plantilla");
		this.add(plantillas);
		// añadir las opciones con sus listeners
		JMenuItem item1 = new JMenuItem("Salvar");
		JMenuItem item2 = new JMenuItem("Limpiar");
		JMenuItem item3 = new JMenuItem("Cargar");

		this.add(item1);
		this.add(item2);
		this.add(item3);

		item1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					mainWindow.salvarFichero();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.limpiarF();
			}
		});

		item3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.cargaFichero();
			}
		});
		// ****************************************************************************
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showPopup(e);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showPopup(e);
				}
			}
		});
		// ****************************************************************************

		
		// Submenu
		for (ConstructorEventos ce : ParserEventos.getConstructoresEventos()) {
			JMenuItem mi = new JMenuItem(ce.toString());
			mi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mainWindow.inserta(ce.template() + System.lineSeparator());
				}
			});
			plantillas.add(mi);
		}
		// String template() es un método público que debe definirse en la clase
		// ConstructorEventos,
		// y que debe generar la plantilla correspondiente en función de los campos, y
		// teniendo en cuenta
		// los posibles valores por defecto.
	}

	private void showPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            this.show(e.getComponent(),
                    e.getX(), e.getY());
        }
    }
}