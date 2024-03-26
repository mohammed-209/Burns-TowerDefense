package edu.comp55.burnstd.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.screens.OptionsScreen;
import edu.comp55.burnstd.towers.Tower;

public class IceProjectile extends Projectile {

	public IceProjectile(Enemy enemy, Map map, Tower tower) {
		super(enemy, map, tower, new Sprite(new Texture(Gdx.files.internal("textures/projectile/snow.png"))));

		this.setScale(1f);
		this.setMoveSpeed(50);
		this.setLifeTime(12);
		this.setAlpha(0.5f);
		this.setOriginCenter();

		this.setOriginBasedPosition(tower.getCenterX(), tower.getCenterY());

		hit = Gdx.audio.newSound(Gdx.files.internal("sounds/homingProjectile_hit.ogg"));
		fire = Gdx.audio.newSound(Gdx.files.internal("sounds/whoosh.ogg"));

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
		this.lifeTimer += delta;
		// Add a random offset to the movement times the amount of time the projectile has lived
		this.setRotation(this.getRotation() + ((float) Math.random() * 100 - 50) * this.getLifeTimer() * delta);


		float moveX = this.getX() + (float) Math.cos(Math.toRadians(this.getRotation())) * this.getMoveSpeed() * delta;
		float moveY = this.getY() + (float) Math.sin(Math.toRadians(this.getRotation())) * this.getMoveSpeed() * delta;

		// Logarithmically slow down the projectile over time
		this.setMoveSpeed(this.getMoveSpeed() * (float) Math.pow(0.65, delta));


		this.setPosition(moveX, moveY);

		if (this.getColor().a > 0f){
			// Slowly fade out the projectile based on the lifeTimer and lifeTime, clamped between 0 and 0.2
			this.setAlpha(MathUtils.clamp(1 - this.getLifeTimer() / this.getLifeTime(), 0, 0.2f));

		}
		this.setScale(this.getScaleX() + this.getLifeTimer() * 0.05f * delta);

	}

	public void damageEnemy() {
		for (Enemy enemy : map.getEnemyList()) {
			if (enemy.getBoundingRectangle().overlaps(getBoundingRectangle()) && !hitEnemies.contains(enemy)) {
				enemy.damaged();
				hitEnemies.add(enemy);
				enemy.setSlowMultiplier(enemy.getSlowMultiplier() - damage/100);
				float pitch = 1.0f + (float) Math.random() * 0.1f - 0.05f;
				//hit.setPitch(hit.play(OptionsScreen.soundVolume), pitch);
			}
		}

	}

}
