package edu.comp55.burnstd.map;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import edu.com55.burnstd.buttons.BulletTowerButton;
import edu.com55.burnstd.buttons.DraggingButton;
import edu.com55.burnstd.buttons.FireballTowerButton;
import edu.com55.burnstd.buttons.HomingTowerButton;
import edu.com55.burnstd.buttons.IceTowerButton;
import edu.com55.burnstd.buttons.LaserTowerButton;
import edu.com55.burnstd.buttons.MoneyTowerButton;
import edu.com55.burnstd.buttons.PoisonTowerButton;
import edu.com55.burnstd.buttons.SniperTowerButton;
import edu.com55.burnstd.buttons.SpikeTowerButton;

public class GameUI extends Stage{
	
	private Level level;
	private TextButton waveButton;
	private Skin skin;
	private DraggingButton currButton;
	
//	private ArrayList<DraggingButton> buttons;
	private int buttonIndex;

	private ProgressBar enemyQueueProgressBar;

	private Slider speedSlider;

	
	//try adding a scrolling menu
	private ScrollPane towerScroll;
	Table towerButtons;	
	
	public GameUI(Level level) {
		this.level = level;
		this.skin = new Skin(Gdx.files.internal("skins/testskin/test.json"));
//		this.buttons = new ArrayList<DraggingButton>();
		this.buttonIndex = 0;

		waveButton = new TextButton("Spawn next wave", skin);
		waveButton.setSize(165, 40);
		waveButton.setPosition(Gdx.graphics.getWidth() - waveButton.getWidth() - 17, 17);
		waveButton.getLabel().setFontScale(0.5f);
		waveButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!GameUI.this.level.isWaveActive()){
					GameUI.this.level.queueWave();
				}
			}
		});

		// Create game speed slider
		speedSlider = new Slider(1f, 10.0f, 0.5f, false, skin);
		speedSlider.setBounds(17, 73, 165, 40);

		speedSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Slider slider = (Slider) actor;
				GameUI.this.level.getGameScreen().setSpeedMultiplier(slider.getValue());
			}
		});

		addActor(speedSlider);
		
		//initializing the scrollpane before adding the buttons
		towerButtons = new Table (skin);
		
		towerScroll = new ScrollPane(towerButtons, new Skin(Gdx.files.internal("skins/teststransparentscrollskin/testscrollskin.json")));
		towerScroll.setBounds(615, 73, 168, 300);
		towerScroll.setFadeScrollBars(true);
		towerScroll.setScrollingDisabled(true, false);


		// Create progress bar for enemy queue and add it to the stage
		enemyQueueProgressBar = new ProgressBar(0, 1, 0.01f, false, skin);
		enemyQueueProgressBar.setBounds(17, 17, 165, 40);
		enemyQueueProgressBar.setAnimateDuration(1.5f);
		enemyQueueProgressBar.setAnimateInterpolation(Interpolation.bounceOut);


		
		HomingTowerButton homingTowerButton = new HomingTowerButton(650, 165, this.level, this);
//		buttons.add(homingTowerButton);

		LaserTowerButton laserTowerButton = new LaserTowerButton(650, 165, this.level, this);
//		buttons.add(laserTowerButton);

		BulletTowerButton bulletTowerButton = new BulletTowerButton(650, 165, this.level, this);
//		buttons.add(bulletTowerButton);

		IceTowerButton iceTowerButton = new IceTowerButton(650, 165, this.level, this);
//		buttons.add(iceTowerButton);

		SniperTowerButton sniperTowerButton = new SniperTowerButton(650, 165, this.level, this);
//		buttons.add(sniperTowerButton);

		SpikeTowerButton spikeTowerButton = new SpikeTowerButton(650, 165, this.level, this);
//		buttons.add(spikeTowerButton);

		PoisonTowerButton poisonTowerButton = new PoisonTowerButton(650, 165, this.level, this);
//		buttons.add(poisonTowerButton);

		MoneyTowerButton moneyTowerButton = new MoneyTowerButton(650, 165, this.level, this);
//		buttons.add(moneyTowerButton);

		FireballTowerButton fireballTowerButton = new FireballTowerButton(650, 165, this.level, this);
//		buttons.add(fireballTowerButton);


		
		//trying to add a scrolling menu into screen
		towerButtons.add(fireballTowerButton).row();
		towerButtons.add(homingTowerButton).row();
		towerButtons.add(laserTowerButton).row();
		towerButtons.add(bulletTowerButton).row();
		towerButtons.add(iceTowerButton).row();
		towerButtons.add(sniperTowerButton).row();
		towerButtons.add(spikeTowerButton).row();
		towerButtons.add(poisonTowerButton).row();
		towerButtons.add(moneyTowerButton).row();



		//Set the scroll bar to be deactivated when it is clicked
		towerButtons.addListener(new ClickListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				towerScroll.setScrollingDisabled(true, true);
				System.out.println("scrolling disabled");
				return true;
			}
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				towerScroll.setScrollingDisabled(true, false);
				System.out.println("scrolling enabled");
			}
		});

		towerScroll.addListener(new InputListener(){
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				setScrollFocus(towerScroll);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				setScrollFocus(null);
			}
		});


		// If towerScroll is added after the other components they will be invisible???
		addActor(towerScroll);
		addActor(enemyQueueProgressBar);
		addActor(waveButton);
	}
	@Override
	public void act(float delta) {
		// Update the progress bar
		super.act(delta);
		enemyQueueProgressBar.setValue(level.getEnemyQueueProgress());
		enemyQueueProgressBar.act(delta);
		towerScroll.act(delta);
		enemyQueueProgressBar.addListener(new TextTooltip("Enemies remaining: " + level.getEnemiesInWave() + "/" + level.getMaxEnemiesInWave() + ".", skin));
		speedSlider.addListener(new TextTooltip("Game speed: " + speedSlider.getValue() + "x.", skin));

	}


	public Table getTowerButtons() {
		return towerButtons;
	}

	public void setTowerButtons(Table towerButtons) {
		this.towerButtons = towerButtons;
	}


}
