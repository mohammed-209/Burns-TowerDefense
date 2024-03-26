package edu.comp55.burnstd.projectile;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.screens.OptionsScreen;
import edu.comp55.burnstd.towers.Tower;

public class HomingProjectile extends Projectile {


	public HomingProjectile(Enemy enemy, Map map, Tower tower) {
		super(enemy, map, tower, new Sprite(new Texture(Gdx.files.internal("textures/projectile/homing_missile.png"))));

		this.setScale(1f);
		this.setMoveSpeed(80);
		this.setLifeTime(10);

		this.setOriginBasedPosition(tower.getCenterX(), tower.getCenterY());

		hit = Gdx.audio.newSound(Gdx.files.internal("sounds/homingProjectile_hit.ogg"));
		fire = Gdx.audio.newSound(Gdx.files.internal("sounds/homingProjectile_fire.ogg"));


		float pitch = 1.0f + (float) Math.random() * 0.1f - 0.05f;
		fire.setPitch(fire.play(OptionsScreen.soundVolume), pitch);

	}

	public void move(float delta) {

		// Change target if enemy is dead
		if (!map.getEnemyList().contains(enemy)) {
			ArrayList<Enemy> enemies = new ArrayList<Enemy>();
			for (Enemy e : map.getEnemyList()) {
				if (this.tower.getRange().contains(e.getX(), e.getY())) {
					enemies.add(e);
				}
			}
			if (!enemies.isEmpty()) {
				this.setEnemy(enemies.get(new Random().nextInt(enemies.size())));

			}

		}

		// Spazz out if no enemies are in range.
		// NEED TO FIX: Stops spazzing when a new enemy spawns even if the new enemy is
		// not in range
		if (!map.getEnemyList().isEmpty()) {
			targetX = enemy.getCenterX();
			targetY = enemy.getCenterY();
		} else {
			targetX = targetX + (float) (Math.random() * 10) - 5;
			targetY = targetY + (float) (Math.random() * 10) - 5;
		}

		float dx = targetX - getCenterX();
		float dy = targetY - getCenterY();

		float distance = (float) Math.sqrt(dx * dx + dy * dy);
		if (distance > 0) {
			dx /= distance;
			dy /= distance;
			float speed = moveSpeed * delta;
			float moveX = getX() + dx * speed;
			float moveY = getY() + dy * speed;
			setPosition(moveX, moveY);
			setRotation((float) Math.atan2(dy, dx) * MathUtils.radiansToDegrees);
		}

	}


	public void damageEnemy() {
		for (Enemy enemy : map.getEnemyList()) {
			if (enemy.getBoundingRectangle().overlaps(getBoundingRectangle())) {
				Explosion e = new Explosion(enemy, map, tower);
				e.setOriginBasedPosition(this.getCenterX(), this.getCenterY());
				e.setScale(e.getScaleX() * this.getDamage() / 50);
				e.setDamage(this.getDamage());
				map.getExplosionHandler().addExplosion(e);
				float pitch = 1.0f + (float) Math.random() * 0.1f - 0.05f;
				hit.setPitch(hit.play(OptionsScreen.soundVolume), pitch);
				setToRemove(true);
			}
		}

	}

}
