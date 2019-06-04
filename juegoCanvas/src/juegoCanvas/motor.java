package juegoCanvas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class motor extends JPanel implements KeyListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private int movimientoY = 0;
	private int movimiento2Y = -600;
	private int movement = (ventana.getAncho() - 181) / 2;
	private boolean allowMovement = true;
	private boolean disparar = false;
	private int movementX = 0;
	private int movimientoBala = 400;
	private boolean showEnemies = false;

	private int respawn = 0;
	private int aparecerObjeto;

	private Random rand = new Random(System.nanoTime());
	private int enemyPositionX = rand.nextInt(250);
	private int vida = 120;

	private int movimientoAsteroide;
	private boolean moverAste = false;

	// Variables para disparar desde la nave

	private int dispararNave = 75;
	private boolean naveShotting = false;

	// Strings de vida y score

	private int vidaNave = 52;
	private int puntuacion = 0;
	private Timer timer;
	private static Font fuente = new Font("Calibri", 3, 20);
	private int timing = 0;
	private boolean mostrarplus50 = false;
	private int mayor;

	private boolean inContador = false;

	private KeyEvent e;

	// MultiThreading

	private Thread ejecutar;
	// Medidas de la nave

	private static final int anchoNave = 112;

	// Pause Screen

	private JButton reanudar;

	// backgrounds images
	private Image nave1;
	private Image fondo = toolkit.createImage(this.getClass().getResource("background.png"));
	private Image shot = toolkit.createImage(this.getClass().getResource("shot.png"));
	private Image enemy = toolkit.createImage(this.getClass().getResource("enemy.png"));
	private Image asteroide = toolkit.createImage(this.getClass().getResource("asteroide.png"));
	private Image score = toolkit.createImage(this.getClass().getResource("barra.png"));
	private Image plus50 = toolkit.createImage(this.getClass().getResource("+50.png"));
	private Image pause = toolkit.createImage(this.getClass().getResource("pause.png"));
	private boolean mostrarPause = false;

	// Constructor

	public motor(Image img) {

		timer = new Timer(250, acciones);
		timer.start();

		nave1 = img;

		Runnable r = new backgroundMovement();
		Thread t = new Thread(r);
		t.start();

		// move = new Thread(this);
		// move.start();
		setFocusable(true);
		addKeyListener(this);
	}

	// Methods

	@Override
	public void paint(Graphics g) {

		/*
		 * buffer = getBufferStrategy();
		 * 
		 * if (buffer == null) { createBufferStrategy(5); return; }
		 */

		// g = buffer.getDrawGraphics();

		g.drawImage(fondo, 0, movimientoY, this);
		g.drawImage(fondo, 0, movimiento2Y, this);
		g.drawImage(nave1, movement, 370, this);

		if (showEnemies) {

			if (aparecerObjeto == 0) {

				g.setColor(Color.white);
				g.drawRect(enemyPositionX, respawn - 15, 130, 15);
				g.setColor(Color.red);
				g.fillRect(enemyPositionX + 5, respawn - 12, vida, 10);
				g.drawImage(enemy, enemyPositionX, respawn, this);

			} else {

				g.setColor(Color.white);
				g.drawRect(enemyPositionX, movimientoAsteroide - 15, 130, 15);
				g.setColor(Color.red);
				g.fillRect(enemyPositionX + 5, movimientoAsteroide - 12, vida, 10);
				g.drawImage(asteroide, enemyPositionX, movimientoAsteroide, this);

				if (movimientoAsteroide >= 600) {

					showEnemies = false;
					moverAste = false;

				}

			}

		} else { // Si los enemigos no se muestran se ejejcutan las operaciones apra crear mas}

			aparecerObjeto = rand.nextInt(2);

			if (aparecerObjeto == 0) {

				ejecutar = new Thread(new respawning());
				ejecutar.start();

				dispararNave = 75;

				new Thread(new dispararNave()).start();

			} else if (aparecerObjeto == 1) {

				movimientoAsteroide = -50;
				moverAste = true;
				new Thread(new bajarAsteroide()).start();

			}

			enemyPositionX = rand.nextInt(250);
			showEnemies = true;
			vida = 120;

		}

		if (disparar) {

			movementX = movement + 54;

			if (e.getKeyCode() == 68) {
				movementX -= 15;
			}

			if (e.getKeyCode() == 65) {
				movementX += 15;
			}

			g.drawImage(shot, movementX, movimientoBala, this);

		}

		if (naveShotting) {

			g.drawImage(shot, enemyPositionX + 59, dispararNave, this);

		}

		if (timing % 25 == 0 && timing != 0) {
			enemyPositionX = movement;

		}

		g.drawImage(score, 0, 478, this);
		g.setColor(Color.BLACK);
		g.setFont(fuente);
		g.drawString(String.valueOf(puntuacion), 265, 540);
		g.setColor(Color.RED);
		g.fillRect(96, 530, vidaNave, 19);
		g.setColor(Color.black);
		g.drawRect(94, 528, 55, 20);

		if (mostrarplus50) {
			g.drawImage(plus50, enemyPositionX, 50, this);
		}

		if (mostrarPause) {

			g.drawImage(pause, 0, 0, this);

			g.setFont(new Font("Calibri", 3, 40));
			g.setColor(Color.WHITE);
			g.drawString("Score: ", 140, 260);
			g.drawString(String.valueOf(puntuacion), 170, 300);
			g.setFont(new Font("Calibri", 3, 37));
			g.setColor(Color.YELLOW);
			g.drawString("Press Enter to continue", 20, 400);

		}

	}

	private void detenerProcesos() {

		allowMovement = false;
		moverAste = false;
		naveShotting = false;
		disparar = false;

		lose perder = new lose(puntuacion, nave1);
		perder.setFocusable(true);
		ventana.getContenedor().add(perder).requestFocusInWindow();

		setVisible(false);

	}

	public void pausa() {

		allowMovement = false;
		moverAste = false;
		naveShotting = false;
		disparar = false;
		timer.stop();
		mostrarPause = true;
		repaint();

	}

	public void continuar() {

		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timer.start();
		allowMovement = true;
		new Thread(new backgroundMovement()).start();

		if (movimientoBala < 400) {

			disparar = true;
			new Thread(new shotting()).start();

		}

		if (respawn <= 40) {

			new Thread(new respawning()).start();

		}

		if (aparecerObjeto == 0) {

			naveShotting = true;
			new Thread(new dispararNave()).start();

		} else {

			moverAste = true;
			new Thread(new bajarAsteroide()).start();

		}

		// reanudar.setVisible(false);

	}

	private ActionListener acciones = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			puntuacion++;
			if (naveShotting) {
				timing++;
			}

		}
	};

	@Override
	public void keyPressed(KeyEvent e) {

		this.e = e;

		if (allowMovement && (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)
				&& movement <= ventana.getAncho() - anchoNave) {
			movement += 15;

		}

		if (allowMovement && (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)
				&& movement >= -10) {
			movement -= 15;

		}

		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE) {
			disparar = true;
			Thread shot = new Thread(new shotting());
			shot.start();
			// paint(g);

		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

			pausa();

		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {

			mostrarPause = false;
			repaint();
			continuar();

		}

	}

	@Override
	public void actionPerformed(ActionEvent er) {
		if (er.getSource().equals(reanudar)) {
			continuar();

		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public boolean colision(int x1, int y1, int anchoB, int altoB, int x2, int y2, int anchoTa, int altoTa) {
		if (x2 > x1 && x2 < x1 + anchoB && y2 > y1 && y2 < y1 + altoB
				|| (x2 + anchoTa > x1 && x2 + anchoTa < x1 + anchoB && y2 > y1 && y2 < y1 + altoB)) {
			return true;
		} else {
			return false;
		}
	}

	// MultiThreading

	class backgroundMovement implements Runnable {

		@Override
		public void run() {

			while (allowMovement) {

				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				movimientoY++;
				movimiento2Y++;

				repaint();

				if (movimientoY == 600) {
					movimientoY -= 1200;

				}

				if (movimiento2Y == 600) {
					movimiento2Y -= 1200;

				}
			}
		}
	}

	class shotting implements Runnable {

		@Override
		public void run() {
			while (disparar) {

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				movimientoBala--;

				if (aparecerObjeto == 0) {
					if (colision(enemyPositionX, 50, 125, 86, movementX, movimientoBala, 1, 1)) {

						vida -= 50;
						movimientoBala = 0;
						disparar = false;

					}

				} else {

					if (colision(enemyPositionX, movimientoAsteroide, 125, 86, movementX, movimientoBala, 1, 1)) {

						vida -= 10;
						movimientoBala = 0;
						disparar = false;
						if (vida <= 0) {
							moverAste = false;
						}

					}
				}

				if (vida <= 0 && naveShotting) {

					mostrarplus50 = true;

					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				if (vida <= 0) {

					naveShotting = false;
					timing = 0;
					showEnemies = false;
					mostrarplus50 = false;
					puntuacion += 50;
				}

				if (movimientoBala <= 0) {

					disparar = false;
					movimientoBala = 400;

				}
			}
		}
	}

	class respawning implements Runnable {

		@Override
		public void run() {

			respawn = -50;

			while (respawn <= 40) {

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				respawn++;

			}
		}
	}

	class mostrarplus50 implements Runnable {

		@Override
		public void run() {
			if (vida <= 0 && naveShotting) {

				mostrarplus50 = true;
			}

		}

	}

	class bajarAsteroide implements Runnable {

		@Override
		public  synchronized void run() {

			while (moverAste) {

				try {
					Thread.sleep(6);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				movimientoAsteroide++;

				if (colision(enemyPositionX, movimientoAsteroide, 114, 114, movement + 18, 410, 60, 129)) {

					moverAste = false;
					detenerProcesos();

				}

				if (movimientoAsteroide == 650) {
					moverAste = false;

				}
			}
		}
	}

	class dispararNave implements Runnable {

		@Override
		public void run() {

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			naveShotting = true;

			while (naveShotting) {

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}

				dispararNave++;

				if (colision(movement + 15, 370, anchoNave - 35, 5, enemyPositionX + 59, dispararNave, 1, 2)) {

					vidaNave -= 25;
					dispararNave = 75;

					if (vidaNave <= 0) {
						detenerProcesos();

					}

				}

				if (dispararNave >= 600) {

					try {
						Thread.sleep(450);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					dispararNave = 75;

				}
			}
		}
	}

}
