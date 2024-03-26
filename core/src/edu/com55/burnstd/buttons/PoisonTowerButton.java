package edu.com55.burnstd.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import edu.comp55.burnstd.map.GameUI;
import edu.comp55.burnstd.map.Level;
import edu.comp55.burnstd.tooltips.TowerTextToolTip;
import edu.comp55.burnstd.towers.IceTower;
import edu.comp55.burnstd.towers.PoisonTower;
import edu.comp55.burnstd.towers.Tower;

public class PoisonTowerButton extends DraggingButton {

	PoisonTower tower;
	String poisonText;
	TowerTextToolTip bulletTip;
	Skin skin;

	public PoisonTowerButton(float X, float Y, final Level level, GameUI gameui) {
		super(X, Y, new Texture(Gdx.files.internal("textures/towers/poison_tower.png")), level, gameui);
		tower = new PoisonTower(0, 0, level.getMap());
		
		skin = new Skin(Gdx.files.internal("skins/testskin/test.json"));
		poisonText = "Poison Tower\nCost: " + tower.getTowerCost() + "\nDamage: " + tower.getTowerDamage() + "\nRange: " + tower.getTowerRange();
		bulletTip = new TowerTextToolTip(poisonText, TooltipManager.getInstance(), skin);
		this.addListener(bulletTip);
	}
	
	public void clickUp(InputEvent event, float x, float y, int pointer, int button) {
		if (level.getMoney() >= tower.getTowerCost()) {
			Tower t = new PoisonTower(positionX, positionY, level.getMap());
			level.getMap().getTowers().add(t);
			level.setMoney(level.getMoney() - tower.getTowerCost());
			System.out.println("touchupPoisonTower");
		}
	}
}
