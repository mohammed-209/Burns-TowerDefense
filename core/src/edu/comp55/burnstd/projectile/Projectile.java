package edu.comp55.burnstd.projectile;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;

import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.screens.OptionsScreen;
import edu.comp55.burnstd.towers.Tower;
import edu.comp55.burnstd.utility.MySprite;

import java.util.ArrayList;

public class Projectile extends MySprite {



	protected float moveSpeed;
	protected float damage;
	protected float lifeTime;
	protected float lifeTimer;
	protected Map map;
	protected Enemy enemy;
	protected Tower tower;
	protected float targetX;
	protected float targetY;
	protected Sound hit;
	protected Sound fire;
	private Sprite sprite;
	protected boolean toRemove = false;
	ArrayList hitEnemies;

	public Projectile(Enemy enemy, Map map, Tower tower, Sprite sprite) {
		this.sprite = sprite;
		this.set(sprite);
		this.setPosition(tower.getCenterX(), tower.getCenterY());
		// Convert damage from damage per second to damage per projectile
		this.setDamage(tower.getTowerDamage() / (1 / (tower.getAttackCooldown() * tower.getFireAmount())));

		this.map = map;
		this.enemy = enemy;
		this.tower = tower;
		hitEnemies = new ArrayList<Enemy>();
	}

	public float getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public float getDamage() {
		return damage;
	}
	public void setDamage(float damage) {
		this.damage = damage;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Enemy getEnemy() {
		return enemy;
	}

	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}

	public Tower getTower() {
		return tower;
	}

	public void setTower(Tower tower) {
		this.tower = tower;
	}

	public float getLifeTime() {
		return lifeTime;
	}

	public float getLifeTimer() {
		return lifeTimer;
	}

	public void setLifeTime(float lifeTime) {
		this.lifeTime = lifeTime;
	}

	public void damageEnemy() {
	}

	public void move(float delta) {

	}

    public void update(float delta) {
		this.move(delta);
		this.setLifeTime(this.getLifeTime() - delta);
		this.draw(map.getLevel().getGameScreen().getBatch());
		this.damageEnemy();
		if (this.getLifeTime() <= 0) {
			this.setToRemove(true);
		}
	}

	public void dispose() {
		if (hit != null){
			hit.dispose();
		}
		if (fire != null){
			fire.dispose();
		}
		if (sprite.getTexture() != null){
			sprite.getTexture().dispose();
		}

	}


	public boolean isToRemove() {
		return toRemove;
	}

	public void setToRemove(boolean toRemove) {
		this.toRemove = toRemove;
	}


}
