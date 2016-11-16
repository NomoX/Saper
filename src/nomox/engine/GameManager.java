package nomox.engine;

public interface GameManager {
	public void init();
	public void render();
	public void update(double delta);
	public void keyPressed(int keyCode);
	public void keyReleased(int keyCode);
	public void mousePressed(int x, int y, int button);
	public void mouseReleassed(int x, int y, int button);
}
