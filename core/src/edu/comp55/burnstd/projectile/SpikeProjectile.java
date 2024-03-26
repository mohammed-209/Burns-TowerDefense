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

public class SpikeProjectile extends Projectile {


	public SpikeProjectile(float angle, Map map, Tower tower) {
		super(null, map, tower, new Sprite(new Texture(Gdx.files.internal("textures/projectile/spike.png"))));

		this.setScale(0.25f);
		this.setMoveSpeed(100);
		this.setLifeTime(15f);

		this.setOriginBasedPosition(tower.getCenterX(), tower.getCenterY());


		hit = Gdx.audio.newSound(Gdx.files.internal("sounds/homingProjectile_hit.ogg"));
		fire = Gdx.audio.newSound(Gdx.files.internal("sounds/bulletProjectile_fire.ogg"));

		float pitch = 1.0f + (float) Math.random() * 0.1f - 0.05f;
		fire.setPitch(fire.play(OptionsScreen.soundVolume), pitch);





		this.setRotation(angle);





	}

	// Move the projectile in the direction of it's angle
	public void move(float delta) {
		this.setX(this.getX() + (float) Math.cos(Math.toRadians(this.getRotation())) * this.getMoveSpeed() * delta);
		this.setY(this.getY() + (float) Math.sin(Math.toRadians(this.getRotation())) * this.getMoveSpeed() * delta);

		if (!tower.getRange().contains(this.getCenterX(), this.getCenterY())) {
			this.setToRemove(true);
		}

	}


	public void damageEnemy() {
		for (Enemy enemy : map.getEnemyList()) {
			if (enemy.getBoundingRectangle().overlaps(getBoundingRectangle())) {
				enemy.damaged();
				enemy.setHealth(enemy.getHealth() - damage);
				float pitch = 1.0f + (float) Math.random() * 0.1f - 0.05f;
				hit.setPitch(hit.play(OptionsScreen.soundVolume), pitch);
				this.setToRemove(true);
			}
		}

	}

}
