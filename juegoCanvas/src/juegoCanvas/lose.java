package juegoCanvas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dataBaseConnection.process;

public class lose extends JPanel implements ActionListener {

	private JLabel fondo;
	private ImageIcon imagen = new ImageIcon(this.getClass().getResource("fondoLose.png"));
	private static Font fuente = new Font("Calibri", 3, 23);
	private JButton playAgain;
	private JLabel scoreLabel;
	private JLabel record;
	private JButton mainMenu;
	private static int mayor = 0;
	private Image character;
	private int recordd;

	public lose(int score, Image img) {

		character = img;

		setLayout(null);

		if (score > mayor) {

			mayor = score;

		}

		scoreLabel = new JLabel("Score: " + String.valueOf(score));
		scoreLabel.setBounds(50, 300, 150, 30);
		scoreLabel.setFont(fuente);
		scoreLabel.setForeground(Color.YELLOW);
		add(scoreLabel);

		process process = new process();
		if (score > process.select()) {
			process.update(score);
		}

		record = new JLabel("Record: " + process.select());
		record.setBounds(50, 330, 150, 30);
		record.setFont(fuente);
		record.setForeground(Color.YELLOW);
		add(record);

		mainMenu = new JButton("Main Menu");
		mainMenu.setBounds(50, 440, 150, 30);
		mainMenu.setFont(fuente);
		mainMenu.setBackground(Color.YELLOW);
		mainMenu.addActionListener(this);
		add(mainMenu);

		playAgain = new JButton("Play Again");
		playAgain.addActionListener(this);
		playAgain.setBackground(Color.YELLOW);
		playAgain.setFont(new Font("Calibri", 3, 25));
		playAgain.setBounds(50, 400, 150, 30);
		add(playAgain);

		fondo = new JLabel(imagen);
		fondo.setBounds(0, 0, 400, 600);
		add(fondo);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(playAgain)) {

			this.setVisible(false);
			ventana.getContenedor().add(new motor(character)).requestFocusInWindow();

		}
		if (e.getSource().equals(mainMenu)) {

			this.setVisible(false);
			ventana.getContenedor().add(new inicio()).requestFocusInWindow();

		}
	}
}
