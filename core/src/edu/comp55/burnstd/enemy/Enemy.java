package edu.comp55.burnstd.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.utility.MySprite;

public class Enemy extends MySprite {

	protected float health;
	protected float maxHealth;
	protected float moveSpeed;
	protected int damage;
	protected float angle;
	protected float spawnCooldown;
	protected int currPath;
	protected Map map;
	protected float toX;
	protected float toY;
	protected int money;
	protected boolean dead;
	private boolean hurt;
	protected Color defaultColor = new Color(Color.WHITE);
	protected Color slowColor = new Color(Color.BLUE);
	protected Color damageColor = new Color(0.25f, 0.25f, 0.25f, 1);
	protected Color poisonColor = new Color(Color.GREEN);
	protected Color fireColor = new Color(Color.ORANGE);
	protected float slowMultiplier = 1;
	protected Color tint = new Color(Color.WHITE);

	protected Color levelColor1 = new Color(Color.GREEN);
	protected Color levelColor2 = new Color(Color.YELLOW);
	protected Color levelColor3 = new Color(Color.RED);
	protected Color levelColor4 = new Color(Color.PURPLE);
	protected Color levelColor5 = new Color(Color.ORANGE);
	protected Color levelColor6 = new Color(Color.BLUE);
	protected Color levelColor7 = new Color(Color.CYAN);
	protected Color levelColor8 = new Color(Color.MAGENTA);
	protected Color levelColor9 = new Color(Color.GOLD);
	protected Color levelColor10 = new Color(Color.FIREBRICK);

	protected float poisonDamage = 0;
	private float poisonTimer;
	protected float fireDamage = 0;
	private float fireTimer;
	protected int level;

	public Enemy(float x, float y, float spawnCooldown, Map map) {
		this.spawnCooldown = spawnCooldown;
		currPath = 0;
		this.map = map;
		toX = -1;
		toY = -1;
		dead = false;
		this.setOriginCenter();
	}

	public void move(float delta) {
		// Check if toX has not been initialized yet (equals -1), set the enemy's position and toX and toY to the first path node's center

		if (toX == -1) {
			setOriginBasedPosition(map.getEnemyBase().getCenterX(), map.getEnemyBase().getCenterY());
			toX = map.getPath().get(currPath).getCenterX();
			toY = map.getPath().get(currPath).getCenterY();
		}

		// Calculate the distance between the enemy's current position and the target position (toX, toY)
		float dx = toX - getCenterX();
		float dy = toY - getCenterY();
		float distance = (float) Math.sqrt(dx * dx + dy * dy);
		
		// If distance is greater than 0 (enemy has not reached target position), update enemy's position
		if (distance > 0) {
			
			// Calculate the normalized direction vector towards the target position
			dx /= distance;
			dy /= distance;
			
			// Calculate the speed based on the enemy's move speed and delta
			float speed = getMoveSpeed() * delta;

			
			// Calculate the new position based on the direction vector and speed
			float moveX = getCenterX() + dx * speed;
			float moveY = getCenterY() + dy * speed;
			
			// Set the enemy's new position and rotation angle
			setOriginBasedPosition(moveX, moveY);
			setRotation(((float) Math.atan2(dy, dx) * MathUtils.radiansToDegrees) - 90);

			// Check if the enemy has reached its target position.
			if (Math.abs(moveX - toX) < speed && Math.abs(moveY - toY) < speed) {
				
				// Increment currPath and check if we've reached the end of the path
				if (currPath < map.getPath().size() - 1) {
					
					// Set the new target position to the next path node's center plus a random offset of up to 15 units
					currPath++;
					toX = map.getPath().get(currPath).getCenterX() + MathUtils.random(-15, 15);
					toY = map.getPath().get(currPath).getCenterY() + MathUtils.random(-15, 15);

				} else {

					// If we've reached the end of the path, damage the player's base and remove the enemy from the map
					map.getLevel().getMap().getPlayerBase().setHealth(map.getLevel().getMap().getPlayerBase().getHealth() - damage);
					map.getLevel().setEnemiesInWave(map.getLevel().getEnemiesInWave() - 1);
					this.setDead(true);
				}
			}

		}
	}

	public void setLevel(int level) {
		if (level > 0){
			level1();
			tint = levelColor1;
			defaultColor = levelColor1;
		}
		if (level > 1){
			level2();
			tint = levelColor2;
			defaultColor = levelColor2;
		}
		if (level > 2){
			level3();
			tint = levelColor3;
			defaultColor = levelColor3;
		}
		if (level > 3){
			level4();
			tint = levelColor4;
			defaultColor = levelColor4;
		}
		if (level > 4){
			level5();
			tint = levelColor5;
			defaultColor = levelColor5;
		}
		if (level > 5){
			level6();
			tint = levelColor6;
			defaultColor = levelColor6;
		}
		if (level > 6){
			level7();
			tint = levelColor7;
			defaultColor = levelColor7;
		}
		if (level > 7){
			level8();
			tint = levelColor8;
			defaultColor = levelColor8;
		}
		if (level > 8){
			level9();
			tint = levelColor9;
			defaultColor = levelColor9;
		}
		if (level > 9){
			level10();
			tint = levelColor10;
			defaultColor = levelColor10;
		}
		this.level = level;
	}

	public int getLevel() {
		return level;
	}


	public void level1(){
		this.setHealth(this.getHealth() * 3f);
		this.setMaxHealth(this.getMaxHealth() * 3f);
		this.setDamage((int) (this.getDamage() * 1.2f));
		this.setMoney((int) (this.getMoney() * 1.25f));
	}

	public void level2(){
		this.setHealth(this.getHealth() * 3f);
		this.setMaxHealth(this.getMaxHealth() * 3f);
		this.setDamage((int) (this.getDamage() * 1.2f));
		this.setMoney((int) (this.getMoney() * 1.25f));
	}

	public void level3(){
		this.setHealth(this.getHealth() * 3f);
		this.setMaxHealth(this.getMaxHealth() * 3f);
		this.setDamage((int) (this.getDamage() * 1.2f));
		this.setMoney((int) (this.getMoney() * 1.25f));
	}

	public void level4(){
		this.setHealth(this.getHealth() * 3f);
		this.setMaxHealth(this.getMaxHealth() * 3f);
		this.setDamage((int) (this.getDamage() * 1.2f));
		this.setMoney((int) (this.getMoney() * 1.25f));
	}

	public void level5(){
		this.setHealth(this.getHealth() * 3f);
		this.setMaxHealth(this.getMaxHealth() * 3f);
		this.setDamage((int) (this.getDamage() * 1.2f));
		this.setMoney((int) (this.getMoney() * 1.25f));
	}

	public void level6(){
		this.setHealth(this.getHealth() * 3f);
		this.setMaxHealth(this.getMaxHealth() * 3f);
		this.setDamage((int) (this.getDamage() * 1.2f));
		this.setMoney((int) (this.getMoney() * 1.25f));
	}

	public void level7(){
		this.setHealth(this.getHealth() * 3f);
		this.setMaxHealth(this.getMaxHealth() * 3f);
		this.setDamage((int) (this.getDamage() * 1.2f));
		this.setMoney((int) (this.getMoney() * 1.25f));
	}

	public void level8(){
		this.setHealth(this.getHealth() * 3f);
		this.setMaxHealth(this.getMaxHealth() * 3f);
		this.setDamage((int) (this.getDamage() * 1.2f));
		this.setMoney((int) (this.getMoney() * 1.25f));
	}

	public void level9(){
		this.setHealth(this.getHealth() * 3f);
		this.setMaxHealth(this.getMaxHealth() * 3f);
		this.setDamage((int) (this.getDamage() * 1.2f));
		this.setMoney((int) (this.getMoney() * 1.25f));
	}

	public void level10(){
		this.setHealth(this.getHealth() * 3f);
		this.setMaxHealth(this.getMaxHealth() * 3f);
		this.setDamage((int) (this.getDamage() * 1.2f));
		this.setMoney((int) (this.getMoney() * 1.25f));
	}





	public void update(float delta) {
		poisonUpdate(delta);
		fireUpdate(delta);
		setTintColor();
		setColor(tint);
		move(delta);
		updateSlowMultiplier(delta);
	}

	// Mix all the status colors together to get the final tint color
	public void setTintColor(){
		tint.set(defaultColor);
		tint.lerp(slowColor, 1 - slowMultiplier);
		tint.lerp(poisonColor, poisonDamage/(this.getHealth()/10));
		tint.lerp(fireColor, fireDamage/15);
		tint.lerp(damageColor, 1 - this.getHealth()/this.getMaxHealth());

		if (hurt) {
			tint.set(Color.RED);
		}
	}

	// Slowly return the movement multiplier to 1 over time
	public void updateSlowMultiplier(float delta) {
		if (slowMultiplier < 1) {
			setSlowMultiplier(getSlowMultiplier() + delta * 0.008f);
		}
	}

	public void poisonUpdate(float delta){
		poisonTimer+=delta;
		if (poisonDamage > 0 && poisonTimer > 1){
			poisonTimer = 0;
			this.damaged();
			this.setHealth(this.getHealth() - poisonDamage);
			//Reduce poison damage by 10%
			poisonDamage *= 0.9;
			if (poisonDamage < 1){
				poisonDamage = 0;
			}
		}
	}

	public void fireUpdate(float delta){
		fireTimer+=delta;
		if (fireDamage > 0 && fireTimer > 1){
			fireTimer = 0;
			this.damaged();
			this.setHealth(this.getHealth() - fireDamage);
			//Reduce poison damage by 10%
			fireDamage *= 0.75;
			if (fireDamage < 1){
				fireDamage = 0;
			}

			// Spread fire to nearby enemies
			for (Enemy enemy : map.getEnemyList()) {
				if (enemy.getBoundingRectangle().overlaps(this.getBoundingRectangle())) {
					enemy.setFireDamage(fireDamage);
				}
			}
		}
	}


	public void setFireDamage(float fireDamage) {
		if (this.fireDamage < 1){
			this.fireDamage = 1;
		}
		else{
			this.fireDamage = fireDamage;
		}
	}

	public float getFireDamage() {
		return fireDamage;
	}

	// Find enemy potential position after time
	public float[] findPosition(float time) {
		float[] position = new float[2];
		float dx = toX - getX();
		float dy = toY - getY();
		float distance = (float) Math.sqrt(dx * dx + dy * dy);
		if (distance > 0) {
			dx /= distance;
			dy /= distance;
			float speed = getMoveSpeed() * time;
			float moveX = getX() + dx * speed;
			float moveY = getY() + dy * speed;
			position[0] = moveX;
			position[1] = moveY;
		}
		return position;
	}

	public float getSpawnCooldown() {
		return spawnCooldown;
	}

	public void setSpawnCooldown(float spawnCooldown) {
		this.spawnCooldown = spawnCooldown;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
		if (getHealth() <= 0) {
			die();
		}
	}

	public void damaged() {
		hurt = true;
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				hurt = false;
			}
		}, 0.1f);
	}

	public float getMoveSpeed() {
		return moveSpeed * slowMultiplier;
	}

	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public void die() {
		if (!isDead()){
			map.getLevel().setMoney(map.getLevel().getMoney() + money);
			map.getLevel().setScore(map.getLevel().getScore() + money);
			setDead(true);
			map.getLevel().setEnemiesInWave(map.getLevel().getEnemiesInWave() - 1);
		}

	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public Vector2 getVector2() {
		return new Vector2(getX(), getY());
	}

	public float getSlowMultiplier() {
		return slowMultiplier;
	}

	public void setSlowMultiplier(float slowMultiplier) {
		this.slowMultiplier = MathUtils.clamp(slowMultiplier, 0.25f, 1);
	}

	public void dispose() {
	}

	protected void setMaxHealth(float health) {
		maxHealth = health;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public void setPoisonDamage(float poisonDamage) {
		if (this.poisonDamage < 1){
			this.poisonDamage = 1;
		}
		else {
			this.poisonDamage = poisonDamage;
		}
	}

	public float getPoisonDamage() {
		return poisonDamage;
	}
}