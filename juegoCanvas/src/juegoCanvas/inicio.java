package juegoCanvas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class inicio extends JPanel implements ActionListener {

	private ImageIcon background = new ImageIcon(this.getClass().getResource("fondo.png"));
	private static Font fuente = new Font("Calibri", 3, 20);

	private JButton start;
	private JLabel fondo;

	public inicio() {

		setLayout(null);
		setFocusable(true);

		start = new JButton("Play");
		start.setFont(fuente);
		start.setBackground(Color.DARK_GRAY);
		start.setForeground(Color.BLACK);
		start.setBounds(100, 350, 125, 35);
		start.addActionListener(this);
		add(start);

		fondo = new JLabel(background);
		fondo.setBounds(0, 0, 400, 600);
		add(fondo);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(start)) {

			this.setVisible(false);
			ventana.getContenedor().add(new selectCharacter()).requestFocusInWindow();

		}

	}

}
