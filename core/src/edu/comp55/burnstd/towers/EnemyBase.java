package edu.comp55.burnstd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import edu.comp55.burnstd.map.Map;

public class EnemyBase extends Tower {

	public EnemyBase(float x, float y, Map map) {
		super(x, y, map, new Sprite(new Texture("textures/towers/enemy_base.png")));
	}

}
