package edu.comp55.burnstd.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import edu.comp55.burnstd.map.Map;

public class EnemyStandard extends Enemy {
	Texture texture = new Texture(Gdx.files.internal("textures/enemies/standard.png"));
	Sprite sprite = new Sprite(texture);
	EnemyType type = EnemyType.STANDARD;

	public EnemyStandard(float x, float y, float spawnCooldown, Map map) {
		super(x, y, spawnCooldown, map);
		this.set(sprite);
		this.setScale(1f);
		this.setMoveSpeed(25);
		this.setMoney(25);
		this.setHealth(75);
		this.setMaxHealth(75);
		this.setDamage(150);
		this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
	}
}
