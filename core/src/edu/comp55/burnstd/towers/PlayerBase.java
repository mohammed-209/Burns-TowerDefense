package edu.comp55.burnstd.towers;

import javax.management.loading.PrivateClassLoader;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray.IntChannel;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.bullet.collision.integer_comparator;

import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Level;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.screens.LoseScreen;
import edu.comp55.burnstd.screens.UpgradeScreen;


public class PlayerBase extends Tower{
	int health;

	Map map;

	public PlayerBase(float x, float y, Map map) {
		super(x, y, map, new Sprite(new Texture("textures/towers/burns_tower.png")));
		this.map = map;
		this.setHealth((int)(1000 * UpgradeScreen.getUpgradeHealth()));
	}

	public void setHealth(int h) {
		health = h;
		if (health <= 0) {
			map.getLevel().getGameScreen().setLost(true);
			health = 0;
		}
	}

	public int getHealth () {
		return health;
	}


}
