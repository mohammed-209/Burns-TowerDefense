package edu.comp55.burnstd.projectile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import com.badlogic.gdx.math.Polygon;
import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.screens.OptionsScreen;
import edu.comp55.burnstd.towers.Tower;

public class LaserProjectile extends Projectile {

	Polygon hitbox;

	public LaserProjectile(Enemy enemy, Map map, Tower tower) {
		super(enemy, map, tower, new Sprite(new Texture(Gdx.files.internal("textures/projectile/laser.png"))));
		this.setOrigin(this.getWidth()/2, 15);
		this.setLifeTime(0.8f);
		this.setScale(1f);
		this.setOriginBasedPosition(tower.getCenterX(), tower.getCenterY());

		//init hitbox with 4 points from the corners of the sprite
		hitbox = new Polygon(new float[] {0,0, this.getWidth(), 0, this.getWidth(), this.getHeight(), 0, this.getHeight()});

		hit = Gdx.audio.newSound(Gdx.files.internal("sounds/homingProjectile_hit.ogg"));
		fire = Gdx.audio.newSound(Gdx.files.internal("sounds/laser_shot.ogg"));

		float pitch = 1.0f + (float) Math.random() * 0.1f - 0.05f;
		fire.setPitch(fire.play(OptionsScreen.soundVolume), pitch);

		// Damage enemies once
		move(0f);
		this.damageEnemy();
		

	}

	@Override
	public void update(float delta) {
		this.setLifeTime(this.getLifeTime() - delta);
		this.setScale(lifeTime, getScaleY());
		this.draw(map.getLevel().getGameScreen().getBatch());
		if (lifeTime<=0) setToRemove(true);
	}


	public void move(float delta) {
		targetX = enemy.getCenterX();
		targetY = enemy.getCenterY();

		float dx = targetX - tower.getCenterX();
		float dy = targetY - tower.getCenterY();

		float distance = (float) Math.sqrt(dx * dx + dy * dy);
		dx /= distance;
		dy /= distance;

		setRotation(((float) Math.atan2(dy, dx) * MathUtils.radiansToDegrees) - 90);
	}

	public void damageEnemy() {
		for (Enemy enemy : map.getEnemyList()) {
			if (enemy.getBoundingRectangle().overlaps(getBoundingRectangle())) {
				enemy.setHealth(enemy.getHealth() - damage);
				enemy.damaged();
				float pitch = 1.0f + (float) Math.random() * 0.1f - 0.05f;
				hit.setPitch(hit.play(OptionsScreen.soundVolume), pitch);
			}
		}

	}

}
