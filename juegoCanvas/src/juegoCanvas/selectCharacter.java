package juegoCanvas;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class selectCharacter extends JPanel implements ActionListener {

	private JButton characters[][] = new JButton[3][3];
	private ImageIcon fondo = new ImageIcon(this.getClass().getResource("fondoSelect.png"));
	private ImageIcon anakinShip = new ImageIcon(this.getClass().getResource("anakinShip.png"));
	private ImageIcon millenium = new ImageIcon(this.getClass().getResource("millenium.png"));
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private Image character;
	private JLabel background;

	public selectCharacter() {

		setLayout(null);

		int x, y = 0;

		for (int i = 0; i < characters.length; i++) {

			x = 15;
			y += 100 + 35;

			for (int j = 0; j < characters[0].length; j++) {
				characters[i][j] = new JButton();
				characters[i][j].setBounds(x, y, 100, 100);
				characters[i][j].addActionListener(this);
				this.add(characters[i][j]);
				x += 100 + 25;
			}
		}

		characters[0][0].setIcon(anakinShip);
		characters[0][0].setBackground(Color.red);

		characters[0][1].setIcon(millenium);
		characters[0][1].setBackground(Color.red);

		background = new JLabel(fondo);
		background.setBounds(0, 0, 400, 600);
		add(background);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(characters[0][0])) {

			character = toolkit.createImage(this.getClass().getResource("nave.png"));
			this.setVisible(false);
			ventana.getContenedor().add(new motor(character)).requestFocusInWindow();

		} else if (e.getSource().equals(characters[0][1])) {

			character = toolkit.createImage(this.getClass().getResource("milleniumFalcon.png"));
			this.setVisible(false);
			ventana.getContenedor().add(new motor(character)).requestFocusInWindow();

		}
	}

}
