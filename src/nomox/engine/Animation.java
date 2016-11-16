package nomox.engine;

import java.util.ArrayList;
import java.util.List;

public class Animation {
	private List<Sprite> frames; // список фрейймів
	private int current_frame = 0; // поточний фрейм (index)
	private boolean loop;
	private int delay;
	private boolean is_play = false;
	
	public Animation() {
		this.frames = new ArrayList<Sprite>();
		this.loop = false;
		this.delay = 500;
	}
	public Animation(int delay) {
		this.frames = new ArrayList<Sprite>();
		this.loop = false;
		this.delay = delay;
	}
	public Animation(int delay, boolean loop) {
		this.frames = new ArrayList<Sprite>();
		this.loop = loop;
		this.delay = delay;
	}
	// params
	public void loop(boolean loop) {
		this.loop = loop;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	public boolean isPlay() {
		return is_play;
	}
	// control
	public void play() {
		is_play = true;
		// tip! залити в тред
		// час анімації рахувати окремо, щоб не було розсинкрону
		Thread thread = new Thread() {
			public void run() {
				while (is_play) {
					current_frame++;
					if (current_frame >= frames.size()) {
						current_frame = 0;
						if (!loop) Animation.this.stop();
					}
					try {
						sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
	}
	public void pause() {
		is_play = false;
	}
	public void stop() {
		// якщо вертаєм на початок тут встановлюється перший кадр
		pause();
		current_frame = 0; // якщо вертаєм на початок тут то встановлюєть останній кадр який був показаний
	}
	// frames
	public void addFrame(Sprite sprite) {
		frames.add(sprite);
	}
	public Sprite getFrame(int index) {
		return frames.get(index);
	}
	public void removeFrame(int index) {
		frames.remove(index);
	}
	public void removeAllFrames() {
		frames.clear();
	}
	public void setFrame(int index) {
		current_frame = index;
	}
	public int currentFrame() {
		return current_frame;
	}
	public void draw(int x, int y) {
		frames.get(current_frame).draw(x, y);
	}
}
