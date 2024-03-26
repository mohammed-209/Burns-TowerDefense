package edu.comp55.burnstd.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.projectile.IceProjectile;
import edu.comp55.burnstd.projectile.PoisonProjectile;
import edu.comp55.burnstd.projectile.Projectile;
import edu.comp55.burnstd.screens.UpgradeScreen;

import java.util.ArrayList;
import java.util.HashSet;

public class PoisonTower extends Tower {


	public PoisonTower(float x, float y, Map map) {
		super(x, y, map, new Sprite(new Texture(Gdx.files.internal("textures/towers/poison_tower.png"))));

		this.setAttackCooldown(0.10f * UpgradeScreen.getUpgradeFireRate());
		this.setTowerDamage(2.5f * UpgradeScreen.getUpgradeDamage());
		this.setTowerRange(100 * UpgradeScreen.getUpgradeRange());
		this.setTowerCost(2500);
		this.setOriginCenter();
		this.setFireAmount(1);
	}


	public void spawnProjectile(Enemy enemy) {
		Projectile projectile = new PoisonProjectile(enemy, map, this);
		projectiles.add(projectile);
	}




}
