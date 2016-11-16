package nomox.engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Engine extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private boolean running = false;
	// game
	private int width;
	private int height;
	private String name;
	
	private int someKeyDown;
	// public engine params
	public int delay = 10;
	
	// engine units
	static Graphics g; // глобальна змінна, для малювання спрайтів, анімацій і т.д.
	GameManager gm; // інтерфейс з основними функціями (рендер, обнова, нажаття клавіш етц.)
	public JFrame frame;
	
	public Engine(GameManager gm, String name, int width, int height) {
		this.gm = gm;
		this.name = name;
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(this.width, this.height));

		frame = new JFrame(this.name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);

		this.start();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		
		addKeyListener(new KeyInputHandler());
		addMouseListener(new MouseHandler());
		gm.init();
		while(running) {
			long time = System.nanoTime();
			_render();
			gm.update((time - lastTime) / 1000000000.0); // 1000000000.0
			lastTime = time;
			
			try {
				Thread.sleep(delay); // щоб не гризло багато ресурсів
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private void start() {
		running = true;
		new Thread(this).start();
	}
	void _render() {
		BufferStrategy bs = getBufferStrategy(); 
		if (bs == null) {
			createBufferStrategy(2);
			requestFocus();
			return;
		}
			
		g = bs.getDrawGraphics();
		g.setColor(Color.black); // default color
		g.fillRect(0, 0, getWidth(), getHeight());
		gm.render();
		g.dispose();
		bs.show();
	}
	private class KeyInputHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			gm.keyPressed(e.getKeyCode());
			someKeyDown = e.getKeyCode();
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				//leftPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				//rightPressed = true;
			}
		} 
		
		public void keyReleased(KeyEvent e) {
			gm.keyReleased(e.getKeyCode());
			someKeyDown = 0;
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				//leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				//rightPressed = false;
			}
		}
	}
	private class MouseHandler implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			gm.mousePressed(e.getX(), e.getY(), e.getButton());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			gm.mouseReleassed(e.getX(), e.getY(), e.getButton());
		}
		
	}
	public boolean isKeyDown(int keyCode) {
		return (someKeyDown == keyCode);
	}
}
