package juegoCanvas;

import java.awt.Container;

import javax.swing.JFrame;

public class ventana extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int ancho = 400;
	private static final int alto = 600;
	private static Container contenedor = new Container();

	public ventana() {

		setSize(ancho, alto);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// setResizable(false);
		inicio inicio = new inicio();
		contenedor = getContentPane();
		contenedor.add(inicio);
		this.setVisible(true);

	}

	public static Container getContenedor() {
		return contenedor;
	}

	public static int getAncho() {
		return ancho;
	}

	public static int getAlto() {
		return alto;
	}

}
