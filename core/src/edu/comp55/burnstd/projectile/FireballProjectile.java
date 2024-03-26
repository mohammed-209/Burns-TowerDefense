package edu.comp55.burnstd.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.screens.OptionsScreen;
import edu.comp55.burnstd.towers.FireballTower;
import edu.comp55.burnstd.towers.PoisonTower;
import edu.comp55.burnstd.towers.Tower;

import java.util.HashSet;

public class FireballProjectile extends Projectile {


	public FireballProjectile(Enemy enemy, Map map, Tower tower) {
		super(enemy, map, tower, new Sprite(new Texture(Gdx.files.internal("textures/projectile/fireball.png"))));

		this.setScale(1f);
		this.setMoveSpeed(80);
		this.setLifeTime(10);
		this.setOriginCenter();

		this.setOriginBasedPosition(tower.getCenterX(), tower.getCenterY());

		hit = Gdx.audio.newSound(Gdx.files.internal("sounds/homingProjectile_hit.ogg"));
		fire = Gdx.audio.newSound(Gdx.files.internal("sounds/bulletProjectile_fire.ogg"));



		float pitch = 1.0f + (float) Math.random() * 0.1f - 0.05f;
		fire.setPitch(fire.play(OptionsScreen.soundVolume), pitch);

		// Calculate distance between enemy and projectile
		Vector2 enemyPos = new Vector2(enemy.getCenterX(), enemy.getCenterY());
		Vector2 projectilePos = new Vector2(this.getCenterX(), this.getCenterY());
		float distance = enemyPos.dst(projectilePos);

		// Calculate time it would take to reach enemy
		float time = distance / this.getMoveSpeed();

		// Get the predicted enemy position after the time it would take to reach the enemy
		float[] predictedPos = enemy.findPosition(time);

		// Calculate the angle between the projectile and the predicted enemy position
		float angle = MathUtils.atan2(predictedPos[1] - this.getY(), predictedPos[0] - this.getX()) * MathUtils.radiansToDegrees;
		this.setRotation(angle);





	}

	// Move the projectile in the direction of it's angle
	public void move(float delta) {
		this.setX(this.getX() + (float) Math.cos(Math.toRadians(this.getRotation())) * this.getMoveSpeed() * delta);
		this.setY(this.getY() + (float) Math.sin(Math.toRadians(this.getRotation())) * this.getMoveSpeed() * delta);
	}

	public void damageEnemy() {
		for (Enemy enemy : map.getEnemyList()) {
			if (enemy.getBoundingRectangle().overlaps(getBoundingRectangle())) {
				enemy.damaged();
				enemy.setFireDamage(enemy.getFireDamage() + this.getDamage());
				float pitch = 1.0f + (float) Math.random() * 0.1f - 0.05f;
				hit.setPitch(hit.play(OptionsScreen.soundVolume), pitch);
				setToRemove(true);
			}
		}
	}



}
