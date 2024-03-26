package edu.comp55.burnstd.towers;

public enum TowerTypes {
	ENEMY_BASE, BURNS_TOWER, SHORT, LONG, SLOW;

	public String toString() {
		switch (this) {
		case SHORT:
			return "short_ranged";
		case LONG:
			return "long_ranged";
		case SLOW:
			return "slow_enemies";
		case ENEMY_BASE:
			return "enemy_base";
		case BURNS_TOWER:
			return "burns_tower";
		}
		return "n/a";
	}
}
