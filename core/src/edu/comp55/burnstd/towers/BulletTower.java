package edu.comp55.burnstd.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.projectile.BulletProjectile;
import edu.comp55.burnstd.projectile.Projectile;
import edu.comp55.burnstd.screens.UpgradeScreen;

import java.awt.*;
import java.util.ArrayList;

public class BulletTower extends Tower {
	public BulletTower(float x, float y, Map map) {
		super(x, y, map, new Sprite(new Texture(Gdx.files.internal("textures/towers/bullet_tower.png"))));

		this.setAttackCooldown(0.25f * UpgradeScreen.getUpgradeFireRate());
		this.setTowerDamage(50f * UpgradeScreen.getUpgradeDamage());
		this.setTowerRange(100 * UpgradeScreen.getUpgradeRange());
		this.setTowerCost(750);
		this.setFireAmount(1);
		
	}

	public void spawnProjectile(Enemy enemy) {
		Projectile projectile = new BulletProjectile(enemy, map, this);
		projectiles.add(projectile);
		System.out.println("damage: " + this.getTowerDamage());
	}

}
