package edu.comp55.burnstd.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import edu.comp55.burnstd.map.Map;

public class EnemyTankBoss extends Enemy {
	Texture texture = new Texture(Gdx.files.internal("textures/enemies/tank_boss.png"));
	Sprite sprite = new Sprite(texture);
	EnemyType type = EnemyType.TANK_BOSS;




	public EnemyTankBoss(float x, float y, float spawnCooldown, Map map) {
		super(x, y, spawnCooldown, map);
		this.set(sprite);
		this.setScale(1f);
		this.setMoveSpeed(8);
		this.setMoney(2000);
		this.setHealth(30000);
		this.setMaxHealth(30000);
		this.setDamage(1000);
		this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
	}
}
