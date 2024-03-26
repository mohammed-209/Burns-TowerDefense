package edu.comp55.burnstd.utility;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyActor extends Actor {

	public float getCenterX() {
		return getX() + getWidth() / 2;
	}

	public float getCenterY() {
		return getY() + getHeight() / 2;
	}

}
