package edu.comp55.burnstd.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.projectile.BulletProjectile;
import edu.comp55.burnstd.projectile.Projectile;
import edu.comp55.burnstd.projectile.SpikeProjectile;
import edu.comp55.burnstd.screens.UpgradeScreen;

public class SpikeTower extends Tower {
	public SpikeTower(float x, float y, Map map) {
		super(x, y, map, new Sprite(new Texture(Gdx.files.internal("textures/towers/spike_tower.png"))));

		this.setAttackCooldown(1f * UpgradeScreen.getUpgradeFireRate());
		this.setTowerDamage(50f * UpgradeScreen.getUpgradeDamage());
		this.setTowerRange(100 * UpgradeScreen.getUpgradeRange());
		this.setTowerCost(2250);
		this.setFireAmount(8 + UpgradeScreen.getUpgradeAmount());
		
	}


	@Override
	public void upgradeLevel1() {
		super.upgradeLevel1();
		this.setFireAmount(this.getFireAmount() + 1);
	}

	@Override
	public void upgradeLevel2() {
		super.upgradeLevel2();
		this.setFireAmount(this.getFireAmount() + 1);
	}

	@Override
	public void upgradeLevel3() {
		super.upgradeLevel3();
		this.setFireAmount(this.getFireAmount() + 1);
	}




	public void spawnProjectile(Enemy enemy) {

		for (int i = 0; i < fireAmount; i++){
			float angle = 360/fireAmount * i;
			Projectile projectile = new SpikeProjectile(angle, map, this);
			projectiles.add(projectile);
		}
	}
}
