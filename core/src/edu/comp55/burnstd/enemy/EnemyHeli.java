package edu.comp55.burnstd.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import edu.comp55.burnstd.map.Map;

public class EnemyHeli extends Enemy {
	Texture texture = new Texture(Gdx.files.internal("textures/enemies/heli.png"));
	Sprite sprite = new Sprite(texture);

	EnemyType type = EnemyType.HELI;

	public EnemyHeli(float x, float y, float spawnCooldown, Map map) {
		super(x, y, spawnCooldown, map);
		this.set(sprite);
		this.setScale(0.4f);
		this.setMoveSpeed(20);
		this.setHealth(1000);
		this.setMaxHealth(1000);
		this.setMoney(100);
		this.setDamage(150);
		this.setOriginCenter();
	}





	@Override
	public void move(float delta) {
		// Check if toX has not been initialized yet (equals -1), set the enemy's position and toX and toY to the first path node's center

		if (toX == -1) {
			setPosition(map.getEnemyBase().getCenterX(), map.getEnemyBase().getCenterY());
			toX = map.getPath().get(currPath).getCenterX();
			toY = map.getPath().get(currPath).getCenterY();
		}

		// Calculate the distance between the enemy's current position and the target position (toX, toY)
		float dx = toX - getX();
		float dy = toY - getY();
		float distance = (float) Math.sqrt(dx * dx + dy * dy);

		// If distance is greater than 0 (enemy has not reached target position), update enemy's position
		if (distance > 0) {

			// Calculate the normalized direction vector towards the target position
			dx /= distance;
			dy /= distance;

			// Calculate the speed based on the enemy's move speed and delta
			float speed = getMoveSpeed() * delta;


			// Calculate the new position based on the direction vector and speed
			float moveX = getX() + dx * speed;
			float moveY = getY() + dy * speed;

			// Set the enemy's new position and rotation angle
			setPosition(moveX, moveY);
			setRotation(((float) Math.atan2(dy, dx) * MathUtils.radiansToDegrees) - 90);

			// Check if the enemy has reached its target position.
			if (Math.abs(moveX - toX) < speed && Math.abs(moveY - toY) < speed) {

				// Increment currPath and check if we've reached the end of the path
				if (currPath < map.getPath().size() - 1) {

					// Set the new target position to the node 10 ahead of the current, plus a random offset of up to 15 units
					for(int i = 0; i < 10; i++){
						if (map.getPath().get(currPath) != map.getPath().getLast()){
							currPath++;
						}
					}


					toX = map.getPath().get(currPath).getCenterX() + (float) (Math.random() * 30) - this.getWidth() * this.getScaleX();
					toY = map.getPath().get(currPath).getCenterY() + (float) (Math.random() * 30) - this.getHeight() * this.getScaleY();
				} else {

					// If we've reached the end of the path, damage the player's base and remove the enemy from the map
					map.getLevel().getMap().getPlayerBase().setHealth(map.getLevel().getMap().getPlayerBase().getHealth() - damage);
					map.getLevel().setEnemiesInWave(map.getLevel().getEnemiesInWave() - 1);
					this.setDead(true);
				}
			}

		}
	}
}
