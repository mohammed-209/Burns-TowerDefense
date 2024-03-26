package edu.comp55.burnstd.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.map.Map;
import edu.comp55.burnstd.projectile.BulletProjectile;
import edu.comp55.burnstd.projectile.Projectile;

public class ExplosionHandler extends Tower {
	public ExplosionHandler(float x, float y, Map map) {
		super(x, y, map, new Sprite(new Texture(Gdx.files.internal("textures/towers/bullet_tower.png"))));
		this.setScale(0.0000001f);
		this.tile.setOccupied(false);
	}

	public void addExplosion(Projectile explosion) {
		projectiles.add(explosion);
	}

}
