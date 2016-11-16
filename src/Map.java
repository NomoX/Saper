import java.util.Random;

public class Map {
	private final int S_SIZE = 8; // size of xs or ys
	private final int[] xs = {1, 1, 0, -1, -1, -1, 0, 1};
	private final int[] ys = {0, 1, 1, 1, 0, -1, -1, -1};
	
	int width, height;
	boolean[][] bombs;
	boolean[][] visibleBombs;
	boolean[][] flags;
	boolean[][] hidden;
	byte[][] nums;
	
	Random rand = new Random();
	
	public void createField(int width, int height) {
		this.width = width;
		this.height = height;
		
		bombs = new boolean[width][height];
		visibleBombs = new boolean[width][height];
		flags = new boolean[width][height];
		hidden = new boolean[width][height];
		nums = new byte[width][height];
	}
	public void generateBombs(int rate) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int el = rand.nextInt(rate);
				if (el == 0)
					bombs[i][j] = true;
				else
					bombs[i][j] = false;
			}
		}
		
		// gen numbs
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				nums[i][j] = getBombsCount(i, j);
			}
		}
	}
	public void setFlag(int i, int j) {
		flags[i][j] = true;
	}
	public void removeFlag(int i, int j) {
		flags[i][j] = false;
	}
	public void iterateMap(MapIterator mapIterator) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				mapIterator.iterate(i, j);
			}
		}
	}
	
	void openCell(int i, int j) {
		if (hidden[i][j]) return;
		hidden[i][j] = true;
		if (nums[i][j] == 0) {
			int r, c;
			for (int k = 0; k < S_SIZE; k++) {
				r = i + xs[k];
				c = j + ys[k];
				if (r >= 0 && c >= 0 && r < width && c < height) {
					openCell(i + xs[k], j + ys[k]);
				}
			}
		}
	}
	
	/*
	 
	  000
	  0X0
	  000
	 
	 */
	private byte getBombsCount(int i, int j) {
		byte count = 0;
		int r, c;
		for (int k = 0; k < S_SIZE; k++) {
			r = i + xs[k];
			c = j + ys[k];
			if (r >= 0 && c >= 0 && r < width && c < height) {
				if (bombs[r][c])
					count++;
			}
		}
		return count;
	}
}
