package edu.comp55.burnstd.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.projectile.BulletProjectile;
import edu.comp55.burnstd.projectile.Projectile;
import edu.comp55.burnstd.screens.UpgradeScreen;

import java.util.ArrayList;

public class MoneyTower extends Tower {
	public MoneyTower(float x, float y, Map map) {
		super(x, y, map, new Sprite(new Texture(Gdx.files.internal("textures/towers/money_tower.png"))));

		this.setAttackCooldown(1f * UpgradeScreen.getUpgradeFireRate());
		this.setTowerDamage(2f * UpgradeScreen.getUpgradeDamage());
		this.setTowerRange(0);
		this.setTowerCost(500);
		
	}

	@Override
	public void towerAttack(float delta) {
		attackTimer += delta;
		if (attackTimer >= attackCooldown && map.getLevel().isWaveActive()) {
			map.getLevel().setMoney(map.getLevel().getMoney() + (int) this.getTowerDamage());
			attackTimer = 0f;
		}
	}

}
