package edu.comp55.burnstd.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import edu.comp55.burnstd.BurnsTowerDefence;

public class OptionsScreen extends Window {

	private Label titleLabel;
	private Label musicLabel;
	private Label soundLabel;
	private Slider musicSlider;
	private Slider soundSlider;
	private TextButton backButton;
	public static float musicVolume = BurnsTowerDefence.prefs.getFloat("musicVolume", 0.5f);
	public static float soundVolume = BurnsTowerDefence.prefs.getFloat("soundVolume", 0.5f);

	public OptionsScreen(Stage stage) {
		super("", new Skin(Gdx.files.internal("skins/testskin/test.json")));
		Skin skin = new Skin(Gdx.files.internal("skins/testskin/test.json"));
		// Create the UI elements
		titleLabel = new Label("Settings", skin);
		titleLabel.setFontScale(2f);
		musicLabel = new Label("Music Volume", skin);
		soundLabel = new Label("Sound Effects Volume", skin);
		musicSlider = new Slider(0f, 1f, 0.01f, true, skin);
		soundSlider = new Slider(0f, 1f, 0.01f, true, skin);
		musicSlider.setValue(musicVolume);
		soundSlider.setValue(soundVolume);

		backButton = new TextButton("Back", skin);

		Table table = new Table();
		table.setFillParent(true);
		table.defaults().pad(10f);
		table.add(titleLabel).colspan(2).center();
		table.row();
		table.add(musicLabel);
		table.add(musicSlider).width(200f);
		table.row();
		table.add(soundLabel);
		table.add(soundSlider).width(200f);
		table.row();
		table.add(backButton).colspan(2).center();
		this.addActor(table);
		loadPrefs();

		// Half the scale of the window
		this.setScale(0.5f);

		// Allow window to move outside of the screen
		this.setKeepWithinStage(false);

		// Make window not movable
		this.setMovable(false);

		// Set size to half the width and height of game window
		this.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Set position to center of screen
		this.setPosition(stage.getWidth()/2-this.getWidth()/4, stage.getHeight()/2-this.getHeight()/4);

		// Add listeners to the UI elements
		musicSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				musicVolume = musicSlider.getValue();
				BurnsTowerDefence.prefs.putFloat("musicVolume", musicVolume);
				BurnsTowerDefence.prefs.flush();
			}
		});

		soundSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				soundVolume = soundSlider.getValue();
				BurnsTowerDefence.prefs.putFloat("soundVolume", soundVolume);
				BurnsTowerDefence.prefs.flush();
			}
		});

		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				OptionsScreen.this.remove();

			}
		});
	}

	public static void loadPrefs() {
		musicVolume = BurnsTowerDefence.prefs.getFloat("musicVolume");
		soundVolume = BurnsTowerDefence.prefs.getFloat("soundVolume");

		System.out.println(musicVolume);
		System.out.println(soundVolume);
	}

}