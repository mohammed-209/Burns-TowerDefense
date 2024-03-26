package edu.comp55.burnstd.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import edu.comp55.burnstd.map.Map;

public class EnemyTank extends Enemy {
	Texture texture = new Texture(Gdx.files.internal("textures/enemies/tank.png"));
	Sprite sprite = new Sprite(texture);
	EnemyType type = EnemyType.TANK;




	public EnemyTank(float x, float y, float spawnCooldown, Map map) {
		super(x, y, spawnCooldown, map);
		this.set(sprite);
		this.setScale(1.2f);
		this.setMoveSpeed(15);
		this.setMoney(150);
		this.setHealth(1000);
		this.setMaxHealth(1000);
		this.setDamage(300);
		this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
	}
}
