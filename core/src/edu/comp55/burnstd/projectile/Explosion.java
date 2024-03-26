package edu.comp55.burnstd.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.towers.Tower;

import java.util.ArrayList;

public class Explosion extends Projectile {


	public Explosion(Enemy enemy, Map map, Tower tower) {
		super(enemy, map, tower, new Sprite(new Texture(Gdx.files.internal("textures/projectile/explosion.png"))));

		this.setScale(1f);
		this.setDamage(5);
		this.setMoveSpeed(0);
		this.setLifeTime(0.3f);
		this.setOriginCenter();
		this.setRotation(MathUtils.random(0, 360));

		this.setOriginBasedPosition(tower.getCenterX(), tower.getCenterY());

	}

	public void move(float delta) {
		this.setScale(this.getScaleX() - this.getScaleX() * delta * 3);
		this.setColor(1, 1, 1, this.getColor().a - delta * 5);

	}

	public void damageEnemy() {
		ArrayList<Enemy> damagePool = new ArrayList<Enemy>();
		for (Enemy enemy : map.getEnemyList()) {
			if (enemy.getBoundingRectangle().overlaps(getBoundingRectangle()) && !hitEnemies.contains(enemy)) {
				damagePool.add(enemy);
			}
		}

		// Damage all enemies in the damage pool with a reduced damage depending on how many enemies are in the pool
		float poolDamage = this.getDamage() / damagePool.size();

		for (Enemy enemy : damagePool) {
			enemy.damaged();
			hitEnemies.add(enemy);
			enemy.setHealth(enemy.getHealth() - poolDamage);
			float pitch = 1.0f + (float) Math.random() * 0.1f - 0.05f;
		}
	}



}
