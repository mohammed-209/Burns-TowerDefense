package edu.comp55.burnstd.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import edu.comp55.burnstd.map.Map;

public class EnemyFast extends Enemy {
	Texture texture = new Texture(Gdx.files.internal("textures/enemies/fast.png"));
	Sprite sprite = new Sprite(texture);

	EnemyType type = EnemyType.FAST;

	public EnemyFast(float x, float y, float spawnCooldown, Map map) {
		super(x, y, spawnCooldown, map);
		this.set(sprite);
		this.setScale(0.5f);
		this.setMoveSpeed(80);
		this.setHealth(10);
		this.setMaxHealth(10);
		this.setMoney(5);
		this.setDamage(50);
		this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
	}
}
