package edu.com55.burnstd.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;

import edu.comp55.burnstd.map.GameUI;
import edu.comp55.burnstd.map.Level;
import edu.comp55.burnstd.tooltips.TowerTextToolTip;
import edu.comp55.burnstd.towers.LaserTower;
import edu.comp55.burnstd.towers.Tower;

public class LaserTowerButton extends DraggingButton {

	LaserTower tower;
	String laserText;
	TowerTextToolTip laserTip;
	Skin skin;
	
	public LaserTowerButton(float X, float Y, final Level level, GameUI gameui) {
		super(X, Y, new Texture(Gdx.files.internal("textures/towers/laser_tower.png")), level, gameui);
		tower = new LaserTower(0, 0, level.getMap());
		
		skin = new Skin(Gdx.files.internal("skins/testskin/test.json"));
		laserText = "Laser Tower\nCost: " + tower.getTowerCost() + "\nDamage: " + tower.getTowerDamage() + "\nRange: " + tower.getTowerRange();
		laserTip = new TowerTextToolTip(laserText, TooltipManager.getInstance(), skin);
		this.addListener(laserTip);
	}

	public void clickUp(InputEvent event, float x, float y, int pointer, int button) {
		if (level.getMoney() >= tower.getTowerCost()) {
			Tower t = new LaserTower(positionX, positionY, level.getMap());
			level.getMap().getTowers().add(t);
			level.setMoney(level.getMoney() - tower.getTowerCost());
		}
	}
}
