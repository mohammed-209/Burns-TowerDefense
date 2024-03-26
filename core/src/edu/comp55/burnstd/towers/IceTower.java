package edu.comp55.burnstd.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.projectile.BulletProjectile;
import edu.comp55.burnstd.projectile.IceProjectile;
import edu.comp55.burnstd.projectile.Projectile;
import edu.comp55.burnstd.screens.UpgradeScreen;

import java.util.ArrayList;

public class IceTower extends Tower {


	public IceTower(float x, float y, Map map) {
		super(x, y, map, new Sprite(new Texture(Gdx.files.internal("textures/towers/ice_tower.png"))));

		this.setAttackCooldown(0.10f * UpgradeScreen.getUpgradeFireRate());
		this.setTowerDamage(10f * UpgradeScreen.getUpgradeDamage());
		this.setTowerRange(100 * UpgradeScreen.getUpgradeRange());
		this.setTowerCost(1500);
		this.setOriginCenter();
		this.setFireAmount(1);
	}


	public void spawnProjectile(Enemy enemy) {
		Projectile projectile = new IceProjectile(enemy, map, this);
		projectiles.add(projectile);
	}

}
