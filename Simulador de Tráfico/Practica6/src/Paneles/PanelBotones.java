package Paneles;

import javax.swing.JPanel;

import Observadores.DialogoInformes;

@SuppressWarnings("serial")
public class PanelBotones extends JPanel{

	private DialogoInformes dialogoInformes;

	public PanelBotones(DialogoInformes dialogoInformes) {
		super();
		this.dialogoInformes = dialogoInformes;
	}
}
