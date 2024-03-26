package edu.comp55.burnstd.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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

public class PoisonProjectile extends Projectile {


	Sound poisonExplosion;
	public boolean exploding = false;

	public PoisonProjectile(Enemy enemy, Map map, Tower tower) {
		super(enemy, map, tower, new Sprite(new Texture(Gdx.files.internal("textures/projectile/poison.png"))));

		this.setScale(0.5f);
		this.setMoveSpeed(50);
		this.setLifeTime(12);
		this.setAlpha(0.5f);
		this.setOriginCenter();

		this.setOriginBasedPosition(tower.getCenterX(), tower.getCenterY());


		hit = Gdx.audio.newSound(Gdx.files.internal("sounds/homingProjectile_hit.ogg"));
		fire = Gdx.audio.newSound(Gdx.files.internal("sounds/whoosh.ogg"));
		poisonExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion_gas.ogg"));

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
		this.setMoveSpeed(this.getMoveSpeed() * (float) Math.pow(0.75, delta));


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
				//enemy.damaged();
				hitEnemies.add(enemy);
				enemy.setPoisonDamage(enemy.getPoisonDamage() + this.getDamage());
				float pitch = 1.0f + (float) Math.random() * 0.1f - 0.05f;
				//hit.setPitch(hit.play(0.2f), pitch);
			}
		}

	}

	@Override
	public void update(float delta) {
		super.update(delta);
		testForExplosion();
		if (exploding){
			Projectile explosion = new Explosion(enemy, map, tower);
			explosion.setScale(this.getScaleX());
			explosion.setOriginBasedPosition(this.getCenterX(), this.getCenterY());
			explosion.setDamage(10 * this.getScaleX());
			map.getExplosionHandler().addExplosion(explosion);
			setToRemove(true);
		}
	}

	public void testForExplosion(){
		boolean ignition = false;
		HashSet<PoisonProjectile> linkedProjectiles = new HashSet<PoisonProjectile>();

		// Check for collision with fireball projectiles
		for (Tower t : map.getTowers()){
			if (t instanceof FireballTower){
				for (Projectile p : t.getProjectiles()){
					if (this.getBoundingRectangle().overlaps(p.getBoundingRectangle())){
						ignition = true;
						// Add projectile to list of linked projectiles
						for (Tower poisonTower : map.getTowers()){
							if (poisonTower instanceof PoisonTower){
								for (Projectile linkedP : poisonTower.getProjectiles()){
									if (p.getBoundingRectangle().overlaps(linkedP.getBoundingRectangle()) && !linkedProjectiles.contains(linkedP)){
										linkedProjectiles.add( (PoisonProjectile) linkedP);
										// Continue checking for collisions with other poison projectiles
										while (true) {
											boolean newCollision = false;
											for (Projectile otherP : poisonTower.getProjectiles()) {
												if (linkedP.getBoundingRectangle().overlaps(otherP.getBoundingRectangle()) && !linkedProjectiles.contains(otherP)) {
													linkedProjectiles.add((PoisonProjectile) otherP);
													newCollision = true;
												}
											}
											if (!newCollision) {
												break;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		if (ignition){
			// Explode and remove linked projectiles
			// Set poisonExplosion to be lower pitched the more projectiles are linked
			float pitch = MathUtils.clamp(1.5f - linkedProjectiles.size() * 0.05f, 0.3f, 1.5f);
			poisonExplosion.setPitch(poisonExplosion.play(OptionsScreen.soundVolume * MathUtils.clamp(linkedProjectiles.size() / 50f, 0.3f, 1f)), pitch);


			for (PoisonProjectile p : linkedProjectiles){
				p.setExploding(true);
			}
		}
	}

	public void setExploding(boolean exploding){
		this.exploding = exploding;
	}

	public boolean isExploding(){
		return exploding;
	}

}
