package edu.comp55.burnstd.towers;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.map.Tile;
import edu.comp55.burnstd.projectile.Projectile;
import edu.comp55.burnstd.utility.MySprite;

public class Tower extends MySprite {
	protected TowerTypes type;
	protected float health;
	protected float damageMult;
	protected float rangeRadius;
	protected int cost;
	protected Map map;
	protected Circle range;
	protected ArrayList<Projectile> projectiles;
	protected float attackCooldown;
	protected float attackTimer;
	protected Enemy currTarget;
	protected Tile tile;

	protected int fireAmount;

	protected int towerLevel = 0;
	protected ShapeRenderer rangeRenderer = new ShapeRenderer();

	protected Color levelTint1 = new Color(Color.GREEN).add(0.5f, 0.5f, 0.5f, 1);
	protected Color levelTint2 = new Color(Color.BLUE).add(0.5f, 0.5f, 0.5f, 1);
	protected Color levelTint3 = new Color(Color.RED).add(0.5f, 0.5f, 0.5f, 1);

	protected int towerUpgradeCost;

	Skin skin = new Skin(Gdx.files.internal("skins/testskin/test.json"));
	Window w;
	TextButton upgrade, sell, cancel;

	public Tower(float x, float y, Map map, Sprite sprite) {
		projectiles = new ArrayList<Projectile>();
		this.map = map;
		this.set(sprite);
		this.setTowerRange(1);
		this.setBounds(x, y, map.cellWidth(), map.cellHeight());
		for (Tile t : map.getTiles()) {
			if (t.getBoundingRectangle().contains(this.getX(), this.getY())) {
				this.tile = t;
				t.setOccupied(true);
			}
		}
		this.setPosition(tile.getX(), tile.getY());
	}

	public void spawnProjectile(Enemy enemy) {
	}

	public void updateProjectile(Float delta) {
	}

	public void update(float delta) {
		towerAttack(delta);
		updateProjectile(delta);
		cleanProjectiles();

		float x = Gdx.input.getX();
        float y = Gdx.input.getY();
		Vector2 coords = map.getLevel().getGameScreen().getStage().screenToStageCoordinates(new Vector2(x, y));

        if (this.getBoundingRectangle().contains(coords.x, coords.y) && Gdx.input.justTouched() && w == null) {
			w = new Window("Tower Menu", skin);
			System.out.println("Tower Menu Opened");

			w.setBounds((int)coords.x, (int)coords.y, 250, 250);
            w.setMovable(false);
            w.setPosition(coords.x, coords.y);
            map.getLevel().getGameScreen().getStage().addActor(w);
            
            upgrade = new TextButton("Upgrade", skin);
            sell = new TextButton("Sell",skin);
            cancel = new TextButton ("Cancel", skin);
            
            
            upgrade.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					upgradeTower();
					System.out.println("Tower UPGRADED");
					w.remove();
					w = null;
					System.out.println("Tower Menu Closed");
				}
            });
            
            sell.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					sellTower();
					System.out.println("Tower SOLD");
					w.remove();
					w = null;
					System.out.println("Tower Menu Closed");
				}
            });
            
            cancel.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					w.remove();
					w = null;
					System.out.println("Tower Menu Closed");
				}
            });
            
            w.add(upgrade).row();
            w.add(sell).row();
            w.add(cancel).row();
        }
	}

	// Draw the range circle to show the range of the tower on screen
	public void drawRange() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		rangeRenderer.setProjectionMatrix(map.getLevel().getGameScreen().getBatch().getProjectionMatrix());
		rangeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		rangeRenderer.setColor(0, 0, 1, 0.05f);
		rangeRenderer.circle(this.getCenterX(), this.getCenterY(), range.radius);
		rangeRenderer.end();

		rangeRenderer.begin(ShapeRenderer.ShapeType.Line);
		rangeRenderer.setColor(0, 0, 0, 0.3f);
		rangeRenderer.circle(this.getCenterX(), this.getCenterY(), range.radius);
		rangeRenderer.end();

		Gdx.gl.glDisable(GL20.GL_BLEND);
	}


	public Circle getRange() {
		return range;
	}

	public void updateProjectile(float delta) {
		for (Projectile projectile : projectiles) {
			projectile.update(delta);
		}
	}

	public void towerAttack(float delta) {
		attackTimer += delta;
		ArrayList<Enemy> tempArray = new ArrayList<Enemy>();
		Enemy enemy = getClosestEnemy();
		if (attackTimer >= attackCooldown && enemy != null) {
			spawnProjectile(enemy);
			attackTimer = 0f;
		}
	}

	public void upgradeTower() {
		if (map.getLevel().getMoney() < this.getTowerUpgradeCost() || towerLevel >= 3) {
			return;
		}

		map.getLevel().setMoney(map.getLevel().getMoney() - this.getTowerUpgradeCost());
		towerLevel++;
		switch (towerLevel) {
			case 1:
				upgradeLevel1();
				break;
			case 2:
				upgradeLevel2();
				break;
			case 3:
				upgradeLevel3();
				break;
		}
	}

	public void upgradeLevel1() {
		this.setColor(levelTint1);
		this.setTowerCost((int) (this.getTowerCost() * 1.5));
		this.setTowerDamage(this.getTowerDamage() * 2f);
		this.setTowerRange((int) (this.getRange().radius * 1.5f));
		this.setAttackCooldown(this.getAttackCooldown() * 0.9f);
	}

	public void upgradeLevel2() {
		this.setColor(levelTint2);
		this.setTowerCost((int) (this.getTowerCost() * 1.5));
		this.setTowerDamage(this.getTowerDamage() * 2f);
		this.setTowerRange((int) (this.getRange().radius * 1.5f));
		this.setAttackCooldown(this.getAttackCooldown() * 0.9f);
	}

	public void upgradeLevel3() {
		this.setColor(levelTint3);
		this.setTowerCost((int) (this.getTowerCost() * 1.5));
		this.setTowerDamage(this.getTowerDamage() * 2f);
		this.setTowerRange(this.getRange().radius * 1.5f);
		this.setAttackCooldown(this.getAttackCooldown() * 0.9f);
	}

	//Sell Tower Method - currently set to recover half of the tower's value
	public void sellTower() {
		map.getLevel().setMoney(map.getLevel().getMoney() + this.getTowerCost()/2);
		map.getTowers().remove(this);
		tile.setOccupied(false);
	}

	// Finds the closest enemy to the tower
	public Enemy getClosestEnemy() {
		if (map.getEnemyList().isEmpty()){
			return null;
		}

		ArrayList<Enemy> inRange = new ArrayList<Enemy>();
		for (Enemy enemy : map.getEnemyList()) {
			if (range.contains(enemy.getCenterX(), enemy.getCenterY())) {
				inRange.add(enemy);
			}
		}
		if (inRange.isEmpty()) {
			return null;
		}

		Enemy closest = inRange.get(0);
		for (Enemy enemy : inRange) {
			if (this.getVector2().dst(enemy.getVector2()) < this.getVector2().dst(closest.getVector2())) {
				closest = enemy;
			}
		}
		return closest;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void setType(TowerTypes type) {
		this.type = type;
	}

	public TowerTypes getTowerType() {
		return type;
	}

	public void setTowerHealth(float health) {
		this.health = health;
	}

	public float getTowerHealth() {
		return health;
	}

	public void setFireAmount(int fireAmount) {
		this.fireAmount = fireAmount;
	}

	public int getFireAmount() {
		return fireAmount;
	}

	public void setTowerDamage(float f) {
		this.damageMult = f;
	}

	public float getTowerDamage() {
		return damageMult;
	}

	public void setTowerRange(float range) {
		float cellMultiplier = (map.cellHeight() + map.cellWidth() / 2);

		this.range = new Circle(getCenterX(), getCenterY(), (range * cellMultiplier)/100);
		System.out.println(range);
	}

	public void cleanProjectiles() {
		ArrayList<Projectile> toRemove = new ArrayList<Projectile>();
		for (Projectile projectile : projectiles) {
			if (projectile.isToRemove()){
				toRemove.add(projectile);
			}
		}
		for (Projectile projectile : toRemove) {
			projectile.dispose();
			projectiles.remove(projectile);
		}
	}

	public float getTowerRange() {
		return rangeRadius;
	}

	public void setTowerCost(int cost) {
		this.cost = cost;
		this.setTowerUpgradeCost((int) (cost * 2f));
	}

	public int getTowerCost() {
		return cost;
	}

	public float getAttackCooldown() {
		return attackCooldown;
	}

	public void setAttackCooldown(float attackCooldown) {
		this.attackCooldown = attackCooldown;
	}

	public Enemy getCurrTarget() {
		return currTarget;
	}

	public void setCurrTarget(Enemy currTarget) {
		this.currTarget = currTarget;
	}

	public Vector2 getVector2() {
		return new Vector2(getX(), getY());
	}


	public int getTowerUpgradeCost() {
		return towerUpgradeCost;
	}

	public void setTowerUpgradeCost(int towerUpgradeCost) {
		this.towerUpgradeCost = towerUpgradeCost;
	}


}
