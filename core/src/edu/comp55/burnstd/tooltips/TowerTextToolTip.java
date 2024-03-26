package edu.comp55.burnstd.tooltips;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;

public class TowerTextToolTip extends TextTooltip{
	
	public TowerTextToolTip(String string, TooltipManager tipManager, Skin skin) {
		super(string, tipManager, skin);
		tipManager.initialTime = 0.25f;
		tipManager.subsequentTime = 0.25f;
		tipManager.resetTime = 0.5f;
		tipManager.maxWidth = 200f;
	}
	

}
