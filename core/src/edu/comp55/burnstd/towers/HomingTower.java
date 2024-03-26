package edu.comp55.burnstd.towers;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.projectile.HomingProjectile;
import edu.comp55.burnstd.projectile.Projectile;
import edu.comp55.burnstd.screens.UpgradeScreen;

public class HomingTower extends Tower {
	public HomingTower(float x, float y, Map map) {
		super(x, y, map, new Sprite(new Texture(Gdx.files.internal("textures/towers/missile_tower.png"))));

		this.setAttackCooldown(1f * UpgradeScreen.getUpgradeFireRate());
		this.setTowerDamage(40f * UpgradeScreen.getUpgradeDamage());
		this.setTowerRange(160 * UpgradeScreen.getUpgradeRange());
		this.setTowerCost(2000);
		this.setFireAmount(1);
		
	}

	public void spawnProjectile(Enemy enemy) {
		Projectile projectile = new HomingProjectile(enemy, map, this);
		projectiles.add(projectile);
	}

}
