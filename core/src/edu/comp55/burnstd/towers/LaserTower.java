package edu.comp55.burnstd.towers;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.projectile.LaserProjectile;
import edu.comp55.burnstd.projectile.Projectile;
import edu.comp55.burnstd.screens.UpgradeScreen;

public class LaserTower extends Tower {
	public LaserTower(float x, float y, Map map) {
		super(x, y, map, new Sprite(new Texture(Gdx.files.internal("textures/towers/laser_tower.png"))));
		this.setTowerRange(300 * UpgradeScreen.getUpgradeRange());
		this.setAttackCooldown(2f * UpgradeScreen.getUpgradeFireRate());
		this.setTowerCost(3000);
		this.setTowerDamage(15f * UpgradeScreen.getUpgradeDamage());
		this.setFireAmount(1);
	}


	public void spawnProjectile(Enemy enemy) {
		Projectile projectile = new LaserProjectile(enemy, map, this);
		projectiles.add(projectile);
	}

}
