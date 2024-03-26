package edu.comp55.burnstd.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.projectile.BulletProjectile;
import edu.comp55.burnstd.projectile.FireballProjectile;
import edu.comp55.burnstd.projectile.Projectile;
import edu.comp55.burnstd.screens.UpgradeScreen;

public class FireballTower extends Tower {
	public FireballTower(float x, float y, Map map) {
		super(x, y, map, new Sprite(new Texture(Gdx.files.internal("textures/towers/fireball_tower.png"))));

		this.setAttackCooldown(2f * UpgradeScreen.getUpgradeFireRate());
		this.setTowerDamage(5f * UpgradeScreen.getUpgradeDamage());
		this.setTowerRange(200 * UpgradeScreen.getUpgradeRange());
		this.setTowerCost(1750);
		fireAmount = 1;
		
	}


	public void spawnProjectile(Enemy enemy) {
		Projectile projectile = new FireballProjectile(enemy, map, this);
		projectiles.add(projectile);
	}


}
