package edu.comp55.burnstd.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import edu.comp55.burnstd.towers.Tower;
import edu.comp55.burnstd.utility.MySprite;

public class Tile extends MySprite {

	private float x;
	private float y;
	private Tower tower;
	protected Map map;
	private boolean isOccupied;

	public Tile(float x, float y, float cellWidth, float cellHeight, Map map) {
		this.map = map;
		this.x = x;
		this.y = y;
		this.tower = null;

		this.set(new Sprite(new Texture("textures/screencomponents/UI/tile.png")));
		this.setBounds(x, y, cellWidth, cellHeight);
		this.setAlpha(0.8f);

	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public boolean isOccupied() {
		return isOccupied;
	}
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public Tower getTower() {
		return tower;
	}

	public void setTower(Tower tower) {
		this.tower = tower;
	}

	public Map getMap() {
		return map;
	}

	public void handleClick() {
		System.out.println("Tile clicked: X=" + x + ", Y=" + y);
	}
}