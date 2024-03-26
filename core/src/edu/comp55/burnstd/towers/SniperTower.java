package edu.comp55.burnstd.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.projectile.BulletProjectile;
import edu.comp55.burnstd.projectile.Projectile;
import edu.comp55.burnstd.projectile.SniperProjectile;
import edu.comp55.burnstd.screens.UpgradeScreen;

import java.util.ArrayList;

public class SniperTower extends Tower {


	public SniperTower(float x, float y, Map map) {
		super(x, y, map, new Sprite(new Texture(Gdx.files.internal("textures/towers/sniper_tower.png"))));

		this.setAttackCooldown(10f * UpgradeScreen.getUpgradeFireRate());
		this.setTowerDamage(250f * UpgradeScreen.getUpgradeDamage());
		this.setTowerRange(500 * UpgradeScreen.getUpgradeRange());
		this.setTowerCost(3000);
		fireAmount = 1;
		
	}



	public void spawnProjectile(Enemy enemy) {
		Projectile projectile = new SniperProjectile(enemy, map, this);
		projectiles.add(projectile);
	}

}
