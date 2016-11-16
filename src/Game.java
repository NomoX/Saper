import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import nomox.engine.Engine;
import nomox.engine.GameManager;
import nomox.engine.Sprite;

public class Game implements GameManager {
	private static Engine engine;
	
	private final int CELL_SIZE = 24;
	
	Map map;
	
	Sprite menu_bar, sub_menu_bar;
	Sprite help, game_over, win;
	Sprite b_menu, b_play, b_retry, b_exit, b_help, b_easy, b_norm, b_hard, b_diff, b_back;
	
	Sprite mine;
	Sprite flag;
	Sprite hidden_cell;
	Sprite empty_cell;
	Sprite s_one, s_two, s_three, s_four, s_five, s_six, s_seven, s_eight;
	
	Sprite end_game = game_over;
	
	int last_diff = 8;
	int curr_menu = 1;
	List<Button> buttons_menu;
	List<Button> buttons_play;
	List<Button> buttons_help;
	List<Button> buttons_gameover;
	
	public static void main(String[] args) {
		engine = new Engine(new Game(), "Saper 2016", 470, 614); // offset -10
		engine.delay = 10;
		//engine.frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Game.class.getResource("icon.png")));
	}

	@Override
	public void init() {
		menu_bar = new Sprite().fromResources("/resources/gui/bar.png");
		sub_menu_bar = new Sprite().fromResources("/resources/gui/bar_sub.png");
		help = new Sprite().fromResources("/resources/gui/help.png");
		game_over = new Sprite().fromResources("/resources/gui/game_over.png");
		win = new Sprite().fromResources("/resources/gui/win.png");
		b_menu = new Sprite().fromResources("/resources/gui/b_menu.png");
		b_play = new Sprite().fromResources("/resources/gui/b_play.png");
		b_retry = new Sprite().fromResources("/resources/gui/b_retry.png");
		b_exit = new Sprite().fromResources("/resources/gui/b_exit.png");
		b_help = new Sprite().fromResources("/resources/gui/b_help.png");
		b_easy = new Sprite().fromResources("/resources/gui/b_easy.png");
		b_norm = new Sprite().fromResources("/resources/gui/b_norm.png");
		b_hard = new Sprite().fromResources("/resources/gui/b_hard.png");
		b_diff = new Sprite().fromResources("/resources/gui/b_diff.png");
		b_back = new Sprite().fromResources("/resources/gui/b_back.png");
		
		mine = new Sprite().fromResources("/resources/mine.png");
		flag = new Sprite().fromResources("/resources/flag.png");
		hidden_cell = new Sprite().fromResources("/resources/hidden.png");
		empty_cell = new Sprite().fromResources("/resources/empty.png");
		s_one = new Sprite().fromResources("/resources/one.png");
		s_two = new Sprite().fromResources("/resources/two.png");
		s_three = new Sprite().fromResources("/resources/three.png");
		s_four = new Sprite().fromResources("/resources/four.png");
		s_five = new Sprite().fromResources("/resources/five.png");
		s_six = new Sprite().fromResources("/resources/six.png");
		s_seven = new Sprite().fromResources("/resources/seven.png");
		s_eight = new Sprite().fromResources("/resources/eight.png");
		
		buttons_menu = new ArrayList<>();
		buttons_play = new ArrayList<>();
		buttons_help = new ArrayList<>();
		buttons_gameover = new ArrayList<>();
		
		buttons_menu.add(new Button(b_play, 160, 150, () -> {curr_menu = 2;}));
		buttons_menu.add(new Button(b_help, 160, 200, () -> {curr_menu = 3;}));
		buttons_menu.add(new Button(b_exit, 160, 250, () -> {System.exit(0);}));
		
		buttons_play.add(new Button(b_easy, 160, 170, () -> {playGame(12);}));
		buttons_play.add(new Button(b_norm, 160, 220, () -> {playGame(8);}));
		buttons_play.add(new Button(b_hard, 160, 270, () -> {playGame(5);}));
		buttons_play.add(new Button(b_back, 20, 295, () -> {curr_menu = 1;}));
		
		buttons_help.add(new Button(b_back, 20, 295, () -> {curr_menu = 1;}));
		
		buttons_gameover.add(new Button(b_menu, 20, 295, () -> {curr_menu = 1;}));
		buttons_gameover.add(new Button(b_retry, 280, 295, () -> {playGame(last_diff);}));
		
		map = new Map();
		map.createField(20, 26);
		map.generateBombs(8);
	}

	@Override
	public void render() {
		map.iterateMap((i, j) -> {
			// cells and nums
			if (map.hidden[i][j]) {
				// cell
				empty_cell.draw(i * CELL_SIZE, j * CELL_SIZE);
				// num
				if (map.nums[i][j] != 0)
					getSpriteNum(map.nums[i][j]).draw(i * CELL_SIZE, j * CELL_SIZE);
			}
			else
				hidden_cell.draw(i * CELL_SIZE, j * CELL_SIZE);
			
			// bombs
			if (map.bombs[i][j] && map.visibleBombs[i][j])
				mine.draw(i * CELL_SIZE, j * CELL_SIZE);
			// flags
			if (map.flags[i][j])
				flag.draw(i * CELL_SIZE, j * CELL_SIZE);
		});
		
		if (curr_menu == 1) {
			menu_bar.draw(40, 100);
			
			for (Button b : buttons_menu) {
				b.draw();
			}
		}
		else if (curr_menu == 2) {
			sub_menu_bar.draw(40, 100);
			
			b_diff.draw(160, 110);
			for (Button b : buttons_play) {
				b.draw();
			}
		}
		else if (curr_menu == 3) {
			sub_menu_bar.draw(40, 100);
			help.draw(40, 100);
			
			b_help.draw(160, 110);
			for (Button b : buttons_help) {
				b.draw();
			}
		}
		else if (curr_menu == 4) {
			menu_bar.draw(40, 100);
			
			end_game.draw(40, 100);
			
			for (Button b : buttons_gameover) {
				b.draw();
			}
		}
	}

	@Override
	public void update(double delta) {
		
	}

	@Override
	public void keyPressed(int keyCode) {
		
	}

	@Override
	public void keyReleased(int keyCode) {
		
	}

	@Override
	public void mousePressed(int x, int y, int button) {
		Point pos = getCellAtPos(x, y);
		if (curr_menu == 0) {
			int i = pos.x;
			int j = pos.y;
			if (button == MouseEvent.BUTTON1) {
				if (engine.isKeyDown(KeyEvent.VK_Z))
					if (!map.hidden[i][j] && map.bombs[i][j])
						return;
				
				if (map.flags[i][j])
					return;
					//map.flags[i][j] = false;
				map.openCell(i, j);
	
				// loose
				if (map.bombs[i][j]) {
					map.visibleBombs[i][j] = true;
					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					openAllBombs();
					curr_menu = 4;
					end_game = game_over;
				}
				
			}
			if (button == MouseEvent.BUTTON3) {
				if (!map.hidden[i][j])
					map.flags[i][j] = !map.flags[i][j]; // invert
				if (checkForWin()) {
					curr_menu = 4;
					end_game = win;
				}
			}
		}
		if (button == MouseEvent.BUTTON1) {
			if (curr_menu == 1) {
				for (Button b : buttons_menu) {
					b.click(x, y);
				}
			}
			else if (curr_menu == 2) {
				for (Button b : buttons_play) {
					b.click(x, y);
				}
			}
			else if (curr_menu == 3) {
				for (Button b : buttons_help) {
					b.click(x, y);
				}
			}
			else if (curr_menu == 4) {
				for (Button b : buttons_gameover) {
					b.click(x, y);
				}
			}
		}
		
	}

	@Override
	public void mouseReleassed(int x, int y, int button) {
		
	}
	
	Point getCellAtPos(int x, int y) {
		return new Point(x / CELL_SIZE, y / CELL_SIZE);
	}
	Sprite getSpriteNum(byte num) {
		switch (num) {
		case 1:
			return s_one;
		case 2:
			return s_two;
		case 3:
			return s_three;
		case 4:
			return s_four;
		case 5:
			return s_five;
		case 6:
			return s_six;
		case 7:
			return s_seven;
		case 8:
			return s_eight;
		}
		return null;
	}
	boolean checkForWin() {
		for (int i = 0; i < map.width; i++) {
			for (int j = 0; j < map.height; j++) {
				if (map.bombs[i][j] != map.flags[i][j])
					return false;
			}
		}
		return true;
	}
	void openAllBombs() {
		for (int i = 0; i < map.width; i++) {
			for (int j = 0; j < map.height; j++) {
				if (map.bombs[i][j]) {
					map.hidden[i][j] = true;
					map.visibleBombs[i][j] = true;
				}
			}
		}
	}
	void playGame(int diff) {
		last_diff = diff;
		curr_menu = 0;
		map.createField(20, 26);
		map.generateBombs(diff);
	}
}
