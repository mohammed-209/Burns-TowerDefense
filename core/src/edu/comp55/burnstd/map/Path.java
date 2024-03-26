package edu.comp55.burnstd.map;

import com.badlogic.gdx.graphics.Texture;

public class Path extends Tile {
	
	public Path(float x, float y, float cellWidth, float cellHeight, Map map) {
		super(x, y, cellWidth, cellHeight, map);
		Texture myTexture = new Texture("textures/screencomponents/UI/path.png");
		this.setTexture(myTexture);
		this.setOriginCenter();



		for (Tile t : map.getTiles()) {
			if (t.getBoundingRectangle().contains(this.getCenterX(), this.getCenterY())) t.setOccupied(true);
		}
	}

	public void checkValidPosition() {
		boolean passed = false;
		for (Tile t : getMap().getTiles()) {
			if (t.getBoundingRectangle().contains(this.getCenterX(), this.getCenterY())) {
				passed = true;
			}
		}

		if (!map.getLevel().getAllowOverlappingPaths()){
			for (Path p : getMap().getPath()) {
				if (getMap().getPath().peekLast() == p) {
					break;
				} else if (p.getBoundingRectangle().contains(this.getCenterX(), this.getCenterY())) {
					passed = false;
				}
			}
		}


		if (!passed) {
			getMap().getPath().remove(getMap().getPath().getLast());
			System.out.println("Cannot place");
		}
		else {
			this.getMap().getLevel().setMoney(this.getMap().getLevel().getMoney() - 50);
		}
	}

}
