import nomox.engine.Sprite;

public class Button {
	ButtonAction buttonAction;
	Sprite sprite;
	int x, y;
	public Button(Sprite sprite, int x, int y, ButtonAction buttonAction) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		this.buttonAction = buttonAction;
	}
	public void draw() {
		sprite.draw(x, y);
	}
	public void click(int x, int y) {
		boolean clicked = (x > this.x &&
				y > this.y &&
				x < this.x + sprite.getWidth() &&
				y < this.y + sprite.getHeight());
		if (clicked)
			buttonAction.click();
	}
}
