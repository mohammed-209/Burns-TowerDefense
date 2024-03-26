package edu.comp55.burnstd.utility;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class MySprite extends Sprite {

	public float getCenterX() {
		return getX() + getWidth() / 2;
	}

	public float getCenterY() {
		return getY() + getHeight() / 2;
	}

}
