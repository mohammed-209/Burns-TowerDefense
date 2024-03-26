package edu.comp55.burnstd.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.comp55.burnstd.tooltips.TowerTextToolTip;

import javax.swing.*;

public class SetupUI extends Stage {

	private Level level;

	
	
	public SetupUI(Level level) {
		this.level = level;
		Skin skin = new Skin(Gdx.files.internal("skins/testskin/test.json"));

		TextButton resetButton = new TextButton("Reset", skin);
		resetButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SetupUI.this.level.reset();
				//reset isOccupied status
				for (Tile t : SetupUI.this.level.getMap().getTiles()) {
					t.setOccupied(false);
				}
			}
		});
		resetButton.addListener(new TowerTextToolTip("Reset path", TooltipManager.getInstance(), skin));
		addActor(resetButton);



		TextButton backButton = new TextButton("Back", skin);
		backButton.setPosition(75, 0);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SetupUI.this.level.getMap().backPath();
			}
		});
		backButton.addListener(new TextTooltip("Undo last path", skin));
		addActor(backButton);


		TextButton confirmButton = new TextButton("Confirm", skin);
		confirmButton.setPosition(500, 0);
		confirmButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (SetupUI.this.level.getMap().pathConnects) {
					SetupUI.this.level.setGameMode(1);
				}
				
			}
		});
		confirmButton.addListener(new TextTooltip("Confirm path", skin));
		addActor(confirmButton);
	}

}
