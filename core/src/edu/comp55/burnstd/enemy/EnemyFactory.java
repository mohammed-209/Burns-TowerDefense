package edu.comp55.burnstd.enemy;

import edu.comp55.burnstd.map.Map;

public class EnemyFactory {

	public static Enemy createEnemy(String enemyType, float spawnCooldown, float x, float y, Map map, int level) {
		Enemy e = null;
		switch (enemyType) {
			case "standard":
				e = new EnemyStandard(x, y, spawnCooldown, map);
				e.setLevel(level);
				break;
			case "fast":
				e = new EnemyFast(x, y, spawnCooldown, map);
				e.setLevel(level);
				break;
			case "tank":
				e = new EnemyTank(x, y, spawnCooldown, map);
				e.setLevel(level);
				break;
			case "heli":
				e = new EnemyHeli(x, y, spawnCooldown, map);
				e.setLevel(level);
				break;
			case "tank_boss":
				e = new EnemyTankBoss(x, y, spawnCooldown, map);
				e.setLevel(level);
				break;
		default:
			System.out.println("Unknown enemy type: " + enemyType);
		}
		return e;
	}
}